package test.db;

import static org.kered.dko.SQLFunction.CONCAT;
import static org.kered.dko.SQLFunction.DATEADD;
import static org.kered.dko.SQLFunction.COUNT;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.kered.dko.Bulk;
import org.kered.dko.CSV;
import org.kered.dko.Condition;
import org.kered.dko.Constants;
import org.kered.dko.Constants.CALENDAR;
import org.kered.dko.Context;
import org.kered.dko.Context.Undoer;
import org.kered.dko.Diff;
import org.kered.dko.Diff.RowChange;
import org.kered.dko.Field;
import org.kered.dko.Field.Tag;
import org.kered.dko.Join;
import org.kered.dko.Query;
import org.kered.dko.QueryFactory;
import org.kered.dko.Table;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.unittest.nosco_test_jpetstore.Account;
import org.kered.dko.unittest.nosco_test_jpetstore.Category;
import org.kered.dko.unittest.nosco_test_jpetstore.Inventory;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;
import org.kered.dko.unittest.nosco_test_jpetstore.Orderstatus;
import org.kered.dko.unittest.nosco_test_jpetstore.Product;
import org.kered.dko.unittest.nosco_test_jpetstore.Supplier;

import test.db.callback.nosco_test_jpetstore.ItemCB;
//import static org.kered.dko.Function.*;


public class SharedDBTests extends TestCase {

	protected String SELECT_COUNT_1_FROM_INVENTORY = "select count(1) from inventory";
	DataSource ds = null;
	ConnectionCountingDataSource ccds = null;

	public static void printTestName() {
		final StackTraceElement[] st = Thread.currentThread().getStackTrace();
		System.err.println("------------============[ "+ st[2].getMethodName() +" ]============------------");
	}

	public void test01() throws SQLException {
		printTestName();
		final Connection conn = ds.getConnection();
		final Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery(SELECT_COUNT_1_FROM_INVENTORY);
		rs.next();
		final int count = rs.getInt(1);
		//System.err.println("count: "+ count);
		rs.close();
		stmt.close();
		conn.close();
		assertTrue(count > 0);
	}

	public void test02() throws SQLException {
		printTestName();
		assertTrue(Item.ALL.use(ds).count() > 0);
	}

	public void test04() throws SQLException {
		printTestName();
		int count = 0;
		for (final Item i : Item.ALL.use(ds).top(10)) ++count;
		assertEquals(10, count);
	}

	public void testWithAndCross() throws SQLException {
		printTestName();
		int count = 0;
		final long countp = Product.ALL.use(ds).count();
		final long countc = Category.ALL.use(ds).count();
		final Query<Product> q = Product.ALL.use(ds).cross(Category.class);
		for (final Product p : q) {
			//assertNotNull(p);
			//assertNotNull(p.getProductidFK());
			++count;
		}
		System.err.println("countp "+ countp);
		System.err.println("countc "+ countc);
		System.err.println("testWithAndCross "+ count);
		System.err.println("testWithAndCross "+ Category.ALL.use(ds).count());
		//assertTrue(count == countp * countc);
	}

	public void testFK1() throws SQLException {
		printTestName();
		final long itemCount = Item.ALL.use(ds).count();
		final long itemCount2 = Item.ALL.use(ds).with(Item.FK_SUPPLIER).count();
		// counts should be the same w/ and w/o the FK reference
		assertTrue(itemCount == itemCount2);
		int count = 0;
		boolean sawEST29 = false;
		for (final Item item : Item.ALL.use(ccds).with(Item.FK_SUPPLIER)) {
			count++;
			if (!"EST-29".equals(item.getItemid()) && !item.getItemid().startsWith("test-")) {
				assertNotNull(item.getSupplierFK());
			} else {
				sawEST29 = true;
			}
		}
		assertTrue(sawEST29);
		assertEquals(itemCount, count);
		// make sure the calls to the FK object didn't gen new queries
		assertEquals(1, ccds.getCount());
	}

//	public void testFKNoWith() throws Exception {
//	printTestName();
//		final Undoer x = Context.getVMContext().setDataSource(ccds);
//		final ClassLoader classLoader = this.getClass().getClassLoader();
//		final Class<?> clz = classLoader.loadClass("org.kered.dko.UsageMonitor");
//		final java.lang.reflect.Field field = clz.getDeclaredField("warnBadFKUsageCount");
//		field.setAccessible(true);
//		final java.lang.reflect.Field __NOSCO_USAGE_MONITOR = Table.class.getDeclaredField("__NOSCO_USAGE_MONITOR");
//		__NOSCO_USAGE_MONITOR.setAccessible(true);
//		final Method warnBadFKUsage = clz.getDeclaredMethod("warnBadFKUsage");
//		warnBadFKUsage.setAccessible(true);
//		Object um = null;
//		final long warnBadFKUsageCountPre = field.getLong(null);
//		for (final Item item : Item.ALL) { //.with(Item.FK_SUPPLIER)
//			// this should create O(n) queries because we didn't specify with() above
//			item.getSupplierFK();
//			if (um == null) um = __NOSCO_USAGE_MONITOR.get(item);
//		}
//		assertTrue(ccds.getCount() > 1);
//		warnBadFKUsage.invoke(um);
//		final long warnBadFKUsageCountPost = field.getLong(null);
//		assertEquals(warnBadFKUsageCountPre+1, warnBadFKUsageCountPost);
//	}

	@SuppressWarnings("unused")
	public void testFKReverseCounts() throws SQLException {
		printTestName();
		System.err.println("testFKReverseCounts start");
		final Undoer x = Context.getVMContext().setDataSource(ds);
		final long count1 = Supplier.ALL.count();
		final long count2 = Supplier.ALL.with(Item.FK_SUPPLIER).count();
		int count3 = 0;
		int supplierCount = 0;
		for (final Supplier s : Supplier.ALL) {
			supplierCount += 1;
			final long itemCount = s.getItemSet().count();
			count3 += Math.max(1, itemCount);
		}
		assertEquals(count2, count3);
		assertEquals(count1, supplierCount);
	}

	@SuppressWarnings("unused")
	public void testFKReverse() throws SQLException {
		printTestName();
		System.err.println("testFKReverse start");
		final long count1 = Supplier.ALL.count();
		long count2 = 0;
		final Undoer y = Context.getVMContext().setDataSource(ccds);
		for (final Supplier s : Supplier.ALL.with(Item.FK_SUPPLIER)) {
			count2++;
			System.err.println(s);
			for (final Item i : s.getItemSet()) {
				System.err.println("\t"+i);
			}
			if (s.getSuppid() == 1) assertTrue(s.getItemSet().count() > 0);
			else assertEquals(0, s.getItemSet().count());
		}
		assertEquals(count1, count2);
		assertEquals(1, ccds.getCount());
		System.err.println("testFKReverse done");
	}

	@SuppressWarnings("unused")
	public void testFKReverseQueryReplicationBug() throws SQLException {
		printTestName();
		final Query<Supplier> q = Supplier.ALL.with(Item.FK_SUPPLIER);
		q.count();
		for (final Supplier s : q) {
		}
	}

	public void testFKTwoLevels() throws SQLException {
		printTestName();
		final Undoer u = Context.getVMContext().setDataSource(ccds);
		for (final Item i : Item.ALL.with(Item.FK_PRODUCTID_PRODUCT, Product.FK_CATEGORY)) {
			assertNotNull(i);
			assertNotNull(i.getProductidFK());
			assertNotNull(i.getProductidFK().getCategoryFK());
			System.err.println(i);
		}
		assertEquals(1, ccds.getCount());
	}

	public void testSimpleDiff() throws SQLException {
		printTestName();
		final List<Item> items = Item.ALL.asList();
		assertTrue(items.size() > 0);
		items.get(0).setAttr4("something");
		items.add(new Item());
		items.add(new Item());
		int updates = 0;
		int adds = 0;
		int count = 0;
		for (final RowChange<Item> diff : Diff.diff(items)) {
			count += 1;
			if (diff.isAdd()) adds += 1;
			if (diff.isUpdate()) updates += 1;
			System.err.println(diff);
		}
		assertEquals(1, updates);
		assertEquals(2, adds);
		assertEquals(3, count);
	}

	public void testDateAdd() throws SQLException {
		printTestName();
		Orderstatus.ALL.where(Orderstatus.TIMESTAMP.lt(
				DATEADD(Orderstatus.TIMESTAMP, 1, CALENDAR.DAY)))
				.asList();
	}

	public void testRecommendation() throws InterruptedException {
		printTestName();
		for (final Item i : Item.ALL) {
			i.getProductidFK();
		}
		// note: this should raise a logging WARNING!
		System.gc();
		Thread.sleep(1000);
		// this doesn't work for now since it's the last test
	}

	public void testExists() throws InterruptedException {
		printTestName();
		for (final Item i : Item.ALL.where(Item.ALL.exists())) {}
	}

	public void testConcat() throws SQLException {
		printTestName();
		assertEquals(1, Item.ALL.where(CONCAT(Item.ITEMID, "!").eq("EST-20!")).count());
	}

	public void testAdd() throws SQLException {
		printTestName();
		assertEquals(1, Inventory.ALL.where(Inventory.ITEMID.eq("EST-14"))
				.where(Inventory.QTY.add(0).eq(Inventory.QTY)).count());
	}

	public void testObjectArray() throws SQLException {
		printTestName();
		final long count = Item.ALL.count();
		int count2 = 0;
		for (final Object[] oa : Item.ALL.asIterableOfObjectArrays()) {
			count2 += 1;
		}
		assertEquals(count, count2);
	}

    public void testTransaction() throws SQLException {
		printTestName();
    	Context.getThreadContext().startTransaction(ds);
    	Item.ALL.get(Item.ITEMID.eq("EST-20"));
    	Context.getThreadContext().commitTransaction(ds);
    	Item.ALL.get(Item.ITEMID.eq("EST-20"));
    }

    public void testTopWithToManyRelationship() throws SQLException {
		printTestName();
    	final Product p = Product.ALL.with(Item.FK_PRODUCTID_PRODUCT)
    			.where(Product.PRODUCTID.eq("FL-DSH-01"))
    			.first();
    	assertEquals(2, p.getItemSet().count());
    	assertEquals(1, Product.ALL.with(Item.FK_PRODUCTID_PRODUCT).top(1).asList().size());
    }

    public void testBulkInsert() throws SQLException {
		printTestName();
    	final Query<Category> them = Category.ALL.where(Category.CATID.like("test-%"));
		them.delete();
    	final List<Category> categories = new ArrayList<Category>();
    	categories.add(new Category().setCatid("test-1"));
    	categories.add(new Category().setCatid("test-2").setName("woot"));
    	categories.add(new Category().setCatid("test-3").setName("woot2"));
    	final Bulk bulk = new Bulk(ds);
    	bulk.insertAll(categories);
    	assertEquals(3, them.count());
    }

    public void testBulkUpdate() throws SQLException {
		printTestName();
    	final long count = Item.ALL.count();
    	final List<Item> items = Item.ALL.asList();
		for (final Item item : items) {
    		item.setAttr2("woot2"+Math.random());
    		if (Math.random() > .5) {
    			item.setAttr3("woot3");
    		} else {
    			item.setAttr3("");
    		}
    		if (Math.random() > .5) {
    			item.setAttr4("woot4");
    		} else {
    			item.setAttr4("");
    		}
    	}
    	final Bulk bulk = new Bulk(ds);
    	final long ret = bulk.updateAll(items);
    	assertEquals(count, ret);
    }

    public void testBulkInsertOrUpdate() throws SQLException {
		printTestName();
    	System.err.println("testBulkInsertOrUpdate");
    	Item.ALL.where(Item.ITEMID.like("test-%")).delete();
    	final List<Item> updates = Item.ALL.asList();
    	String pid = null;
    	Integer sid = -1;
		for (final Item item : updates) {
			pid = item.getProductid();
			sid = item.getSupplier();
    		item.setAttr2("woot2");
    		if (Math.random() > .5) {
    			item.setAttr3("woot3");
    		}
    		if (Math.random() > .5) {
    			item.setAttr4("woot4");
    		}
    	}
		final List<Item> adds = new ArrayList<Item>();
		adds.add(new Item().setItemid("test-10").setProductid(pid).setSupplier(sid));
		adds.add(new Item().setItemid("test-20").setProductid(pid).setSupplier(sid).setAttr5("woot5"));
		final List<Item> all = new ArrayList<Item>();
		all.addAll(adds);
		all.addAll(updates);
    	final Bulk bulk = new Bulk(ds);
    	final long ret = bulk.insertOrUpdateAll(all);
    	assertEquals(all.size(), ret);
    	assertEquals(2, adds.size());
    	System.err.println(Item.ALL.get(Item.ITEMID.eq("test-10")));
    	System.err.println(Item.ALL.get(Item.ITEMID.eq("test-20")));
    	assertEquals(adds.size(), bulk.deleteAll(adds));
    }

    public void testBulkCommitDiff() throws SQLException {
		printTestName();
    	System.err.println("testBulkCommitDiff");
    	final Product p = Product.ALL.first();
    	Item.ALL.where(Item.ITEMID.like("test-%")).delete();
    	new Item().setItemid("test-1").setProductid(p.getProductid()).insert();
    	new Item().setItemid("test-2").setProductid(p.getProductid()).insert();
    	new Item().setItemid("test-3").setProductid(p.getProductid()).insert();
    	final List<Item> pre = Item.ALL.where(Item.ITEMID.like("test-%")).orderBy(Item.ITEMID).asList();
    	final List<Item> post = Item.ALL.where(Item.ITEMID.like("test-%")).orderBy(Item.ITEMID).asList();
    	post.get(0).setAttr2("woot2");
    	post.remove(1);
    	post.add(new Item().setItemid("test-4").setProductid(p.getProductid()));
    	final List<RowChange<Item>> diff = Diff.diffActualized(pre, post);
    	assertEquals(3, diff.size());
    	final Bulk bulk = new Bulk(ds);
    	final long ret = bulk.commitDiff(diff);
    	assertEquals(3, ret);
    	Item.ALL.where(Item.ITEMID.like("test-%")).delete();
    }

    public void testCallbacks() throws SQLException {
		printTestName();
    	final Item item = Item.ALL.first();
    	ItemCB.preUpdate = 0;
    	ItemCB.postUpdate = 0;
    	item.setAttr3("woot3-"+Math.random());
    	item.update();
    	assertEquals(1, ItemCB.preUpdate);
    	assertEquals(1, ItemCB.postUpdate);
    }

    public void testCallbacksBulk() throws SQLException {
		printTestName();
    	final Item item = Item.ALL.first();
    	item.setAttr3("woot3-"+Math.random());
    	ItemCB.preUpdate = 0;
    	ItemCB.postUpdate = 0;
    	final Bulk bulk = new Bulk(ds);
    	final List<Item> items = new ArrayList();
    	items.add(item);
    	bulk.updateAll(items);
    	assertEquals(1, ItemCB.preUpdate);
    	assertEquals(1, ItemCB.postUpdate);
    }

    public void testWriteCSV() throws Exception {
		printTestName();
    	CSV.write(Item.ALL, new java.io.File("bin/items.csv"));
    }

    public void testReadCSV() throws Exception {
		printTestName();
    	final List<Item> as = Item.ALL.asList();
    	final File f = new File("bin/items.csv");
    	CSV.write(as, f);
    	final List<Item> bs = new ArrayList<Item>();
    	for (final Item x : CSV.read(Item.class, f)) {
    		bs.add(x);
    	}
    	assertEquals(as.size(), bs.size());
    	Collections.sort(as);
    	Collections.sort(bs);
    	final List<RowChange<Item>> diff = Diff.diffActualized(as, bs);
    	for (final RowChange<Item> rc : diff) {
    		System.err.println(rc);
    	}
    	assertEquals(0, diff.size());
    }

    public void testWarningsOff() throws Exception {
		printTestName();
    	final Undoer u = Context.getVMContext().enableUsageWarnings(false);
    	for (final Item x : Item.ALL) {}
    	u.undo();
    }

    public void testWarningsOff2() throws Exception {
		printTestName();
    	for (final Object[] x : Item.ALL.asIterableOfObjectArrays()) {}
    	System.gc();
    }

    public void testFieldInField() throws Exception {
		printTestName();
    	Item.ALL.where(Item.ATTR1.in(Item.ATTR1, Item.ATTR2)).asList();
    }

    public void testCrossColumnSelects() throws Exception {
		printTestName();
    	// only select the cols from the primary table
    	final int colCount = Item._FIELDS.size();
    	final Method getSelectFields = Item.ALL.getClass().getDeclaredMethod("getSelectFields");
    	getSelectFields.setAccessible(true);
    	final Query<Item> q = Item.ALL.cross(Product.class).top(10);
    	final List<Field<?>> selectedFields = (List<Field<?>>) getSelectFields.invoke(q);
    	assertEquals(colCount, selectedFields.size());
    	for (final Object[] row : q.asIterableOfObjectArrays()) {
    		assertTrue(colCount < row.length);
    	}
    }

    public void testBetweenFields() throws Exception {
		printTestName();
    	Item.ALL.where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
    }

//    public void testBetweenFieldsInMemory() throws Exception {
//	printTestName();
//    	final List<Item> orig = Item.ALL.where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
//    	final List<Item> inMem = Item.ALL.toMemory().where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
//    	assertEquals(orig.size(), inMem.size());
//    }

    public void testMapBy2() throws Exception {
		printTestName();
    	final Map<String, Map<String, Item>> x = Item.ALL.mapBy(Item.ATTR2, Item.ATTR1);
    	for (final Entry<String, Map<String, Item>> e : x.entrySet()) {
    		for (final Entry<String, Item> e2 : e.getValue().entrySet()) {
    			System.err.println(e.getKey() +" "+ e2.getKey() + " "+ e2.getValue());
    		}
    	}
    	assertTrue(x.size() > 0);
    }

    public void testCollectBy2() throws Exception {
		printTestName();
    	final Map<String, Map<String, Collection<Item>>> x = Item.ALL.collectBy(Item.ATTR2, Item.ATTR1);
    	for (final Entry<String, Map<String, Collection<Item>>> e : x.entrySet()) {
    		for (final Entry<String, Collection<Item>> e2 : e.getValue().entrySet()) {
    			System.err.println(e.getKey() +" "+ e2.getKey() + " "+ e2.getValue());
    		}
    	}
    	assertTrue(x.size() > 0);
    }

    @SuppressWarnings("unchecked")
	public void testFieldKeywordCheck() throws Exception {
		printTestName();
    	final Field<Integer> field = new Field(0, null, "ADD", null, null, null);
    	final Class classSqlContext = this.getClass().getClassLoader().loadClass("org.kered.dko.SqlContext");
    	final Class classDBQuery = this.getClass().getClassLoader().loadClass("org.kered.dko.DBQuery");
    	final java.lang.reflect.Field fieldDbType = classSqlContext.getDeclaredField("dbType");
    	fieldDbType.setAccessible(true);
    	final Constructor con = classSqlContext.getDeclaredConstructor(classDBQuery);
    	con.setAccessible(true);
    	final Object context = con.newInstance(Item.ALL);
    	fieldDbType.set(context, Constants.DB_TYPE.SQLSERVER);
    	final Method method = Field.class.getDeclaredMethod("getSQL", classSqlContext);
    	method.setAccessible(true);
    	final Object ret = method.invoke(field, context);
    	assertEquals("[ADD]", ret);
    }

	public void testSelectColumnOrdering() throws Exception {
		printTestName();
		final Query<Item> q = Item.ALL.onlyFields(Item.ATTR5, Item.ATTR3, Item.ATTR1, Item.LISTPRICE);
		final List<Field<?>> fields = q.getSelectFields();
		assertEquals(Item.ATTR5, fields.get(0));
		assertEquals(Item.ATTR3, fields.get(1));
		assertEquals(Item.ATTR1, fields.get(2));
		assertEquals(Item.LISTPRICE, fields.get(3));
	}

	public void testJoin() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<Join<Item, Product>> q = Item.ALL.crossJoin(Product.class);
		assertEquals(c1 * c2,  q.count());
		for (final Join<Item, Product> x : q.top(64)) {
			System.err.println(x);
			x.l.getItemid();
		}
	}

	public void test2Joins() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final Query<Join<Join<Item, Product>, Category>> q = Item.ALL.crossJoin(Product.class).crossJoin(Category.class);
		assertEquals(c1 * c2 * c3,  q.count());
		for (final Join<Join<Item, Product>, Category> x : q.top(64)) {
			System.err.println(x);
			x.l.l.getItemid();
		}
	}

	public void test3Joins() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final long c4 = Account.ALL.count();
		final Query<Join<Join<Join<Item, Product>, Category>, Account>> q = Item.ALL.crossJoin(Product.class)
				.crossJoin(Category.class).crossJoin(Account.class);
		assertEquals(c1 * c2 * c3 * c4,  q.count());
		for (final Join<Join<Join<Item, Product>, Category>, Account> x : q.top(64)) {
			System.err.println(x);
			x.l.l.l.getItemid();
		}
	}

	public void test3JoinsAlt() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final long c4 = Account.ALL.count();
		final Query<Join<Join<Join<Item, Product>, Category>, Account>> q = Item.ALL.crossJoin(Product.class).crossJoin(Category.class).crossJoin(Account.class);
		assertEquals(c1 * c2 * c3 * c4,  q.count());
		for (final Join<Join<Join<Item, Product>, Category>, Account> x : q.top(64)) {
			System.err.println(x);
			x.l.l.l.getItemid();
			x.l.l.r.getProductid();
			x.l.r.getCatid();
			x.r.getEmail();
		}
	}

	public void testJoinAlias() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<Join<Item, Product>> q = Item.ALL.crossJoin(Product.as("pddt"));
		assertEquals(c1 * c2,  q.count());
		for (final Join<Item, Product> x : q) {
			x.l.getItemid();
			x.r.getProductid();
		}
	}

	public void testJoinAliasSelf() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Join<Item, Item>> q = Item.ALL.innerJoin(Item.as("pddt"), Item.ITEMID.eq(Item.ITEMID.from("pddt")));
		assertEquals(c1,  q.count());
		for (final Join<Item, Item> x : q) {
			System.err.println(x);
			assertEquals(x.l.getItemid(), x.r.getItemid());
		}
	}

	public void testLeftJoin() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.leftJoin(Supplier.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final Join<Item, Supplier> x : q.top(64)) {
			assertNotNull(x);
			System.err.println(x);
		}
	}

	public void testRightJoin() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Join<Supplier, Item>> q = Supplier.ALL.rightJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final Join<Supplier, Item> x : q.top(64)) {
			System.err.println(x);
		}
	}

	public void testInnerJoin() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.where(Item.SUPPLIER.isNotNull()).count();
		final Query<Join<Supplier, Item>> q = Supplier.ALL.innerJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final Join<Supplier, Item> x : q.top(64)) {
			System.err.println(x);
		}
	}

	// mysql doesn't support outer joins
//	public void testOuterJoin() throws SQLException {
//		final long c1 = Item.ALL.count();
//		final Query<Join<Supplier, Item>> q = Supplier.ALL.outerJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
//		//assertEquals(c1,  q.count());
//		for (final Join<Supplier, Item> x : q.top(64)) {
//			System.err.println(x);
//		}
//	}

	public void testLeftJoin2() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<Join<Product, Item>> q = Product.ALL.leftJoin(Item.class, Item.PRODUCTID.eq(Product.PRODUCTID));
		assertEquals(c1,  q.count());
		for (final Join<Product, Item> x : q.top(64)) {
			System.err.println(x);
			x.r.getItemid();
			x.l.getProductid();
		}
	}

    public void testTableIn() throws SQLException {
		printTestName();
		System.err.println("testTableIn");
    	final Query<Category> q = Category.ALL.where(Category.CATID.in(Category.ALL.onlyFields(Category.CATID)));
    	assertEquals(Category.ALL.size(), q.size());
    }

    public void testTableIn2() throws SQLException {
		printTestName();
    	final List<Category> cats = new ArrayList<Category>();
    	cats.add(Category.ALL.get(Category.CATID.eq("FISH")));
    	cats.add(Category.ALL.get(Category.CATID.eq("DOGS")));
    	final Query<Category> q = Category.ALL.in(cats);
    	assertEquals(2, q.size());
    	for (final Category c : q) {
    		System.err.println(c);
    		final String id = c.getCatid();
    		assertTrue("FISH".equals(id) || "DOGS".equals(id));
    	}
    }

    public void testJ2() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
    	final Query<Join<Item, Product>> q = Item.ALL.crossJoin(Product.class);
		assertEquals(c1 * c2,  q.count());
    	for (final Join<Item, Product> j : q) {
    		System.err.println(j);
    		j.l.getItemid();
    		j.r.getProductid();
    	}
    }

    public void testJ2Inline() throws SQLException {
		printTestName();
    	for (final Join<Item, Product> j : Item.ALL.crossJoin(Product.class)) {
    		System.err.println(j);
    		j.l.getItemid();
    		j.r.getProductid();
    	}
    }

    public void testJ2Inline2() throws SQLException {
		printTestName();
    	final Query<Join<Item, Product>> q = Item.ALL.crossJoin(Product.class).where(Condition.TRUE);
    	for (final Join<Item, Product> j : q) {
    		System.err.println(j);
    		j.l.getItemid();
    		j.r.getProductid();
    	}
    }

    public void testJ2Alt() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
    	final Query<Join<Item, Product>> q = Item.ALL.crossJoin(Product.class);
		assertEquals(c1 * c2,  q.count());
    	for (final Join<Item, Product> j : q) {
    		System.err.println(j);
    		j.l.getItemid();
    		j.r.getProductid();
    	}
    }

    public void testJ3() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
    	final Query<Join<Join<Item, Product>, Category>> q = Item.ALL.crossJoin(Product.class).crossJoin(Category.class);
		assertEquals(c1 * c2 * c3,  q.count());
    	for (final Join<Join<Item, Product>, Category> j : q) {
    		System.err.println(j);
    		j.l.l.getItemid();
    		j.l.r.getProductid();
    		j.r.getCatid();
    	}
    }
//
//    public void testJ3() throws SQLException {
//		printTestName();
//    	final Query<J3<Item, Product, Category>> q2 = Join.leftJoin(Join.leftJoin(Item.class, Product.class), Category.class);
//    	for (final J3<Item, Product, Category> j : q2) {
//    		j.t1.getItemid();
//    		j.t2.getProductid();
//    		j.t3.getCatid();
//    	}
//    }

    public void testNewBetween() throws SQLException {
		printTestName();
    	final Query<Item> q = Item.ALL.where(Field.between(15.0, Item.UNITCOST, Item.LISTPRICE));
    	for (final Item j : q) {
    		System.err.println(j.toStringDetailed());
    	}
    	assertEquals(18, q.count());
    }

    public void testNewBetweenWithFunctions() throws SQLException {
		printTestName();
    	final Query<Item> q = Item.ALL.where(Field.between(15.0, Item.UNITCOST.add(1.0), Item.LISTPRICE.sub(8.0)));
    	for (final Item j : q) {
    		System.err.println(j.toStringDetailed());
    	}
    	assertEquals(4, q.count());
    }

    public void testBadColumnNames() throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
		printTestName();
    	final Field f = new Field(0, null, "K%", null, null, null);
    	final Method m = Field.class.getDeclaredMethod("getSQL", StringBuffer.class, Constants.DB_TYPE.class);
    	m.setAccessible(true);
    	final StringBuffer sb = new StringBuffer();
    	m.invoke(f, sb, Constants.DB_TYPE.SQLSERVER);
    	assertEquals("[K%]", sb.toString());
    }

    public void testToStrings() {
		printTestName();
    	for (final Account a : Account.ALL) {
    		System.err.println(a);
    		System.err.println(a.toStringDetailed());
    		assertTrue(a.toString().toLowerCase().contains("name"));
    		assertTrue(a.toStringDetailed().toLowerCase().contains("name"));
    		assertFalse(a.toString().contains("addr1"));
    	}
    	for (final Category a : Category.ALL) {
    		System.err.println(a);
    		System.err.println(a.toStringDetailed());
    		assertTrue(a.toString().toLowerCase().contains("name"));
    		assertTrue(a.toStringDetailed().toLowerCase().contains("name"));
    		assertFalse(a.toString().contains("descn"));
    	}
    }

    public void testToStringCB() {
		printTestName();
    	assertTrue(Item.ALL.first().toString().endsWith("/CB"));
    }

    public void testFKSetThenGet() {
	System.err.println("testFKSetThenGet");
    	final Item item = new Item();
	System.err.println("item" + item);
    	final Product product = new Product().setProductid("dsfhlkljfds");
	System.err.println("product" + product);
    	assertNotNull(product);
    	item.setProductidFK(product);
    	final Product p2 = item.getProductidFK();
	System.err.println("p2" + p2);
    	assertNotNull(p2);
    	assertEquals(product.getProductid(), p2.getProductid());
	}

    public void testNewFieldsMethod() {
		printTestName();
    	for (final Item item : Item.ALL.onlyFields(Item.ITEMID, Item.UNITCOST)) {
    		assertEquals(2, item.fields().size());
    	}
    }

    public void testExtend() {
		printTestName();
    	final Field<Integer> f42 = new Field<Integer>("forty_two", Integer.class);
		final Query<Table> q = QueryFactory.IT.addField(Item.ALL, f42, 42);
		for (final Table t : q) {
			assertNotNull(t.get(Item.ITEMID));
			assertEquals(42, t.get(f42).intValue());
		}
    }

    public void testExtendFunction() {
		printTestName();
    	final Field<Integer> f42 = new Field<Integer>("forty_two", Integer.class);
		final Query<Table> q = QueryFactory.IT.addFieldFromCallback(Item.ALL, f42, new QueryFactory.Callback<Table, Integer>() {
			@Override
			public Integer apply(final Table a) {
				return 42;
			}
		});
		for (final Table t : q) {
			assertNotNull(t.get(Item.ITEMID));
			assertEquals(42, t.get(f42).intValue());
		}
    }

    public void testDeleteWithSchemaChange() throws SQLException {
		printTestName();
    	final Product p = Product.ALL.first();
    	new Item().setItemid("test-789").setProductid(p.getProductid()).insert(ds);
		final Undoer u = Context.getVMContext().overrideDatabaseName(ds, Item._SCHEMA_NAME, "bad_db_name");
    	try {
    		Item.ALL.use(ds).where(Item.ITEMID.eq("test-789")).delete();
    		assertTrue(false); // should not get here
    	} catch (final SQLException e) {
    		u.undo();
    		Item.ALL.use(ds).where(Item.ITEMID.eq("test-789")).delete();
    	}
    }

    public void testCrossDBJoin() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.use(ods).count();
		final long c2 = Product.ALL.count();
		final Query<Join<Item, Product>> q = Item.ALL.use(ods).crossJoin(Product.class);
		assertEquals(c1 * c2,  q.count());
		for (final Join<Item, Product> x : q.top(64)) {
			System.err.println(x);
			x.l.getItemid();
		}
    }

	public void testCDBLeftJoin() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.use(ods).leftJoin(Supplier.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		for (final Join<Item, Supplier> x : q.top(64)) {
			System.err.println(x);
		}
		assertEquals(c1,  q.count());
	}

	public void testCDBRightJoin() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.count();
		final Query<Join<Supplier, Item>> q = Supplier.ALL.use(ods).rightJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		for (final Join<Supplier, Item> x : q.top(64)) {
			System.err.println(x);
		}
		assertEquals(c1,  q.count());
	}

	public void testCDBInnerJoin() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.where(Item.SUPPLIER.isNotNull()).count();
		final Query<Join<Supplier, Item>> q = Supplier.ALL.use(ods).innerJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final Join<Supplier, Item> x : q.top(64)) {
			System.err.println(x);
		}
	}

	public void testCDBLeftJoinAlias() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.use(ods).leftJoin(Supplier.as("something"), Item.SUPPLIER.eq(Supplier.SUPPID));
		for (final Join<Item, Supplier> x : q.top(64)) {
			System.err.println(x);
		}
		assertEquals(c1,  q.count());
	}

	public void testCDBCrossJoinX2() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.count();
		final long c2 = Supplier.ALL.count();
		final long c3 = Product.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.use(ods).crossJoin(Supplier.class);
		final Query<Join<Join<Item, Supplier>, Product>> q2 = q.crossJoin(Product.class);
		int c = 0;
		for (final Join<Join<Item, Supplier>, Product> x : q2) {
			System.err.println(x);
			++c;
		}
		assertEquals(c1*c2*c3,  c);
	}

	public void testCDBCrossJoinX3() throws SQLException {
		printTestName();
		final DataSource ods = new PassThruDS(ds);
		final long c1 = Item.ALL.count();
		final long c2 = Supplier.ALL.count();
		final long c3 = Product.ALL.count();
		final long c4 = Category.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.use(ods).crossJoin(Supplier.class);
		final Query<Join<Join<Item, Supplier>, Product>> q2 = q.crossJoin(Product.class);
		final Query<Join<Join<Join<Item, Supplier>, Product>, Category>> q3 = q2.crossJoin(Category.class);
		int c = 0;
		for (final Join<Join<Join<Item, Supplier>, Product>, Category> x : q3) {
			if (c<64) System.err.println(x);
			++c;
		}
		assertEquals(c1*c2*c3*c4,  c);
	}

	public void testSnapshot() throws SQLException {
		printTestName();
		final Set<Item> items = new HashSet<Item>(Item.ALL.asList());
		final Iterable<Item> snapshot = Item.ALL.snapshot();
		final Set<Item> items2 = new HashSet<Item>();
		for (final Item item : snapshot) items2.add(item);
		assertEquals(items.size(), items2.size());
		items.removeAll(items2);
		assertEquals(0, items.size());
	}

//	public void testInnerQuerySelect() throws SQLException {
//		printTestName();
//		Query<Item> items = Item.ALL.alsoSelect(Product.ALL.onlyFields(Product.PRODUCTID).max());
//		for (Item item : items) {
//			// do nothing
//		}
//	}

	public void testInnerQuerySelect() throws SQLException {
		printTestName();
		final Field<String> field = Product.ALL.max().asInnerQueryOf(Product.PRODUCTID);
		final Query<Item> items = Item.ALL.alsoSelect(field);
		for (final Item item : items) {
			System.err.println(field +" = "+ item.get(field));
			assertNotNull(item.get(field));
		}
	}

	public void testInnerQuerySelect2() throws SQLException {
		printTestName();
		final Field<Double> avg = Item.ALL.avg().where(Item.SUPPLIER.eq(Supplier.SUPPID))
				.asInnerQueryOf(Item.UNITCOST);
		final Query<Supplier> suppliers = Supplier.ALL.alsoSelect(avg);
		for (final Supplier supplier : suppliers) {
			System.err.println(supplier +" average cost: $"+ supplier.get(avg));
			if ("XYZ Pets".equals(supplier.getName())) {
				assertNotNull(supplier.get(avg));
			}
		}
	}

	public void testTag() throws SQLException {
		printTestName();
		final Tag<String> tag = new Field.Tag<String>();
		System.err.println("tag: "+ tag);
		final Field<String> taggedFieldSrc = Item.ITEMID.tag(tag);
		final Query<Item> q = Item.ALL.alsoSelect(taggedFieldSrc);
		final Field<String> taggedField = tag.findField(q);
		System.err.println("taggedField: "+ taggedField);
		for (final Item item : q) {
			final String value = item.get(taggedField);
			assertNotNull(value);
		}
	}

	public void testTag2() throws SQLException {
		printTestName();
		final Tag<String> tag = new Field.Tag<String>();
		System.err.println("tag: "+ tag);
		final Field<String> taggedFieldSrc = Item.ITEMID.tag(tag);
		final Query<Item> q = Item.ALL.alsoSelect(taggedFieldSrc);
		final Collection<Field<String>> taggedFields = tag.findFields(q);
		assertEquals(1, taggedFields.size());
		final Field<String> taggedField = taggedFields.iterator().next();
		System.err.println("taggedField: "+ taggedField);
		for (final Item item : q) {
			final String value = item.get(taggedField);
			assertNotNull(value);
		}
	}

	public void testClearTag() throws SQLException {
		printTestName();
		final Tag<String> tag = new Field.Tag<String>();
		System.err.println("tag: "+ tag);
		final Field<String> taggedFieldSrc = Item.ITEMID.tag(tag);
		Query<Item> q = Item.ALL.alsoSelect(taggedFieldSrc);
		final Field<String> taggedField = tag.findField(q);
		System.err.println("taggedField: "+ taggedField);
		assertNotNull(taggedField);
		q = tag.clearFrom(q);
		assertNull(tag.findField(q));
	}

    public void testJoinInnerQuery() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Product> jq = Product.ALL;
		final long c2 = jq.count();
		final Query<Join<Item, Product>> q = Item.ALL.crossJoin(jq);
		assertEquals(c1 * c2,  q.count());
		int c=0;
		for (final Join<Item, Product> x : q) {
			++c;
			if (c<64) System.err.println(x);
			x.l.getItemid();
		}
		assertEquals(c1 * c2,  c);
    }

    public void testJoinInnerQuery2() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Product> jq = Product.ALL.where(Product.PRODUCTID.like("FL%"));
		final long c2 = jq.count();
		final Query<Join<Item, Product>> q = Item.ALL.crossJoin(jq);
		assertEquals(c1 * c2,  q.count());
		int c=0;
		for (final Join<Item, Product> x : q) {
			++c;
			if (c<64) System.err.println(x);
			x.l.getItemid();
		}
		assertEquals(c1 * c2,  c);
    }

	public void testLeftJoinWithInnerQuery() throws SQLException {
		printTestName();
		final long c1 = Item.ALL.count();
		final Query<Join<Item, Supplier>> q = Item.ALL.leftJoin(Supplier.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		final Query<Join<Item, Supplier>> q2 = Item.ALL.leftJoin(Supplier.ALL, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1, q.count());
		assertEquals(c1, q2.count());
		final List<Join<Item, Supplier>> i1 = q.asList();
		final List<Join<Item, Supplier>> i2 = q2.asList();
		assertEquals(i1.size(), i2.size());
		for (int x=0; x<i1.size(); ++x) {
			final Join<Item, Supplier> a = i1.get(x);
			final Join<Item, Supplier> b = i2.get(x);
			System.err.println("a: "+ a);
			System.err.println("b: "+ b);
			assertNotNull(a);
			assertNotNull(b);
			assertTrue(a.equals(b));
		}
	}

	public void testExplain() throws SQLException {
		printTestName();
		System.err.println(Item.ALL.explainAsText());
	}

	public void testUnion() throws SQLException {
		printTestName();
		List<Item> items = Item.ALL.where(Item.ITEMID.eq("EST-1"))
			.union(Item.ALL.where(Item.ITEMID.eq("EST-10")))
			.asList();
		assertEquals(2, items.size());
		assertEquals("EST-1", items.get(0).getItemid());
		assertEquals("EST-10", items.get(1).getItemid());
	}

	public void testUnionWithDups() throws SQLException {
		printTestName();
		List<Item> items = Item.ALL.where(Item.ITEMID.eq("EST-1"))
			.union(Item.ALL.where(Item.ITEMID.eq("EST-1")))
			.asList();
		assertEquals(1, items.size());
		assertEquals("EST-1", items.get(0).getItemid());
	}

	public void testUnionAll() throws SQLException {
		printTestName();
		List<Item> items = Item.ALL.where(Item.ITEMID.eq("EST-1"))
			.unionAll(Item.ALL.where(Item.ITEMID.eq("EST-1")))
			.asList();
		assertEquals(2, items.size());
		assertEquals("EST-1", items.get(0).getItemid());
		assertEquals("EST-1", items.get(1).getItemid());
	}

	public void testUnionInInnerQuery() throws SQLException {
		printTestName();
		Query<Item> union = Item.ALL.where(Item.ITEMID.eq("EST-1")).onlyFields(Item.ITEMID)
			.union(Item.ALL.where(Item.ITEMID.eq("EST-10")).onlyFields(Item.ITEMID));
		List<Item> items = Item.ALL.where(Item.ITEMID.in(union)).asList();
		assertEquals(2, items.size());
		assertEquals("EST-1", items.get(0).getItemid());
		assertEquals("EST-10", items.get(1).getItemid());
	}

	public void testUnionSharedOnlyFields() throws SQLException {
		printTestName();
		Query<Item> union = Item.ALL.where(Item.ITEMID.eq("EST-1"))
				.union(Item.ALL.where(Item.ITEMID.eq("EST-10")))
				.onlyFields(Item.ITEMID);
			List<Item> items = Item.ALL.where(Item.ITEMID.in(union)).asList();
			assertEquals(2, items.size());
			assertEquals("EST-1", items.get(0).getItemid());
			assertEquals("EST-10", items.get(1).getItemid());
	}

	public void testGroupBy() throws SQLException {
		printTestName();
		List<Item> items = Item.ALL.distinct().groupBy(Item.SUPPLIER).asList();
		assertEquals(Supplier.ALL.count(), items.size());
	}

	public void testGroupByCount() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.alsoSelect(COUNT(1)).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT(1)));
		}
	}
	
	public void testGroupByCountColumn() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.alsoSelect(COUNT(Item.ITEMID)).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT(Item.ITEMID)));
		}
	}

	public void testGroupByCountStar() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.alsoSelect(COUNT("*")).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT("*")));
		}
	}

}
