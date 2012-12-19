package test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.docbook.Appointment;
import org.kered.docbook.Doctor;
import org.kered.docbook.Item;
import org.kered.docbook.Office;
import org.kered.docbook.Patient;
import org.kered.docbook.Purchase;

public class DefaultDataSource {

	static DataSource ds = new JDBCDriverDataSource("jdbc:sqlite:bin/thebook.db");

	static {
		try {
			Patient derek = new Patient().setFirstName("Derek").setLastName(
					"Anderson");
			derek.insert();
			Patient charles = new Patient().setFirstName("Charles")
					.setLastName("Anderson");
			charles.insert();
			Office office = new Office().setName("UH College of Optometry");
			office.insert();
			Office office2 = new Office().setName("CF Eye Care");
			office2.insert();
			Doctor doctor = new Doctor().setFirstName("Lingyan")
					.setLastName("Anderson").setPrimaryOfficeIdFK(office);
			doctor.insert();
			Appointment apmt1 = new Appointment().setDoctorIdFK(doctor)
					.setPatientIdFK(derek).setOfficeIdFK(office);
			apmt1.insert();
			Item eyeExam = new Item().setName("Eye Exam").setPrice(100.0);
			eyeExam.insert();
			Item glasses = new Item().setName("Glasses").setPrice(250.0);
			glasses.insert();
			new Purchase().setAppointmentIdFK(apmt1).setItemIdFK(eyeExam)
					.setPrice(eyeExam.getPrice()).setQuantity(1).insert();
			new Purchase().setAppointmentIdFK(apmt1).setItemIdFK(glasses)
					.setPrice(glasses.getPrice()).setQuantity(1).insert();
			new Appointment().setDoctorIdFK(doctor).setPatientIdFK(charles)
					.setOfficeIdFK(office2).insert();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static DataSource get() {
		return ds;
	}

}
