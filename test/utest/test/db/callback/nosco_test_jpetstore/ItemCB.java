package test.db.callback.nosco_test_jpetstore;

import javax.sql.DataSource;

import org.kered.dko.unittest.nosco_test_jpetstore.Item;

public class ItemCB {

	public static int preUpdate;
	public static int postUpdate;

	public static void preUpdate(final Item[] items, final DataSource ds) {
		//System.err.println("preUpdate");
		preUpdate += items.length;
	}

	public static void postUpdate(final Item[] items, final DataSource ds) {
		//System.err.println("postUpdate");
		postUpdate += items.length;
	}

	public static String toString(final Item item) {
		return item.toStringSimple() +"/CB";
	}

}
