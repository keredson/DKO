import java.sql.Timestamp;
import java.util.Date;

import org.nosco.Query;

import db.mysite.Author;
import db.mysite.Blog;
import db.mysite.Entry;
import db.mysite.EntryAuthor;

class Introduction {
	public static void main(String[] args) throws java.sql.SQLException {

/*		Blog.ALL.deleteAll(); //*/

/*		Blog b1 = new Blog();
		b1.setName("Kered.org");
		b1.setTagline("Where a kid can be a kid...");
		b1.save(); //*/

/*		Blog b2 = new Blog();
		b2.setName("Sam I Am");
		b2.setTagline("For the serious blog connoisseur...");
		b2.save(); //*/

		for (Blog blog : Blog.ALL) {
			System.out.println(blog);
		}

		for (Blog blog : Blog.ALL) {
			System.out.println(blog.getId() +": "+ blog.getName());
			System.out.println(blog.getTagline());
		}

		System.out.println(Blog.ALL.count());

		Query<Blog> kidFriendly = Blog.ALL.where(Blog.TAGLINE.like("%kid%"));
		for (Blog blog : kidFriendly) {
			System.out.println(blog.getName());
		}

		Query<Blog> notKidFriendly = Blog.ALL.where(Blog.TAGLINE.like("%kid%").not());
		for (Blog blog : notKidFriendly) {
			System.out.println(blog.getName());
		}

		assert kidFriendly.count() + notKidFriendly.count() == Blog.ALL.count();

		System.out.println(Blog.ALL.get(Blog.ID.eq(7)));

/*		Author a = new Author();
		a.setName("Derek Anderson");
		a.setEmail("afakeone@kered.org");
		a.save();

/*		Author a2 = new Author();
		a2.setName("Curtis Anderson");
		a2.setEmail("another@kered.org");
		a2.save(); //*/
		
		Blog kered = Blog.ALL.get(Blog.ID.eq(7));
/*		Entry e1 = new Entry();
		e1.setBlogIdFK(kered);
		e1.setHeadline("Hello World!");
		e1.setBodyText("This is my first blog entry...");
		e1.setPublishDate(new Timestamp(new Date().getTime()));
		e1.save(); //*/

/*		Entry e2 = new Entry();
		e2.setBlogIdFK(kered);
		e2.setHeadline("A second note from Derek");
		e2.setBodyText("I wish i had something more interesting to say...");
		e2.setPublishDate(new Timestamp(new Date().getTime()));
		e2.save(); //*/

/*		Entry e3 = new Entry();
		e3.setBlogId(8);
		e3.setHeadline("Hello World Too!");
		e3.setBodyText("This one is from Curtis...");
		e3.setPublishDate(new Timestamp(new Date().getTime()));
		e3.save(); //*/

		for (Entry e : Entry.ALL) {
		    System.out.println(e.getId() +": "+ e.getHeadline()
			    +" in blog "+ e.getBlogIdFK().getName());
		}

		for (Entry e : Entry.ALL.with(Entry.FK_BLOG)) {
		    System.out.println(e.getId() +": "+ e.getHeadline()
			    +" in blog "+ e.getBlogIdFK().getName());
		}

		for (Entry e : Entry.ALL
			.with(Entry.FK_BLOG)
			.onlyFields(Entry.ID, Entry.HEADLINE, Blog.NAME)) {
		    System.out.println(e.getId() +": "+ e.getHeadline()
			    +" in blog "+ e.getBlogIdFK().getName());
		}

		Blog keredOrg = Blog.ALL.get(Blog.ID.eq(7));
		for (Entry e : keredOrg.getEntryBlogIdSet()) {
		    System.out.println(e +" "+ e.getHeadline());
		}
		Timestamp time = new Timestamp(111, 9, 19, 11, 30, 0, 0);
		for (Entry e : keredOrg.getEntryBlogIdSet()
			.where(Entry.PUBLISH_DATE.gt(time))) {
		    System.out.println(e +" "+ e.getPublishDate());
		}
		
/*		EntryAuthor ea = new EntryAuthor();
		ea.setAuthorId(1);
		ea.setEntryIdFK(e1);
		ea.save();
		EntryAuthor ea2 = new EntryAuthor();
		ea2.setAuthorId(1);
		ea2.setEntryId(2);
		ea2.save();
		EntryAuthor ea3 = new EntryAuthor();
		ea3.setAuthorId(2);
		ea3.setEntryIdFK(e3);
		ea3.save(); //*/
		
		for (Entry e : Entry.ALL.where(Entry.ID.in(
				EntryAuthor.ALL
					.with(EntryAuthor.FK_AUTHOR)
					.where(Author.EMAIL.like("%fake%"))
					.onlyFields(EntryAuthor.ENTRY_ID)))) {
			System.out.println(e);
		}
		

	}
}
