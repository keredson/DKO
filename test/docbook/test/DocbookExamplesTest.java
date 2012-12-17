package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.kered.dko.Context;
import org.kered.dko.Query;
import org.kered.dko.datasource.SingleConnectionDataSource;
import org.kered.docbook.Appointment;
import org.kered.docbook.Doctor;
import org.kered.docbook.Item;
import org.kered.docbook.Office;
import org.kered.docbook.Patient;
import org.kered.docbook.Purchase;

import junit.framework.TestCase;

public class DocbookExamplesTest extends TestCase {

	private PrintStream out = null;
	PrintStream originalOut = System.out;
	PrintStream originalErr = System.err;
	DataSource ds12345 = DefaultDataSource.get();

	private void init() {
		String method = Thread.currentThread().getStackTrace()[2].getMethodName();
		final File f = new File("gen/docbook/"+method+".out");
		try {
			out = new PrintStream(new FileOutputStream(f));
			System.setOut(out);
			System.setErr(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		if (out!=null) out.close();
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	public void test1() {
		init(); // IGNORE
		for (final Patient patient : Patient.ALL) {
			System.out.println(patient);
		}
	}

	public void test2() {
		init(); // IGNORE
		Doctor doctor = Doctor.ALL.first();
		System.out.println("doctor: " + doctor);
		System.out.println("office: " + doctor.getPrimaryOfficeIdFK());
	}

	public void test3() {
		init(); // IGNORE
		Doctor doctor = Doctor.ALL
				.with(Doctor.FK_PRIMARY_OFFICE_OFFICE)
				.first();
		System.out.println("doctor: " + doctor);
		System.out.println("office: " + doctor.getPrimaryOfficeIdFK());
	}

	public void test4() {
		init(); // IGNORE
		Query<Appointment> apmts = Appointment.ALL
				.with(Appointment.FK_DOCTOR)
				.with(Appointment.FK_OFFICE)
				.with(Appointment.FK_PATIENT);
		for (Appointment apmt : apmts) {
			System.out.println(
					apmt.getPatientIdFK().getFirstName()
					+" has an appointment with "
					+ apmt.getDoctorIdFK().getFirstName()
					+" at "
					+ apmt.getOfficeIdFK().getName()
			);
		}
	}

	public void test5() {
		init(); // IGNORE
		Query<Appointment> apmts = Appointment.ALL
				.with(Appointment.FK_DOCTOR, Doctor.FK_PRIMARY_OFFICE_OFFICE)
				.with(Appointment.FK_OFFICE);
		for (Appointment apmt : apmts) {
			System.out.println(
					apmt.getDoctorIdFK().getFirstName()
					+" (primary office: "
					+ apmt.getDoctorIdFK().getPrimaryOfficeIdFK().getName()
					+") has an appointment at "
					+ apmt.getOfficeIdFK().getName()
			);
		}
	}

	public void test6() {
		init(); // IGNORE
		Query<Appointment> apmts = Appointment.ALL
				.with(Appointment.FK_DOCTOR, Doctor.FK_PRIMARY_OFFICE_OFFICE)
				.with(Appointment.FK_OFFICE)
				.where(Office.ID.neq(Office.ID));
		for (Appointment apmt : apmts) {
			System.out.println(
					apmt.getDoctorIdFK().getFirstName()
					+" has an appointment scheduled at "
					+ apmt.getOfficeIdFK().getName()
					+" but her primary office is "
					+ apmt.getDoctorIdFK().getPrimaryOfficeIdFK().getName() +"!"
			);
		}
	}

	public void test7() {
		init(); // IGNORE
		Doctor doctor = Doctor.ALL
				.with(Appointment.FK_DOCTOR)
				.first();
		System.out.println(doctor.getFirstName() +" has the following appointments scheduled:");
		for (Appointment apmt : doctor.getAppointmentSet()) {
			System.out.println("\t with: "+ apmt.getPatientIdFK().getFirstName());
		}
	}

	public void test8() {
		init(); // IGNORE
		Doctor doctor = Doctor.ALL
				.with(Appointment.FK_DOCTOR, Appointment.FK_PATIENT)
				.first();
		System.out.println(doctor.getFirstName() +" has the following appointments scheduled:");
		for (Appointment apmt : doctor.getAppointmentSet()) {
			System.out.println("\t with: "+ apmt.getPatientIdFK().getFirstName());
		}
	}

	public void test9() throws SQLException {
		init(); // IGNORE
		System.out.println("There is "+ Doctor.ALL.count() +" doctor in the database.");
		System.out.println("There are "+ Patient.ALL.count() +" patients in the database.");
	}

	public void test10() throws SQLException {
		init(); // IGNORE
		Map<String, Integer> countByFirstName = Patient.ALL.countBy(Patient.FIRST_NAME);
		System.out.println("Patients counts by first name: "+ countByFirstName);
		Map<String, Integer> countByLastName = Patient.ALL.countBy(Patient.LAST_NAME);
		System.out.println("Patients counts by last name: "+ countByLastName);
	}

	public void test11() throws SQLException {
		init(); // IGNORE
		Map<String, Patient> mapByFirstName = Patient.ALL.mapBy(Patient.FIRST_NAME);
		System.out.println("Patients counts by first name: "+ mapByFirstName);
		Map<String, Patient> mapByLastName = Patient.ALL.mapBy(Patient.LAST_NAME);
		System.out.println("Patients counts by last name: "+ mapByLastName);
	}

	public void test12() throws SQLException {
		init(); // IGNORE
		Map<String, Collection<Patient>> collectByLastName = Patient.ALL.collectBy(Patient.LAST_NAME);
		for (Entry<String, Collection<Patient>> e : collectByLastName.entrySet()) {
			String lastName = e.getKey();
			System.out.println("We have the follow people with a last name of "+ lastName +":");
			for (Patient patient : e.getValue()) {
				System.out.println("\t"+ patient);
			}
		}
	}

	public void test13() throws SQLException {
		init(); // IGNORE
		Double sum = Purchase.ALL.sum(Purchase.PRICE);
		System.out.println("Our customers purchased a total of $"+ sum);
	}
	
	//Map<Integer, Item> items = Item.ALL.where(Item.ID.in(Purchase.ALL.onlyFields(Purchase.ITEM_ID))).mapBy(Item.ID);
	public void test14() throws SQLException {
		init(); // IGNORE
		Map<Integer, Item> items = Item.ALL.mapBy(Item.ID);
		Map<Integer, Double> sums = Purchase.ALL.sumBy(Purchase.PRICE, Purchase.ITEM_ID);
		for (Entry<Integer, Double> e : sums.entrySet()) {
			Integer itemId = e.getKey();
			Double sum = e.getValue();
			Item item = items.get(itemId);
			System.out.println("Our customers purchased a total of $"+ sum +" in "+ item);
		}
	}

	public void test15() throws SQLException {
		init(); // IGNORE
		Double average = Purchase.ALL.average(Purchase.PRICE);
		System.out.println("Our customers purchased items with an average of $"+ average);
	}
	
	public void test16() throws SQLException {
		init(); // IGNORE
		Map<Integer, Item> items = Item.ALL.mapBy(Item.ID);
		Map<Integer, Double> avgs = Purchase.ALL.averageBy(Purchase.PRICE, Purchase.ITEM_ID);
		for (Entry<Integer, Double> e : avgs.entrySet()) {
			Integer itemId = e.getKey();
			Double average = e.getValue();
			Item item = items.get(itemId);
			System.out.println("The average "+ item +" purchase was $"+ average);
		}
	}

	public void test17() throws SQLException {
		init(); // IGNORE
		Map<Integer, Item> items = Item.ALL.where(Item.ID.in(
				Purchase.ALL.onlyFields(Purchase.ITEM_ID).distinct()
		)).mapBy(Item.ID);
		Map<Integer, Double> avgs = Purchase.ALL.averageBy(Purchase.PRICE, Purchase.ITEM_ID);
		for (Entry<Integer, Double> e : avgs.entrySet()) {
			Integer itemId = e.getKey();
			Double average = e.getValue();
			Item item = items.get(itemId);
			System.out.println("The average "+ item +" purchase was $"+ average);
		}
	}

	public void test18() throws SQLException {
		init(); // IGNORE
		List<String> names = new ArrayList<String>();
		names.add("Derek");
		names.add("Charles");
		for (Patient patient : Patient.ALL.where(Patient.FIRST_NAME.in(names))) {
			System.out.println(patient);
		}
	}

	public void test19() throws SQLException {
		init(); // IGNORE
		for (Patient patient : Patient.ALL.where(Patient.FIRST_NAME.in("Derek", "Charles"))) {
			System.out.println(patient);
		}
	}

	public void test20() throws SQLException {
		init(); // IGNORE
		Query<Patient> q = Patient.ALL
				.cross(Patient.as("family"))
				.where(Patient.LAST_NAME.eq(Patient.LAST_NAME.from("family"))
						.and(Patient.FIRST_NAME.neq(Patient.FIRST_NAME.from("family"))));
		for (Patient patient : q) {
			System.out.println(patient);
		}
	}

	public void test21() throws SQLException {
		init(); // IGNORE
		System.out.println("I see " +Patient.ALL.count() +" patients.");
		DataSource ds = Patient.ALL.getDataSource();
		Context context = Context.getVMContext();
		context.startTransaction(ds);
		new Patient().setFirstName("John").setLastName("Doe").insert(ds);
		System.out.println("Now I see " +Patient.ALL.count() +" patients.");
		context.rollbackTransaction(ds);
		System.out.println("Finally I see " +Patient.ALL.count() +" patients.");
	}

}
