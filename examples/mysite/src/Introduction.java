import org.nosco.Query;

import db.mysite.Blog;

class Introduction {
	public static void main(String[] args) throws java.sql.SQLException {
		
/*		Blog.ALL.deleteAll(); //*/

/*		Blog b = new Blog();
		b.setName("Kered.org");
		b.setTagline("Where a kid can be a kid...");
		b.save(); //*/

/*		Blog b = new Blog();
		b.setName("Sam I Am");
		b.setTagline("For the serious blog connoisseur...");
		b.save(); //*/
		
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
}
}
