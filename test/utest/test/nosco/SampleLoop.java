package test.nosco;

import java.util.logging.Logger;

import org.kered.dko.Query;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SampleLoop {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final MysqlDataSource ds = new MysqlDataSource();
		ds.setUser("root");
		ds.setDatabaseName("nosco_test_jpetstore");
		final Query<Item> items = Item.ALL.use(ds);
		System.err.println("about to start...");
		int count = 0;
		for (final Item item : items) {
			item.getAttr1();
			item.getAttr3();
			++count;
		}
		System.err.println("...done! "+ count);
	}

}
