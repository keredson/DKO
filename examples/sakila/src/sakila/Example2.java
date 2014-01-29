package sakila;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.mycompany.dko.sakila.Film;


public class Example2 {
	
	public static void main(String[] args) throws SQLException {
		
		Map<String, Collection<Film>> m = Film.ALL
				.orderBy(Film.RATING.desc())
				.collectBy(Film.RATING);
		
		for (Entry<String, Collection<Film>> e : m.entrySet()) {
			String key = e.getKey();
			System.out.println(key);
			for (Film f : e.getValue()) {
				System.out.println("\t"+ f.getRating() +"\t"+ f.getTitle());
			}
		}
		
	}
	
}
