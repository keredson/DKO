package org.nosco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.util.Misc;

public class Bulk {

	private DataSource ds;

	public Bulk(DataSource ds) {
		this.ds = ds;
	}

	public <T extends Table> int insertAll(Iterable<T> iterable) throws SQLException {
		int count = 0;
		boolean first = true;
		PreparedStatement ps = null;
		Field[] fields = null;
		QueryImpl<T> q = null;

		for (T x : iterable) {
			if (first) {
				first = false;
				q = (QueryImpl<T>) new QueryImpl<T>(x.getClass()).use(ds);
				Table table = x;
				String sep = q.getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
				StringBuffer sb = new StringBuffer();
				sb.append("insert into ");
				sb.append(table.SCHEMA_NAME() +sep+ table.TABLE_NAME());
				sb.append(" (");
				fields = table.FIELDS();
				sb.append(Misc.join(",", fields));
				String[] bindStrings = new String[fields.length];
				for (int i=0; i < fields.length; ++i) bindStrings[i] = "?";
				sb.append(") values (");
				sb.append(Misc.join(", ", bindStrings));
				sb.append(")");
				String sql = sb.toString();
				Misc.log(sql, null);
				Connection conn = q.getConnRW();
				ps = conn.prepareStatement(sql);
			}
			int i=1;
			for (Field field : fields) {
				Object o = x.get(field);
				// hack for sql server which otherwise gives:
				// com.microsoft.sqlserver.jdbc.SQLServerException:
				// The conversion from UNKNOWN to UNKNOWN is unsupported.
				if (o instanceof Character) ps.setString(i++, o.toString());
				else ps.setObject(i++, o);
			}
			ps.execute();
			count += ps.getUpdateCount();

		}

		ps.close();
		return count;
	}

}
