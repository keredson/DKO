package org.nosco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.nosco.Field.FK;

class TmpTableBuilder {

	private String tmpTableName = "NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
	private Field<?>[] fields;
	private List<Object[]> rows = new ArrayList<Object[]>();

	private Table table = new Table() {

		@Override
		protected void __NOSCO_PRIVATE_preExecute(SqlContext context, Connection conn) throws SQLException {
			StringBuilder sbCreate = new StringBuilder();
			if (context.dbType == Constants.DB_TYPE.SQLSERVER) {
				sbCreate.append("CREATE TABLE #"+ tmpTableName);
			} else {
				sbCreate.append("CREATE TABLE "+ tmpTableName);
			}
			sbCreate.append(" (");
			StringBuilder sbPS = new StringBuilder();
			sbPS.append("insert into "+ tmpTableName +" values (");
			for (int i=0; i<fields.length; ++i) {
				Field<?> field = fields[i];
				sbCreate.append(field.NAME +" "+ field.SQL_TYPE +",");
				sbPS.append("?,");
			}
			sbCreate.delete(sbCreate.length()-1, sbCreate.length());
			sbCreate.append(")");
			sbPS.delete(sbPS.length()-1, sbPS.length());
			sbPS.append(")");
			Statement stmt = null;
			PreparedStatement ps = null;
			try {
				stmt = conn.createStatement();
				String sqlCreate = sbCreate.toString();
				String sqlPS = sbPS.toString();
				Util.log(sqlCreate, null);
				stmt.execute(sqlCreate);
				ps = conn.prepareStatement(sqlPS);
				int i = 0;
				int added = 0;
				for (Object[] row : rows) {
					++i;
					for (int j=0; j<row.length; ++j) {
						Object t = row[j];
						if (t instanceof Character) ps.setString(1, t.toString());
						else ps.setObject(j+1, t);
					}
					ps.addBatch();
					if (i%64 == 0) for (int x : ps.executeBatch()) added += x;
				}
				if (i%64 != 0) {
					for (int x : ps.executeBatch()) {
						added += x;
					}
				}
				System.err.println("TmpTableBuilder added "+ added);
			} catch (SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (ps!=null && !ps.isClosed()) ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}



		}

		@Override
		protected void __NOSCO_PRIVATE_postExecute(SqlContext context, Connection conn) throws SQLException {
			if (context.dbType == Constants.DB_TYPE.HSQL) return;
			if (conn!=null && !conn.isClosed()) {
				Statement stmt = null;
				try {
					stmt = conn.createStatement();
					String sql = "DROP TABLE "+ tmpTableName;
					Util.log(sql, null);
					stmt.execute(sql);
				} catch (SQLException e) {
					throw e;
				} finally {
					try {
						if (stmt!=null && !stmt.isClosed()) stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		@Override
		public String SCHEMA_NAME() {
			return null;
		}

		@Override
		public String TABLE_NAME() {
			return tmpTableName;
		}

		@Override
		public Field[] FIELDS() {
			return fields;
		}

		@Override
		protected FK[] FKS() {
			return new FK[0];
		}

		@Override
		public <S> S get(Field<S> field) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <S> void set(Field<S> field, S value) {
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
		public boolean insert(DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean update(DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean delete(DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean save(DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean exists(DataSource ds) throws SQLException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		protected Object __NOSCO_PRIVATE_mapType(Object o) {
			return null;
		}

	};

	TmpTableBuilder(Field<?>... fields) {
		this.fields = fields;
		StringBuilder sb = new StringBuilder();
	}

	Table buildTable() {
		return table ;
	}

	void addRow(Object... row) {
		rows.add(row);
	}

	void clear() {
		rows.clear();
	}

}
