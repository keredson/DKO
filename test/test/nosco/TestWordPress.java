package test.nosco;
import java.sql.SQLException;

import ddaoo.wordpress.Post;
import junit.framework.TestCase;


public class TestWordPress extends TestCase {
	
	public void test01() throws SQLException {
		this.assertTrue(Post.ALL.size()>0);
	}

	public void test02() throws SQLException {
		System.out.println(Post.ALL.get(2));
	}

	public void test03() throws SQLException {
		boolean gotSomething = false;
		for (Post o : Post.ALL.all()) {
			System.out.println(o);
			gotSomething = true;
		}
		this.assertTrue(gotSomething);
	}

	public void test04() throws SQLException {
		for (Post o : Post.ALL.where(Post.POST_TITLE.eq("Hello world!"))) {
			System.out.println(o);
		}
	}

	public void test05() throws SQLException {
		for (Post o : Post.ALL.where(Post.POST_TITLE.like("Hello%"))) {
			System.out.println(o);
		}
	}

	public void test06() throws SQLException {
		for (Post o : Post.ALL.where(Post.POST_TITLE.like("Hello%").not())) {
			System.out.println(o);
		}
	}

	public void test07() throws SQLException {
		for (Post o : Post.ALL.where(Post.POST_TITLE.neq("Hello world!"))) {
			System.out.println(o);
		}
	}

	public void test08() throws SQLException {
		for (Post o : Post.ALL.all()) {
			System.out.println(o.getId() +": "+ o.getPostTitle());
		}
	}

	public void test09() throws SQLException {
		String x = "_X";
		for (Post o : Post.ALL.all()) {
			String s = o.getPostStatus();
			System.out.println(o.getId() +": "+ s);
			o.setPostStatus(s+x);
			o.setCommentCount(o.getCommentCount()+1);
			o.save();
			assertTrue(o.getPostStatus().equals(s+x));
			o.setPostStatus(s);
			o.setCommentCount(o.getCommentCount()-1);
			o.save();
		}
	}

	public void test10() throws SQLException {
		for (Post o : Post.ALL.onlyFields(Post.POST_NAME)) {
			System.out.println(o.getId() +": "+ o.getPostTitle());
		}
	}

}
