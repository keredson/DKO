package test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.kered.docbook.Person;

public class DefaultDataSource {

	static DataSource ds = new org.kered.dko.datasource.JDBCDriverDataSource("jdbc:sqlite:bin/thebook.db");

	static {
		try {
			Person.ALL.delete();
			final Person lingyan = new Person().setFirstName("Lingyan").setLastName("Anderson");
			lingyan.insert();
			final Person derek = new Person().setFirstName("Derek").setLastName("Anderson");
			derek.insert();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static DataSource get() {
		return ds;
	}

}
