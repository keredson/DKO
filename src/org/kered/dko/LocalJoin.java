package org.kered.dko;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Condition.Visitor;
import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Constants.JOIN_TYPE;
import org.kered.dko.Field.FK;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.datasource.SingleThreadedDataSource;

public class LocalJoin<T extends Table> extends AbstractQuery<T> {

	private static final Logger log = Logger.getLogger("org.kered.dko.LocalJoin");

	private Query<? extends Table> qL;
	private Query<? extends Table> qR;
	private Condition condition;
	private final JOIN_TYPE joinType;
	private long limit = -1;

	private transient List<Field<?>> selectFields;


	public LocalJoin(final LocalJoin<T> q) {
		super(q.ofType);
		joinType = q.joinType;
		qL = q.qL;
		qR = q.qR;
		condition = q.condition;
		limit = q.limit;
	}

	public LocalJoin(final JOIN_TYPE joinType, final Class<? extends Table> type, final Query<? extends Table> q, final Class<? extends Table> t, final Condition on) {
		super(type);
		this.joinType = joinType;
		qL = q; // .top(83)
		qR = QueryFactory.IT.getQuery(t); // .top(98)
		if (on!=null) joinAwareWhere(on);
	}

	private void joinAwareWhere(final Condition... conditions) {
		for (final Condition condition : conditions) {
			if (SoftJoinUtil.conditionIsAllReferencingQuery(condition, qL)) {
				qL = qL.where(condition);
			} else if (SoftJoinUtil.conditionIsAllReferencingQuery(condition, qR)) {
				qR = qR.where(condition);
			} else {
				if (this.condition==null) this.condition = condition;
				else this.condition = this.condition.and(condition);
			}
		}
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		final LocalJoin<T> q = new LocalJoin<T>(this);
		q.joinAwareWhere(conditions);
		return q;
	}

	@Override
	public Query<T> orderBy(Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> limit(final long n) {
		final LocalJoin<T> q = new LocalJoin<T>(this);
		q.limit = n;
		if (joinType==JOIN_TYPE.LEFT || joinType==JOIN_TYPE.OUTER) q.qL = q.qL.top(n);
		if (joinType==JOIN_TYPE.RIGHT || joinType==JOIN_TYPE.OUTER) q.qR = q.qR.top(n);
		return q;
	}

	@Override
	public Query<T> distinct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> max() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> with(FK... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> deferFields(Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> deferFields(Collection<Field<?>> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> onlyFields(Collection<Field<?>> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> onlyFields(Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> orderBy(DIRECTION direction, Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(Field<?> key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(Map<Field<?>, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(DataSource ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(DB_TYPE type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> in(Collection<T> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field<?>> getSelectFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return new ClosableIterator<T>() {
			
			private File tmpFile = null;
			private List<Field<?>> qLfields;
			private List<Field<?>> qRfields;
			private ClosableIterator<Table> iL;
			private ClosableIterator<Table> iR;
			private DualIterator di;

			{
			    final boolean qLpk = SoftJoinUtil.doesConditionCoverPK(qL.getType(), condition);
			    final boolean qRpk = SoftJoinUtil.doesConditionCoverPK(qR.getType(), condition);
			    System.err.println("qLpk:"+ qLpk +" qRpk:"+qRpk);

			    boolean loadRFirst = false;
			    if (qLpk || qRpk) {
			    	loadRFirst = qLpk && !qRpk;
			    } else {
				    final long qLCount = UsageStats.estimateRowCount(qL);
				    final long qRCount = UsageStats.estimateRowCount(qR);
					System.err.println("qLCount "+ qL.getType().getName() +" h:"+ qL.hashCode() +" c:"+ qLCount);
					System.err.println("qRCount "+ qR.getType().getName() +" h:"+ qR.hashCode() +" c:"+ qRCount);
			    	loadRFirst = qLCount > qRCount;
			    }

			    DataSource ds = createDS();
			    
			    if (loadRFirst) {
			    	load(qR, "tr", ds);
			    	Query<? extends Table> qLf = createFilteredQ(qL, condition, "tr", ds);
			    	load(qLf, "tl", ds);
			    } else {
			    	load(qL, "tl", ds);
			    	Query<? extends Table> qRf = createFilteredQ(qR, condition, "tl", ds);
			    	load(qRf, "tr", ds);
			    }
			    
			    initQuery(ds);

			}

			@Override
			public boolean hasNext() {
				return iL.hasNext() || iR.hasNext();
			}

			private void initQuery(DataSource ds) {
				qLfields = qL.getSelectFields();
				qRfields = qR.getSelectFields();
				StringBuffer sb = new StringBuffer();
				sb.append("select ").append(Util.joinFields(Constants.DB_TYPE.SQLITE3, ", ",  qLfields)).append(", ");
				sb.append(Util.joinFields(Constants.DB_TYPE.SQLITE3, ", ",  qRfields)).append(" ");
				sb.append("from tl ").append(joinType).append(" tr on ").append(condition.getSQL(null));
				String sql = sb.toString();
		    	di = new DualIterator(ds, sql, qLfields, qRfields);
		    	iL = new SelectFromOAI((DBQuery) qL, di.getLeftIterator());
		    	iR = new SelectFromOAI((DBQuery) qR, di.getRightIterator());
			}

			@Override
			public T next() {
				Table left = iL.hasNext() ? iL.next() : null;
				Table right = iR.hasNext() ? iR.next() : null;
				return (T) new Join<Table,Table>(left, right);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public synchronized void close() {
				di.close();
			}

			private DataSource createDS() {
				// make sure sqlite driver is loaded...
				org.kered.dko.persistence.Util.getDS();

				try {
					tmpFile  = File.createTempFile("dko_local_join_", ".db");
					//tmpFile.deleteOnExit();
					System.err.println(tmpFile.getPath());
					String url = "jdbc:sqlite:" + tmpFile.getPath();
					DataSource ds = new SingleThreadedDataSource(new JDBCDriverDataSource(Constants.DB_TYPE.SQLITE3, url), 10000, false);
					return ds;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				
			}

		};
	}

	private static void load(Query<? extends Table> q, String table, DataSource ds) {
		List<Field<?>> fields = q.getSelectFields();
		buildSchema(fields, table, ds);
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ").append(table).append(" (");
		for (int i=0; i<fields.size(); ++i) {
			Field<?> field = fields.get(i);
			sb.append(field.NAME);
			if (i<fields.size()-1) sb.append(", ");
		}
		sb.append(") values (");
		for (int i=0; i<fields.size(); ++i) {
			sb.append("?");
			if (i<fields.size()-1) sb.append(", ");
		}
		sb.append(")");
		String sql = sb.toString();
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);
			System.err.println(sql);
			int count = 0;
			for (Table row : q) {
				for (int i=0; i<fields.size(); ++i) {
					Field<?> field = fields.get(i);
					Object o = row.get(field);
					o = row.__NOSCO_PRIVATE_mapType(o);
					if (o instanceof Character) ps.setString(i+1, o.toString());
					else ps.setObject(i+1, o);
				}
				++count;
				ps.execute();
				//ps.addBatch();
				//if (count%256==0) ps.executeBatch();
			}
			//if (count%256!=0) ps.executeBatch();
			conn.commit();
			System.err.println("loaded "+ count +" rows into "+ table);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (conn!=null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void buildSchema(List<Field<?>> fields, String table, DataSource ds) {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(table).append(" (");
		for (int i=0; i<fields.size(); ++i) {
			Field<?> field = fields.get(i);
			sb.append(field.NAME).append(" ").append(field.SQL_TYPE);
			if (i<fields.size()-1) sb.append(", ");
		}
		sb.append(")");
		String sql = sb.toString();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			System.err.println(sql);
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (conn!=null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static List getDistinctFrom(Field<?> key, String otherTable, DataSource ds) {
		List ret = new ArrayList();
		String sql = "select distinct "+ key.NAME +" from "+ otherTable;
		Connection conn = null;
		Statement ps = null;
		try {
			conn = ds.getConnection();
			ps = conn.createStatement();
			System.err.println(sql);
			ResultSet rs = ps.executeQuery(sql);
			while (rs.next()) {
				ret.add(rs.getObject(1));
			}
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (conn!=null && !conn.isClosed()) conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return ret;
	}

	private static Query<? extends Table> createFilteredQ(Query<? extends Table> q, Condition condition, String otherTable, DataSource ds) {
		final Set<Field<?>> queryFields = new HashSet<Field<?>>(q.getSelectFields());
		final Map<Field<?>,Field<?>> fields = new LinkedHashMap<Field<?>,Field<?>>();
		if (condition!=null) condition.visit(new Visitor() {
			@Override
			public void visited(Condition c) {
				if (c instanceof Condition.Binary) {
					Condition.Binary o = (Condition.Binary) c;
					if (queryFields.contains(o.field)) fields.put(o.field, o.field2);
					if (queryFields.contains(o.field2)) fields.put(o.field2, o.field);
				}
				if (c instanceof Condition.Binary2) {
					Condition.Binary2 o = (Condition.Binary2) c;
					if (o.o1 instanceof Field && queryFields.contains(o.o1)) fields.put((Field<?>)o.o1, (Field<?>)o.o2);
					if (o.o2 instanceof Field && queryFields.contains(o.o2)) fields.put((Field<?>)o.o2, (Field<?>)o.o1);
				}
//				if (c instanceof Condition.Ternary) {
//					Condition.Ternary o = (Condition.Ternary) c;
//					if (o.v1 instanceof Field && fields.contains(o.v1)) fields.add((Field<?>)o.v1);
//					if (o.v2 instanceof Field && fields.contains(o.v2)) fields.add((Field<?>)o.v2);
//					if (o.v3 instanceof Field && fields.contains(o.v3)) fields.add((Field<?>)o.v3);
//				}
//				if (c instanceof Condition.Unary) {
//					Condition.Unary o = (Condition.Unary) c;
//					if (fields.contains(o.field)) fields.add(o.field);
//				}
			}
		});
		for (Entry<Field<?>, Field<?>> e : fields.entrySet()) {
			List values = getDistinctFrom(e.getValue(), otherTable, ds);
			q = q.where(e.getKey().in(values));
		}
		return q;
	}

}
