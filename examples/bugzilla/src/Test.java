import db.bugzilla3.Bug;
class Test {
	public static void main(String[] args) throws java.sql.SQLException {
		System.out.println("you have "+ Bug.ALL.count());
	}
}
