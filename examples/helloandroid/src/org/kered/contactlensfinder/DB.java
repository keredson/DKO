package org.kered.contactlensfinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.kered.dko.Context.Undoer;
import org.kered.dko.datasource.SingleConnectionDataSource;

import android.content.Context;
import android.util.Log;

public class DB {
	
    private static final String DB_FILENAME = "myapp.db";
    private static final String ASSETS_INIT_DB_FILENAME = "init.db";
    private static boolean initted = false;
    
	/**
	 * Call once per application run.  Multiple calls are OK / ignored.
	 * Recommend in the onCreate() of you main activity.
	 * Copies an initial sqlite3 database out of you assets folder
	 * into you data folder, then sets up a default DataSource.
	 * @param context
	 */
	public static void getReady(Context context) {
		try {
			getReadyThrow(context);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

    public static void getReadyThrow(final Context context) throws IOException, SQLException {
    	if (initted) return;
		initDriver();
		boolean needToInitDatabase = !new File(getDatabaseFilename(context)).exists();
		Log.v(TAG, needToInitDatabase ? "i need to init our db..." : "i do not need to init our db");
		if (needToInitDatabase) {
			initOnDeviceDatabase(context);
			Log.v(TAG, "...db initted! ");
		}
		setDefaultDataSource(context);
		initted = true;
	}

	private static void initOnDeviceDatabase(final Context context) throws IOException {
		// copies from assets/ to /data/data/.../
		InputStream in = context.getAssets().open(ASSETS_INIT_DB_FILENAME);
		OutputStream out = new FileOutputStream(getDatabaseFilename(context));
		byte[] buffer = new byte[1024];
		for (int len; (len = in.read(buffer)) != -1; ) {
			out.write(buffer, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

    private static void initDriver() {
    	// make sure the SQLDroid driver is loaded
		try {
			final Driver d = (Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
    }

    private static void setDefaultDataSource(Context context) {
		try {
			
			// SQLDroid connections are expensive to create, so use a DKO helper
			// class that acts as a really simple connection pool.
			Connection conn = DriverManager.getConnection(getDatabaseURL(context));
			SingleConnectionDataSource ds = new SingleConnectionDataSource(conn);

			// setAutoUndo(false) is important here, otherwise this contextual setting
			// will revert itself once the Undoer object is GCed.
			Undoer undoer = org.kered.dko.Context.getVMContext().setDataSource(ds);
			undoer.setAutoUndo(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

	private static String getDatabaseURL(Context context) {
		return "jdbc:sqldroid:"+ getDatabaseFilename(context);
	}

	private static String getDatabaseFilename(Context context) {
		return context.getApplicationInfo().dataDir +"/"+ DB_FILENAME;
	}

	private static final String TAG = "DB";

}
