package org.nosco;
import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.sql.SQLException;
import java.util.Map;

import junit.framework.TestCase;

public class Test extends TestCase {
/*

    public Test(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCreate() throws Exception {
		Blog blog = new Blog();
		blog.setName("Kered.org");
		blog.setTagline("Where have all the Dereks gone?");
		blog.save();
		assertTrue(blog.getId()>0);
    }

	*//**
	 * @param args
	 * @throws SQLException 
	 *//*
	public static void testAll() throws Exception {
		// TODO Auto-generated method stub

		System.out.println("Blog.objects.deleteAll(): "+ Blog.objects.deleteAll());
		System.out.println("Author.objects.deleteAll(): "+ Author.objects.deleteAll());
		System.out.println("Entry.objects.deleteAll(): "+ Entry.objects.deleteAll());
		
		for (int i=0; i<5; ++i) {
			Blog blog = new Blog();
			blog.setName("Kered.org #"+ (5-i));
			blog.setTagline("Tagline #"+(i+1));
			blog.save();
			System.out.println(blog);
		}

		for (int i=0; i<5; ++i) {
			Author author = new Author();
			author.setName("Derek Anderson #"+ (5-i));
			author.setEmail("email #"+(i+1));
			author.save();
			System.out.println(author);
		}

		for (int i=0; i<5; ++i) {
			Entry entry = new Entry();
			entry.setHeadline("Headline #"+ (i+1));
			if (i==2) {
				entry.setBlog(Blog.objects.first());
			}
			entry.save();
			System.out.println(entry);
		}

		System.out.println("Blog.objects.all(): "+ Blog.objects.all());

		System.out.println("Blog.objects.none(): "+ Blog.objects.none());

		System.out.println("Blog.objects.count(): "+ Blog.objects.count());

		System.out.println("Blog.objects.get(45): "+ Blog.objects.get(45));

		System.out.println(Blog.objects.latest(Blog.NAME));

		System.out.println(Blog.objects.stats(Blog.NAME));

		System.out.println("Blog.objects.isEmpty(): "+ Blog.objects.isEmpty());

		System.out.println("Blog.objects.filter(Blog.NAME, EQUALS, 'a name'):");
		for (Blog blog : Blog.objects.filter(Blog.NAME, EQUALS, "a name")) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects
				.filter(Blog.NAME, EQUALS, "a name")
				.filter(Blog.TAGLINE, EQUALS, "woot")) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.filter(Blog.TAGLINE, "Tagline #4")) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.exclude(Blog.NAME, EQUALS, "Kered.org #3")) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.orderBy(Blog.NAME)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.orderBy(DESCENDING, Blog.NAME)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.orderBy(DESCENDING, Blog.NAME, Blog.TAGLINE)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.orderBy(DESCENDING, Blog.NAME).orderBy(Blog.TAGLINE)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.distinct()) {
			System.out.println("\t"+ blog);
		}

		for (Map<Field,Object> map : Blog.objects.maps()) {
			System.out.println("\t"+ map);
		}

		for (Blog blog : Blog.objects.fetchRelated(Blog.class)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.deferFields(Blog.NAME)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.onlyFields(Blog.NAME)) {
			System.out.println("\t"+ blog);
		}

		for (Blog blog : Blog.objects.filter(Blog.NAME, EQUALS, "Kered.org #3")) {
			blog.setTagline("an updated tagline!");
			blog.save();
			System.out.println("\t"+ blog);
			System.out.println("\t"+ Blog.objects.get(blog.getId()));
		}

		System.out.println("Author.objects.all():");
		for (Author author : Author.objects.all()) {
			System.out.println("\t"+ author);
		}

		System.out.println("Entry.objects.all():");
		for (Entry o : Entry.objects.all()) {
			System.out.println("\t"+ o +"\n\t\t"+ o.getBlog() +"\t"+ o.getBlog());
		}

		for (Blog blog : Blog.objects.all()) {
			System.out.println("\t"+ blog);
			for (Entry o : blog.entrySet()) {
				System.out.println("\t\t"+ o);
			}
		}

	}
*/

}
