import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.nosco.Util;


import junit.framework.TestCase;
import noscodb.mozilla.Bug;
import noscodb.mozilla.Classification;
import noscodb.mozilla.Product;
import noscodb.mozilla.Profile;


public class TestMozilla extends TestCase {

	public void test01() throws SQLException {
		assertTrue(Product.ALL.size()>0);
	}

	public void test02() throws SQLException {
		Bug o = Bug.ALL.get(Bug.BUG_ID.eq(212657));
		System.out.println(o);
		assertTrue(o != null);
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
			System.out.println(o.getBugId() +" : "+ o.getShortDesc());
		}
	}

	public void test06() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.like("Hello%").not()).limit(50)) {
			System.out.println(o);
		}
	}

	public void test07() throws SQLException {
		for (Bug o : Bug.ALL.where(Bug.SHORT_DESC.neq("Hello world!")).limit(50)) {
			System.out.println(o);
		}
	}

	public void test08() throws SQLException {
		int a = Bug.ALL.where(Bug.SHORT_DESC.like("Hello%")).count();
		int b = Bug.ALL.where(Bug.SHORT_DESC.like("Hello%").not()).count();
		int c = Bug.ALL.count();
		assertTrue(a + b == c);
	}

	public void test10() throws SQLException {
		for (Bug o : Bug.ALL.onlyFields(Bug.SHORT_DESC).top(50)) {
			System.out.println(o.getBugId() +": "+ o.getShortDesc());
		}
	}

	public void test11() throws SQLException {
		for (Product o : Product.ALL.top(10)) {
			System.out.println(o.getClassification());
		}
	}

	public void test13() throws SQLException {
		for (Bug o : Profile.ALL.first().getBugReporterSet()) {
			System.out.println(o);
		}
	}

	public void test14() throws SQLException {
		System.out.println(Bug.ALL.get(Bug.BUG_ID.eq(212657)).getAssignedToProfile().getRealname());
	}
	
	public void test15() throws SQLException {
		Profile assignee = Bug.ALL.get(Bug.BUG_ID.eq(212657)).getAssignedToProfile();
		for (Bug bug : assignee.getBugAssignedToSet().top(50)) {
			System.out.println(bug +" : "+ bug.getShortDesc());
		}
	}

	public void test16() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_ASSIGNED_TO_PROFILE).top(50)) {
			System.out.println(o +" : "+ o.getAssignedToProfile());
		}
	}

	public void test17() throws SQLException {
		assertTrue( Bug.ALL.where(Bug.ASSIGNED_TO.in(
				Profile.ALL.onlyFields(Profile.USERID).where(Profile.USERID.eq(1)))).size()>0 );
	}

	public void test18() throws SQLException {
		int c = 0;
		for (Bug o : Bug.ALL.with(Bug.FK_REPORTER_PROFILE).where(Profile.REALNAME.eq("Derek Anderson"))) {
			System.out.println(o +" : "+ o.getReporterProfile().getRealname());
			++c;
		}
		assertTrue(c > 0);
	}

	public void test19() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_PRODUCT).with(Bug.FK_ASSIGNED_TO_PROFILE).where(Product.ID.eq(1)).limit(10)) {
			System.out.println(o.getBugId() +" : "+ o.getProduct().getName() +" : "+ o.getAssignedToProfile().getRealname());
		}
	}

	public void test20() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_PRODUCT, Product.FK_CLASSIFICATION)
				.where(Classification.NAME.eq("Components")).top(10)) {
			System.out.println(o.getBugId() +" : "+ o.getProduct().getName() +" : "+ o.getProduct().getClassification().getName());
		}
	}

	public void test21() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_REPORTER_PROFILE).with(Bug.FK_QA_CONTACT_PROFILE).top(10)) {
			System.out.println(o.getBugId() +" : "+ o.getReporterProfile().getRealname() +" : "+ o.getQaContactProfile().getRealname());
		}
	}

	public void test22() throws SQLException {
		for (Bug o : Bug.ALL.with(Bug.FK_REPORTER_PROFILE).with(Bug.FK_QA_CONTACT_PROFILE).top(10)
				.where(Profile.USERID.neq(Profile.USERID))) {
			Profile reporterProfile = o.getReporterProfile();
			Profile qaContactProfile = o.getQaContactProfile();
			System.out.println(o.getBugId() +" : "+ reporterProfile.getRealname() +" : "+ qaContactProfile.getRealname());
			assertTrue(reporterProfile.getUserid() != qaContactProfile.getUserid());
		}
	}

	public void test100() throws SQLException {
		int c = 0;
		for (Bug o : Bug.ALL.all()) {
			++c;
		}
		assertTrue(c > 0);
		System.out.println("found "+ c +" bugs");
	}
	
	public void test101() throws SQLException {
		// this is a speed test to baseline test100()
		int c = 0;
		try {
			PreparedStatement ps = Util.conn.prepareStatement("select bugs.cf_colo_site, bugs.target_milestone, bugs.delta_ts, bugs.cclist_accessible, bugs.product_id, bugs.cf_due_date, bugs.cf_status_thunderbird10, bugs.reporter, bugs.creation_ts, bugs.component_id, bugs.cf_blocking_fennec, bugs.cf_blocking_20, bugs.reporter_accessible, bugs.priority, bugs.cf_status_seamonkey25, bugs.cf_status_seamonkey26, bugs.cf_status_seamonkey23, bugs.cf_tracking_firefox10, bugs.cf_status_seamonkey24, bugs.cf_status_seamonkey21, bugs.cf_blocking_fx, bugs.cf_status_seamonkey22, bugs.cf_status_firefox9, bugs.cf_status_firefox8, bugs.cf_blocking_191, bugs.cf_blocking_192, bugs.cf_blocking_thunderbird32, bugs.short_desc, bugs.cf_blocking_thunderbird33, bugs.cf_blocking_thunderbird30, bugs.cf_blocking_thunderbird31, bugs.cf_office, bugs.cf_tracking_thunderbird10, bugs.cf_blocking_seamonkey21, bugs.status_whiteboard, bugs.deadline, bugs.cf_status_firefox5, bugs.cf_status_firefox6, bugs.cf_status_firefox7, bugs.cf_status_thunderbird30, bugs.cf_status_thunderbird31, bugs.cf_status_thunderbird32, bugs.cf_status_thunderbird33, bugs.votes, bugs.everconfirmed, bugs.resolution, bugs.bug_id, bugs.version, bugs.op_sys, bugs.cf_status_192, bugs.cf_status_191, bugs.cf_status_firefox10, bugs.lastdiffed, bugs.qa_contact, bugs.cf_crash_signature, bugs.bug_status, bugs.alias, bugs.bug_file_loc, bugs.cf_status_thunderbird9, bugs.cf_status_20, bugs.cf_status_thunderbird8, bugs.cf_status_thunderbird7, bugs.cf_status_thunderbird6, bugs.remaining_time, bugs.bug_severity, bugs.assigned_to, bugs.cf_tracking_seamonkey25, bugs.estimated_time, bugs.cf_tracking_seamonkey24, bugs.cf_tracking_thunderbird6, bugs.cf_tracking_thunderbird7, bugs.cf_tracking_seamonkey26, bugs.cf_tracking_thunderbird8, bugs.cf_tracking_thunderbird9, bugs.cf_tracking_firefox5, bugs.cf_tracking_firefox6, bugs.cf_tracking_seamonkey23, bugs.cf_tracking_firefox7, bugs.cf_tracking_firefox8, bugs.cf_tracking_seamonkey22, bugs.cf_tracking_firefox9, bugs.rep_platform from mozilla.bugs bugs");
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while(rs.next()) {
				Object[] row = new Object[82];
				for (int i=0; i<row.length; ++i) {
					row[i] = rs.getObject(i+1);
				}
				++c;
			}
			assertTrue(c > 0);
			System.out.println("found "+ c +" bugs");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
