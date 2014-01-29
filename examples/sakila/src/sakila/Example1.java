package sakila;
import org.kered.dko.Query;

import com.mycompany.dko.sakila.Actor;
import com.mycompany.dko.sakila.Film;
import com.mycompany.dko.sakila.FilmActor;
import com.mycompany.dko.sakila.Language;


public class Example1 {
	
	public static void main(String[] args) {
		
		Query<Actor> actors = Actor.ALL
				.with(FilmActor.FK_ACTOR, FilmActor.FK_FILM, Film.FK_LANGUAGE)
				.where(Actor.LAST_NAME.like("H%"))
				.where(Film.LENGTH.lt(100));
		
		for (Actor actor : actors) {
			System.out.println(actor);
			for (FilmActor filmActor : actor.getFilmActorSet()) {
				Film film = filmActor.getFilmIdFK();
				Language language = film.getLanguageIdFK();
				System.out.println("\t" + film.getTitle() +" ("+ film.getLength() +"m in "+ language.getName() +")");
			}
		}
		
	}
	
}
