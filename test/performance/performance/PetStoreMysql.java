package performance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.nosco.Bulk;
import org.nosco.unittest.nosco_test_jpetstore.Category;
import org.nosco.unittest.nosco_test_jpetstore.Item;
import org.nosco.unittest.nosco_test_jpetstore.Product;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class PetStoreMysql {
	
	final static int CREATE_COUNT = 40000;
	final static int RUNS = 20;

	final static MysqlDataSource ds = new MysqlDataSource();
	static {
		ds.setUser("jpetstore");
		ds.setPassword("");
		ds.setDatabaseName("nosco_test_jpetstore");
		ds.setServerName("serenity");
	}
	final static String catid = Category.ALL.use(ds).first().getCatid();

	final static Iterable<Product> products = new Iterable<Product>() {

		@Override
		public Iterator<Product> iterator() {
			return new Iterator<Product>() {
				int count = 0;
				@Override
				public boolean hasNext() {
					return count < CREATE_COUNT;
				}
				@Override
				public Product next() {
					final Product p = new Product();
					p.setProductid(Integer.toString(++count));
					p.setCategory(catid);
					return p;
				}
				@Override
				public void remove() {}
			};
		}
	};
	

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(final String[] args) throws SQLException {
		//calcStatsCreate();
		calcStatsSelect();
	}

	private static double calcAvg(final List<Double> runs) {
		Collections.sort(runs);
		double sum = 0;
		for (int i=1; i<runs.size(); ++i) {
			sum += runs.get(i);
		}
		return sum / (runs.size()-1);
	}

	private static void calcStatsCreate() throws SQLException {
		final List<Double> testJDBCCreateRuns = new ArrayList<Double>();
		final List<Double> testNoscoCreateRuns = new ArrayList<Double>();
		while (testJDBCCreateRuns.size() < RUNS || testNoscoCreateRuns.size() < RUNS) {
			clean();
			if (Math.random() < .5) {
				if (testJDBCCreateRuns.size() < RUNS) testJDBCCreateRuns.add(testJDBCCreate());
			} else {
				if (testNoscoCreateRuns.size() < RUNS) testNoscoCreateRuns.add(testNoscoCreate());
			}
		}
		final double testJDBCCreateAvg = calcAvg(testJDBCCreateRuns);
		final double testNoscoCreateAvg = calcAvg(testNoscoCreateRuns);
		final double diff = 100*testNoscoCreateAvg/testJDBCCreateAvg;
		System.out.println("testNoscoCreate is "+ diff +"% the speed of testJDBCCreate");
	}

	private static double testJDBCCreate() throws SQLException {
		final long start = System.currentTimeMillis();
		final Connection conn = ds.getConnection();
		final PreparedStatement ps = conn.prepareStatement("insert into nosco_test_jpetstore.product (category,productid) values (?,?)");
		int i = 0;
		for (; i<CREATE_COUNT; ++i) {
			ps.setString(2, Integer.toString(i));
			ps.setString(1, catid);
			ps.addBatch();
			if (i % 64 == 0) {
				ps.executeBatch();
			}
		}
		if (i != 0) {
			ps.executeBatch();
		}
		ps.close();
		conn.close();
		final long stop = System.currentTimeMillis();
		final double rate = CREATE_COUNT/((stop-start)/1000.0);
		System.out.println("testJDBCCreate: "+ (stop-start)/1000 +"s ("+ rate +" per/s)");
		return rate;
	}
	
	private static double testNoscoCreate() throws SQLException {
		final long start = System.currentTimeMillis();
		final Bulk bulk = new Bulk(ds);
		bulk.insertAll(products);
		final long stop = System.currentTimeMillis();
		final double rate = CREATE_COUNT/((stop-start)/1000.0);
		System.out.println("testNoscoCreate: "+ (stop-start)/1000 +"s ("+ rate +" per/s)");
		return rate;
	}

	private static void calcStatsSelect() throws SQLException {
		clean();
		testNoscoCreate();
		final List<Double> testJDBCRuns = new ArrayList<Double>();
		final List<Double> testNoscoRuns = new ArrayList<Double>();
		while (testJDBCRuns.size() < RUNS || testNoscoRuns.size() < RUNS) {
			if (Math.random() < .5) {
				if (testJDBCRuns.size() < RUNS) testJDBCRuns.add(testJDBCSelect());
			} else {
				if (testNoscoRuns.size() < RUNS) testNoscoRuns.add(testNoscoSelect());
			}
		}
		final double testJDBCAvg = calcAvg(testJDBCRuns);
		final double testNoscoAvg = calcAvg(testNoscoRuns);
		final double diff = 100*testNoscoAvg/testJDBCAvg;
		System.out.println("testNoscoSelect is "+ diff +"% the speed of testJDBCSelect");
	}

	private static double testJDBCSelect() throws SQLException {
		int count = 0;
		final long start = System.currentTimeMillis();
		final Connection conn = ds.getConnection();
		final PreparedStatement ps = conn.prepareStatement("select * from nosco_test_jpetstore.product");
		final ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			rs.getString("productid");
			rs.getString("category");
			++count;
		}
		rs.close();
		ps.close();
		conn.close();
		final long stop = System.currentTimeMillis();
		final double rate = CREATE_COUNT/((stop-start)/1000.0);
		System.out.println("testJDBCSelect: "+ (stop-start)/1000 +"s ("+ rate +" per/s)");
		if (count != CREATE_COUNT) System.err.println("count != CREATE_COUNT "+ count +" "+ CREATE_COUNT);
		return rate;
	}
	
	private static double testNoscoSelect() throws SQLException {
		int count = 0;
		final long start = System.currentTimeMillis();
		for (final Product p : Product.ALL.use(ds)) {
			p.getProductid();
			p.getCategory();
			++count;
		}
		final long stop = System.currentTimeMillis();
		final double rate = CREATE_COUNT/((stop-start)/1000.0);
		System.out.println("testNoscoSelect: "+ (stop-start)/1000 +"s ("+ rate +" per/s)");
		if (count != CREATE_COUNT) System.err.println("count != CREATE_COUNT "+ count +" "+ CREATE_COUNT);
		return rate;
	}

	private static void clean() throws SQLException {
		Item.ALL.use(ds).delete();
		Product.ALL.use(ds).delete();
	}

}
