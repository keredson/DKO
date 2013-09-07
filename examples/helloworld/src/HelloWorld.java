import javax.sql.DataSource;

import org.kered.dko.datasource.JDBCDriverDataSource;

import com.mycompany.dko.Person;


public class HelloWorld {
	
	// sqlite3 doesn't provide a DataSource impl, so use ours to wrap any JDBC URL.
	static private JDBCDriverDataSource ds = new JDBCDriverDataSource("jdbc:sqlite:helloworld.sqlite3");
	
	public static DataSource getDS() {
		return ds;
	}
	
	public static void main(String[] args) {
		System.out.println("Where is everyone from?");
		for (Person person : Person.ALL.with(Person.FK_HOME_TOWN_PLACE)) {
			System.out.println(person + " is from "
					+ person.getHomeTownFK().toStringDetailed());
		}
	}

}
