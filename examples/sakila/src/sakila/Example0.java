package sakila;
import org.kered.dko.Query;

import com.mycompany.dko.sakila.Actor;


public class Example0 {
	
	public static void main(String[] args) {
		
		Query<Actor> actors = Actor.ALL.limit(10);
		
		for (Actor actor : actors) {
			System.out.println(actor.getFirstName() +" "+ actor.getLastName());
		}
		
	}
	
}
