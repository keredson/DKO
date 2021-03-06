package org.kered.dko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Field.FK;
import org.kered.dko.Field.PK;

class TemporaryTableFactory {

	static class DummyTableWithName<T extends Table> extends Table {

		final String name = "NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		Class<T> cls = null;
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

		public static String _SCHEMA_NAME = null;

		@Override
		public List<Field<?>> fields() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected FK[] FKS() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S> S get(final Expression.Select<S> field) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S> Table set(final Expression.Select<S> field, final S value) {
			return null;
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
				String tableName = (context.dbType==Constants.DB_TYPE.SQLSERVER ? "#" : "") + name;
				if (context.dbType==Constants.DB_TYPE.DERBY) tableName = "SESSION."+tableName;
				String fluff = "";
				if (context.dbType==Constants.DB_TYPE.SQLSERVER) fluff = "TEMPORARY ";
				if (context.dbType==Constants.DB_TYPE.ORACLE || context.dbType==Constants.DB_TYPE.DERBY) fluff = "GLOBAL TEMPORARY ";
				String create = context.dbType==Constants.DB_TYPE.DERBY ? "DECLARE " : "CREATE ";
				sqlSb.append(create+ fluff +"TABLE "+ tableName + "(");
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
					if (context.dbType==Constants.DB_TYPE.DERBY && "varchar".equals(field.SQL_TYPE.toLowerCase())) {
						sqlSb.append("(32672)");
					}
					if (i < fields.size()-1) sqlSb.append(", ");
				}
				sqlSb.append(")");
				if (context.dbType==Constants.DB_TYPE.ORACLE || context.dbType==Constants.DB_TYPE.DERBY) {
					sqlSb.append(" ON COMMIT PRESERVE ROWS");
				}
				if (context.dbType==Constants.DB_TYPE.DERBY) {
					sqlSb.append(" NOT LOGGED");
				}
				final String sql = sqlSb.toString();
				Util.log(sql, null);
				stmt.execute(sql);
				String sqlInsert = "insert into "+ tableName +" values ("+ Util.join(",", placeholders) +")";
				Util.log(sqlInsert, null);
				ps = conn.prepareStatement(sqlInsert);
				int i = 0;
				int added = 0;
				for (final T t : set) {
					++i;
					for (int j=0; j<fields.size(); ++j) {
						final Field<?> field = fields.get(j);
						final Object o = t.get(field);
						Util.setBindingWithTypeFixes(ps, j+1, o);
					}
					ps.addBatch();
					if (i%64 == 0) {
						for (int x : ps.executeBatch()) {
							if (context.dbType==DB_TYPE.ORACLE && x==-2) {
								// from oracle's docs:  a value of -2 indicates that a element was processed 
								// successfully, but that the number of effected rows is unknown.
								x = 1;
							}
							added += x;
						}
					}
				}
				if (i%64 != 0) {
					for (int x : ps.executeBatch()) {
						if (context.dbType==DB_TYPE.ORACLE && x==-2) {
							// from oracle's docs:  a value of -2 indicates that a element was processed 
							// successfully, but that the number of effected rows is unknown.
							x = 1;
						}
						added += x;
					}
				}
//				ResultSet rs = stmt.executeQuery("select count(1) from "+ tableName);
//				while (rs.next()) {
//					System.err.println("tmp table "+ tableName +" has "+ rs.getLong(1) +" rows");
//				}
//				rs.close();
				//System.out.println("added "+ added);
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
			String tableName = (context.dbType==Constants.DB_TYPE.SQLSERVER ? "#" : "") + name;
			if (context.dbType==Constants.DB_TYPE.DERBY) tableName = "SESSION."+tableName;
			try {
				stmt = conn.createStatement();
				if (context.dbType==DB_TYPE.ORACLE) {
					// oracle requires a TRUNCATE before a drop for tmp tables, otherwise you get:
					// ORA-14452: attempt to create, alter or drop an index on temporary table already in use
					final String sql = "TRUNCATE TABLE "+ tableName;
					Util.log(sql, null);
					stmt.execute(sql);
				}
				final String sql = "DROP TABLE "+ tableName;
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
	static <T extends Table> DummyTableWithName createTemporaryTable(final Class<T> cls, final List<Field<?>> fields, final Collection<T> set) {
		return new DummyTableWithName<T>(cls, fields, set);
	}

}
