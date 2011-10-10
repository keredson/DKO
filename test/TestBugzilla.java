import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import noscodb.bugzilla3.Bug;
import noscodb.bugzilla3.Classification;
import noscodb.bugzilla3.Component;
import noscodb.bugzilla3.Product;
import noscodb.bugzilla3.Profile;

import junit.framework.TestCase;


public class TestBugzilla extends TestCase {

	public void test01() throws SQLException {
		assertTrue(Product.ALL.size()>0);
	}

	public void test02() throws SQLException {
		System.out.println(Bug.ALL.get(Bug.BUG_ID.eq(2)));
	}

	public void test03() throws SQLException {
		boolean gotSomething = false;
		for (Product o : Product.ALL.all()) {
			System.out.println(o);
			gotSomething = true;
		}
		assertTrue(gotSomething);
	}

	public void test04() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.eq("Hello world!"))) {
			System.out.println(o);
		}
	}

	public void test05() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.like("Hello%"))) {
			System.out.println(o);
		}
	}

	public void test06() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.like("Hello%").not())) {
			System.out.println(o);
		}
	}

	public void test07() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.neq("Hello world!"))) {
			System.out.println(o);
		}
	}

	public void test08() throws SQLException {
		for (Bug o : Bug.ALL.all()) {
			System.out.println(o.getBugId() +": "+ o.getShortDesc());
		}
	}

	public void test09() throws SQLException {
		String x = "_X";
		for (Bug o : Bug.ALL.top(10)) {
			String s = o.getAlias();
			//System.out.println(o.getBugId() +": "+ s);
			o.setAlias(s+x);
			assertTrue(o.save());
			assertTrue(Bug.ALL.get(Bug.BUG_ID.eq(o.getBugId())).getAlias().equals(s+x));
			o.setAlias(s);
			assertTrue(o.save());
		}
	}

	public void test10() throws SQLException {
		for (Bug o : Bug.ALL.onlyFields(Bug.SHORT_DESC)) {
			System.out.println(o.getBugId() +": "+ o.getShortDesc());
		}
	}

	public void test11() throws SQLException {
		for (Product o : Product.ALL) {
			System.out.println(o.getClassification());
		}
	}

	public void test12() throws SQLException {
		Bug o = new Bug();
		o.setShortDesc("this is a bug");
		o.setAssignedToProfile(Profile.ALL.get(Bug.BUG_ID.eq(1)));
		o.setBugSeverity("");
		o.setBugStatus("NEW");
		o.setDeltaTs(new Timestamp(0));
		o.setOpSys("");
		o.setPriority("");
		o.setProduct(Product.ALL.first());
		o.setRepPlatform("");
		o.setReporterProfile(Profile.ALL.first());
		o.setVersion("");
		o.setComponent(Component.ALL.first());
		o.setEverconfirmed(0);
		assertTrue(o.save());
		System.out.println(o);
	}

	public void test13() throws SQLException {
		for (Bug o : Profile.ALL.first().getBugReporterSet()) {
			System.out.println(o);
		}
	}

	public void test14() throws SQLException {
		System.out.println(Bug.ALL.get(Bug.BUG_ID.eq(1)).getAssignedToProfile().getRealname());
	}
	public void test15() throws SQLException {
		Profile assignee = Bug.ALL.get(Bug.BUG_ID.eq(1)).getAssignedToProfile();
		for (Bug bug : assignee.getBugAssignedToSet()) {
			System.out.println(bug +" : "+ bug.getShortDesc());
		}
	}

	public void test16() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_ASSIGNED_TO_PROFILE)) {
			System.out.println(o +" : "+ o.getAssignedToProfile());
		}
	}

	public void test17() throws SQLException {
		assertTrue( Bug.ALL.where(Bug.ASSIGNED_TO.in(
				Profile.ALL.onlyFields(Profile.USERID).where(Profile.USERID.eq(1)))).size()>0 );
	}

	public void test18() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_ASSIGNED_TO_PROFILE).where(Profile.LOGIN_NAME.eq("Derek Anderson"))) {
			System.out.println(o +" : "+ o.getAssignedToProfile());
		}
	}

	public void test20() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_PRODUCT, Product.FK_CLASSIFICATION)
				.where(Classification.NAME.eq("aclass"))) {
			System.out.println(o +" : "+ o.getProduct().getClassification());
		}
	}

}
