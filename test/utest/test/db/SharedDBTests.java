package test.db;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.kered.dko.Bulk;
import org.kered.dko.CSV;
import org.kered.dko.Condition;
import org.kered.dko.Constants;
import org.kered.dko.Context;
import org.kered.dko.Diff;
import org.kered.dko.Field;
import org.kered.dko.Join;
import org.kered.dko.Query;
import org.kered.dko.QueryFactory;
import org.kered.dko.Table;
import org.kered.dko.Constants.CALENDAR;
import org.kered.dko.Context.Undoer;
import org.kered.dko.Diff.RowChange;
import org.kered.dko.Join.J2;
import org.kered.dko.Join.J3;
import org.kered.dko.Join.J4;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.unittest.nosco_test_jpetstore.Account;
import org.kered.dko.unittest.nosco_test_jpetstore.Category;
import org.kered.dko.unittest.nosco_test_jpetstore.Inventory;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;
import org.kered.dko.unittest.nosco_test_jpetstore.Orderstatus;
import org.kered.dko.unittest.nosco_test_jpetstore.Product;
import org.kered.dko.unittest.nosco_test_jpetstore.Supplier;

//import static org.kered.dko.Function.*;
import static org.kered.dko.SQLFunction.*;

import test.db.callback.nosco_test_jpetstore.ItemCB;


public class SharedDBTests extends TestCase {

	DataSource ds = null;
	ConnectionCountingDataSource ccds = null;

	public void test01() throws SQLException {
		final Connection conn = ds.getConnection();
		final Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("select count(1) from inventory");
		rs.next();
		final int count = rs.getInt(1);
		//System.err.println("count: "+ count);
		rs.close();
		stmt.close();
		conn.close();
		assertTrue(count > 0);
	}

	public void test02() throws SQLException {
		assertTrue(Item.ALL.use(ds).count() > 0);
	}

	public void test04() throws SQLException {
		int count = 0;
		for (final Item i : Item.ALL.use(ds).top(10)) ++count;
		assertEquals(10, count);
	}

	public void testWithAndCross() throws SQLException {
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

	public void testFKNoWith() throws Exception {
		final Undoer x = Context.getVMContext().setDataSource(ccds);
		final ClassLoader classLoader = this.getClass().getClassLoader();
		final Class<?> clz = classLoader.loadClass("org.kered.dko.UsageMonitor");
		final java.lang.reflect.Field field = clz.getDeclaredField("warnBadFKUsageCount");
		field.setAccessible(true);
		final java.lang.reflect.Field __NOSCO_USAGE_MONITOR = Table.class.getDeclaredField("__NOSCO_USAGE_MONITOR");
		__NOSCO_USAGE_MONITOR.setAccessible(true);
		final Method warnBadFKUsage = clz.getDeclaredMethod("warnBadFKUsage");
		warnBadFKUsage.setAccessible(true);
		Object um = null;
		final long warnBadFKUsageCountPre = field.getLong(null);
		for (final Item item : Item.ALL) { //.with(Item.FK_SUPPLIER)
			// this should create O(n) queries because we didn't specify with() above
			item.getSupplierFK();
			if (um == null) um = __NOSCO_USAGE_MONITOR.get(item);
		}
		assertTrue(ccds.getCount() > 1);
		warnBadFKUsage.invoke(um);
		final long warnBadFKUsageCountPost = field.getLong(null);
		assertEquals(warnBadFKUsageCountPre+1, warnBadFKUsageCountPost);
	}

	@SuppressWarnings("unused")
	public void testFKReverseCounts() throws SQLException {
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
		final Query<Supplier> q = Supplier.ALL.with(Item.FK_SUPPLIER);
		q.count();
		for (final Supplier s : q) {
		}
	}

	public void testFKTwoLevels() throws SQLException {
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
		}
		assertEquals(3, count);
		assertEquals(1, updates);
		assertEquals(2, adds);
	}

	public void testDateAdd() throws SQLException {
		Orderstatus.ALL.where(Orderstatus.TIMESTAMP.lt(
				DATEADD(Orderstatus.TIMESTAMP, 1, CALENDAR.DAY)))
				.asList();
	}

	public void testRecommendation() throws InterruptedException {
		for (final Item i : Item.ALL) {
			i.getProductidFK();
		}
		// note: this should raise a logging WARNING!
		System.gc();
		Thread.sleep(1000);
		// this doesn't work for now since it's the last test
	}

	public void testExists() throws InterruptedException {
		for (final Item i : Item.ALL.where(Item.ALL.exists())) {}
	}

	public void testConcat() throws SQLException {
		assertEquals(1, Item.ALL.where(CONCAT(Item.ITEMID, "!").eq("EST-20!")).count());
	}

	public void testAdd() throws SQLException {
		assertEquals(1, Inventory.ALL.where(Inventory.ITEMID.eq("EST-14"))
				.where(Inventory.QTY.add(0).eq(Inventory.QTY)).count());
	}

	public void testObjectArray() throws SQLException {
		final long count = Item.ALL.count();
		int count2 = 0;
		for (final Object[] oa : Item.ALL.asIterableOfObjectArrays()) {
			count2 += 1;
		}
		assertEquals(count, count2);
	}

    public void testTransaction() throws SQLException {
    	Context.getThreadContext().startTransaction(ds);
    	Item.ALL.get(Item.ITEMID.eq("EST-20"));
    	Context.getThreadContext().commitTransaction(ds);
    	Item.ALL.get(Item.ITEMID.eq("EST-20"));
    }

    public void testTopWithToManyRelationship() throws SQLException {
    	final Product p = Product.ALL.with(Item.FK_PRODUCTID_PRODUCT)
    			.where(Product.PRODUCTID.eq("FL-DSH-01"))
    			.first();
    	assertEquals(2, p.getItemSet().count());
    	assertEquals(1, Product.ALL.with(Item.FK_PRODUCTID_PRODUCT).top(1).asList().size());
    }

    public void testBulkInsert() throws SQLException {
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
    	final long count = Item.ALL.count();
    	final List<Item> items = Item.ALL.asList();
		for (final Item item : items) {
    		item.setAttr2("woot2");
    		if (Math.random() > .5) {
    			item.setAttr3("woot3");
    		}
    		if (Math.random() > .5) {
    			item.setAttr4("woot4");
    		}
    	}
    	final Bulk bulk = new Bulk(ds);
    	final long ret = bulk.updateAll(items);
    	assertEquals(count, ret);
    }

    public void testBulkInsertOrUpdate() throws SQLException {
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
    	final Item item = Item.ALL.first();
    	ItemCB.preUpdate = 0;
    	ItemCB.postUpdate = 0;
    	item.setAttr3("woot3");
    	item.update();
    	assertEquals(1, ItemCB.preUpdate);
    	assertEquals(1, ItemCB.postUpdate);
    }

    public void testCallbacksBulk() throws SQLException {
    	System.err.println("testCallbacksBulk");
    	final Item item = Item.ALL.first();
    	item.setAttr3("woot3");
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
    	CSV.write(Item.ALL, new java.io.File("bin/items.csv"));
    }

    public void testReadCSV() throws Exception {
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
    	assertEquals(0, diff.size());
    }

    public void testWarningsOff() throws Exception {
    	final Undoer u = Context.getVMContext().enableUsageWarnings(false);
    	for (final Item x : Item.ALL) {}
    	u.undo();
    }

    public void testWarningsOff2() throws Exception {
    	for (final Object[] x : Item.ALL.asIterableOfObjectArrays()) {}
    	System.gc();
    }

    public void testFieldInField() throws Exception {
    	Item.ALL.where(Item.ATTR1.in(Item.ATTR1, Item.ATTR2)).asList();
    }

    public void testCrossColumnSelects() throws Exception {
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
    	Item.ALL.where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
    }

    public void testBetweenFieldsInMemory() throws Exception {
    	final List<Item> orig = Item.ALL.where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
    	final List<Item> inMem = Item.ALL.toMemory().where(Item.ATTR1.between(Item.ATTR1, Item.ATTR2)).asList();
    	assertEquals(orig.size(), inMem.size());
    }

    public void testMapBy2() throws Exception {
    	final Map<String, Map<String, Item>> x = Item.ALL.mapBy(Item.ATTR2, Item.ATTR1);
    	for (final Entry<String, Map<String, Item>> e : x.entrySet()) {
    		for (final Entry<String, Item> e2 : e.getValue().entrySet()) {
    			System.err.println(e.getKey() +" "+ e2.getKey() + " "+ e2.getValue());
    		}
    	}
    	assertTrue(x.size() > 0);
    }

    public void testCollectBy2() throws Exception {
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
		final Query<Item> q = Item.ALL.onlyFields(Item.ATTR5, Item.ATTR3, Item.ATTR1, Item.LISTPRICE);
		final List<Field<?>> fields = q.getSelectFields();
		assertEquals(Item.ATTR5, fields.get(0));
		assertEquals(Item.ATTR3, fields.get(1));
		assertEquals(Item.ATTR1, fields.get(2));
		assertEquals(Item.LISTPRICE, fields.get(3));
	}

	public void testJoin() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<Item> q = Item.ALL.crossJoin(Product.class);
		assertEquals(c1 * c2,  q.count());
		for (final Item x : q.top(64)) {
			System.err.println(x);
			x.getItemid();
		}
	}

	public void test2Joins() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final Query<Item> q = Item.ALL.crossJoin(Product.class).crossJoin(Category.class);
		assertEquals(c1 * c2 * c3,  q.count());
		for (final Item x : q.top(64)) {
			System.err.println(x);
			x.getItemid();
		}
	}

	public void test3Joins() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final long c4 = Account.ALL.count();
		final Query<Item> q = Item.ALL.crossJoin(Product.class)
				.crossJoin(Category.class).crossJoin(Account.class);
		assertEquals(c1 * c2 * c3 * c4,  q.count());
		for (final Item x : q.top(64)) {
			System.err.println(x);
			x.getItemid();
		}
	}

	public void test3JoinsAlt() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
		final long c4 = Account.ALL.count();
		final Query<J4<Item, Product, Category, Account>> q = Join.cross(Join.cross(Join.cross(Item.ALL, Product.class), Category.class), Account.class);
		assertEquals(c1 * c2 * c3 * c4,  q.count());
		for (final J4<Item, Product, Category, Account> x : q.top(64)) {
			System.err.println(x);
			x.t1.getItemid();
			x.t2.getProductid();
			x.t3.getCatid();
			x.t4.getEmail();
		}
	}

	public void testJoinAlias() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<J2<Item, Product>> q = Join.cross(Item.class, Product.as("pddt"));
		assertEquals(c1 * c2,  q.count());
		for (final J2<Item, Product> x : q) {
			x.t1.getItemid();
			x.t2.getProductid();
		}
	}

	public void testJoinAliasSelf() throws SQLException {
		final long c1 = Item.ALL.count();
		final Query<J2<Item, Item>> q = Join.inner(Item.class, Item.as("pddt"), Item.ITEMID.eq(Item.ITEMID.from("pddt")));
		assertEquals(c1,  q.count());
		for (final J2<Item, Item> x : q) {
			System.err.println(x);
			assertEquals(x.t1.getItemid(), x.t2.getItemid());
		}
	}

	public void testLeftJoin() throws SQLException {
		final long c1 = Item.ALL.count();
		final Query<J2<Item, Supplier>> q = Join.left(Item.class, Supplier.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final J2<Item, Supplier> x : q.top(64)) {
			System.err.println(x);
		}
	}

	public void testRightJoin() throws SQLException {
		final long c1 = Item.ALL.count();
		final Query<J2<Supplier, Item>> q = Join.right(Supplier.class, Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1,  q.count());
		for (final J2<Supplier, Item> x : q.top(64)) {
			System.err.println(x);
		}
	}

	public void testInnerJoin() throws SQLException {
		final long c1 = Item.ALL.count();
		final Query<J2<Supplier, Item>> q = Join.inner(Supplier.class, Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
		assertEquals(c1-1,  q.count());
		for (final J2<Supplier, Item> x : q.top(64)) {
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
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final Query<J2<Product, Item>> q = Join.left(Product.class, Item.class, Item.PRODUCTID.eq(Product.PRODUCTID));
		assertEquals(c1,  q.count());
		for (final J2<Product, Item> x : q.top(64)) {
			System.err.println(x);
			x.t2.getItemid();
			x.t1.getProductid();
		}
	}

    public void testTableIn() throws SQLException {
		System.err.println("testTableIn");
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
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
    	final Query<J2<Item, Product>> q = Join.cross(Item.ALL, Product.class);
		assertEquals(c1 * c2,  q.count());
    	for (final J2<Item, Product> j : q) {
    		System.err.println(j);
    		j.t1.getItemid();
    		j.t2.getProductid();
    	}
    }

    public void testJ2Inline() throws SQLException {
    	for (final J2<Item, Product> j : Join.cross(Item.ALL, Product.class)) {
    		System.err.println(j);
    		j.t1.getItemid();
    		j.t2.getProductid();
    	}
    }

    public void testJ2Inline2() throws SQLException {
    	final Query<J2<Item, Product>> q = Join.cross(Item.ALL, Product.class).where(Condition.TRUE);
    	for (final J2<Item, Product> j : q) {
    		System.err.println(j);
    		j.t1.getItemid();
    		j.t2.getProductid();
    	}
    }

    public void testJ2Alt() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
    	final Query<J2<Item, Product>> q = Join.cross(Item.class, Product.class);
		assertEquals(c1 * c2,  q.count());
    	for (final J2<Item, Product> j : q) {
    		System.err.println(j);
    		j.t1.getItemid();
    		j.t2.getProductid();
    	}
    }

    public void testJ3() throws SQLException {
		final long c1 = Item.ALL.count();
		final long c2 = Product.ALL.count();
		final long c3 = Category.ALL.count();
    	final Query<J3<Item, Product, Category>> q = Join.cross(Join.cross(Item.ALL, Product.class), Category.class);
		assertEquals(c1 * c2 * c3,  q.count());
    	for (final J3<Item, Product, Category> j : q) {
    		System.err.println(j);
    		j.t1.getItemid();
    		j.t2.getProductid();
    		j.t3.getCatid();
    	}
    }
//
//    public void testJ3() throws SQLException {
//    	final Query<J3<Item, Product, Category>> q2 = Join.leftJoin(Join.leftJoin(Item.class, Product.class), Category.class);
//    	for (final J3<Item, Product, Category> j : q2) {
//    		j.t1.getItemid();
//    		j.t2.getProductid();
//    		j.t3.getCatid();
//    	}
//    }

    public void testNewBetween() throws SQLException {
    	final Query<Item> q = Item.ALL.where(Field.between(15.0, Item.UNITCOST, Item.LISTPRICE));
    	for (final Item j : q) {
    		System.err.println(j.toStringDetailed());
    	}
    	assertEquals(18, q.count());
    }

    public void testNewBetweenWithFunctions() throws SQLException {
    	final Query<Item> q = Item.ALL.where(Field.between(15.0, Item.UNITCOST.add(1.0), Item.LISTPRICE.sub(8.0)));
    	for (final Item j : q) {
    		System.err.println(j.toStringDetailed());
    	}
    	assertEquals(4, q.count());
    }

    public void testBadColumnNames() throws SQLException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
    	final Field f = new Field(0, null, "K%", null, null, null);
    	final Method m = Field.class.getDeclaredMethod("getSQL", StringBuffer.class, Constants.DB_TYPE.class);
    	m.setAccessible(true);
    	final StringBuffer sb = new StringBuffer();
    	m.invoke(f, sb, Constants.DB_TYPE.SQLSERVER);
    	assertEquals("[K%]", sb.toString());
    }

    public void testToStrings() {
    	for (final Account a : Account.ALL) {
    		System.err.println(a);
    		System.err.println(a.toStringDetailed());
    		assertTrue(a.toString().contains("name"));
    		assertTrue(a.toStringDetailed().contains("name"));
    		assertFalse(a.toString().contains("addr1"));
    	}
    	for (final Category a : Category.ALL) {
    		System.err.println(a);
    		System.err.println(a.toStringDetailed());
    		assertTrue(a.toString().contains("name"));
    		assertTrue(a.toStringDetailed().contains("name"));
    		assertFalse(a.toString().contains("descn"));
    	}
    }

    public void testToStringCB() {
    	assertTrue(Item.ALL.first().toString().endsWith("/CB"));
    }

    public void testNewFieldsMethod() {
    	for (final Item item : Item.ALL.onlyFields(Item.ITEMID, Item.UNITCOST)) {
    		assertEquals(2, item.fields().size());
    	}
    }

    public void testExtend() {
    	final Field<Integer> f42 = new Field<Integer>("forty_two", Integer.class);
		final Query<Table> q = QueryFactory.IT.addField(Item.ALL, f42, 42);
		for (final Table t : q) {
			assertNotNull(t.get(Item.ITEMID));
			assertEquals(42, t.get(f42).intValue());
		}
    }

    public void testExtendFunction() {
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

}
