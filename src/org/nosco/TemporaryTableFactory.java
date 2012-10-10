package org.nosco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.nosco.Field.FK;
import org.nosco.Field.PK;

class TemporaryTableFactory {

	private static class DummyTableWithName<T extends Table> extends Table {

		final String name = "NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		private Class<T> cls = null;
		private List<Field<?>> fields = null;
		private Collection<T> set = null;

		@SuppressWarnings("rawtypes")
		public static Field.PK<?> PK = new Field.PK();

		@SuppressWarnings("unused")
		protected DummyTableWithName(final Field[] _fields, final Object[] _objects, final int _start, final int _end) {
			// TODO Auto-generated method stub
		}

		public DummyTableWithName(final Class<T> cls, final List<Field<?>> fields, final Collection<T> set) {
			this.cls = cls;
			this.fields = fields;
			this.set = set;
		}

		@Override
		protected String SCHEMA_NAME() {
			return null;
		}

		String TABLE_NAME(final SqlContext sqlContext) {
			return sqlContext.dbType==Constants.DB_TYPE.SQLSERVER ? "#"+name : name;
		}

		@Override
		protected String TABLE_NAME() {
			return name;
		}

		@Override
		public List<Field<?>> FIELDS() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected FK[] FKS() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S> S get(final Field<S> field) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S> void set(final Field<S> field, final S value) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean insert() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean update() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delete() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean save() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean exists() throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean insert(final DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean update(final DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delete(final DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean save(final DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean exists(final DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void __NOSCO_PRIVATE_preExecute(final SqlContext context, final Connection conn) throws SQLException {
			Statement stmt = null;
			PreparedStatement ps = null;
			try {
				stmt = conn.createStatement();
				final StringBuffer sqlSb = new StringBuffer();
				sqlSb.append("CREATE "+ (context.dbType==Constants.DB_TYPE.SQLSERVER ? "" : "TEMPORARY ") +"TABLE "+ TABLE_NAME(context) + "(");
				final List<String> placeholders = new ArrayList<String>();
				for (int i=0; i<fields.size(); ++i) {
					final Field<?> field = fields.get(i);
					placeholders.add("?");
					sqlSb.append(field.NAME);
					sqlSb.append(" ");
					sqlSb.append(field.SQL_TYPE);
					if (context.dbType==Constants.DB_TYPE.MYSQL && "varchar".equals(field.SQL_TYPE.toLowerCase())) {
						sqlSb.append("(4096)");
					}
					if (i < fields.size()-1) sqlSb.append(", ");
				}
				sqlSb.append(")");
				final String sql = sqlSb.toString();
				Util.log(sql, null);
				stmt.execute(sql);
				ps = conn.prepareStatement("insert into "+ TABLE_NAME(context) +" values ("+ Util.join(",", placeholders) +")");
				int i = 0;
				int added = 0;
				for (final T t : set) {
					++i;
					for (int j=0; j<fields.size(); ++j) {
						final Field<?> field = fields.get(j);
						final Object o = t.get(field);
						if (o instanceof Character) ps.setString(j+1, t.toString());
						else ps.setObject(j+1, o);
					}
					ps.addBatch();
					if (i%64 == 0) for (final int x : ps.executeBatch()) added += x;
				}
				if (i%64 != 0) {
					for (final int x : ps.executeBatch()) {
						added += x;
					}
				}
			} catch (final SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
				try {
					if (ps!=null && !ps.isClosed()) ps.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}
		@Override
		protected void __NOSCO_PRIVATE_postExecute(final SqlContext context, final Connection conn) throws SQLException {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				final String sql = "DROP TABLE "+ TABLE_NAME(context);
				Util.log(sql, null);
				stmt.execute(sql);
			} catch (final SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	static <T extends Table> T createTemporaryTable(final Class<T> cls, final List<Field<?>> fields, final Collection<T> set) {
		return (T) new DummyTableWithName<T>(cls, fields, set);
	}

}
