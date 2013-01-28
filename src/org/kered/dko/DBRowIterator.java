package org.kered.dko;

import static org.kered.dko.Constants.DIRECTION.DESCENDING;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Tuple.Tuple2;


class DBRowIterator<T extends Table> implements PeekableClosableIterator<Object[]> {

	private static final int BATCH_SIZE = 2048;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
		if (rs != null && !rs.isClosed()) rs.close();
		if (ps != null && !ps.isClosed()) ps.close();
	}

	private static final Logger log = Logger.getLogger("org.kered.dko.DBRowIterator");

	private String sql;
	final DBQuery<T> query;
	private PreparedStatement ps;
	private ResultSet rs;
	Field<?>[] selectedFields;
	private Field<?>[] selectedBoundFields;
	private Connection conn;
	private final Queue<Object[]> nextRows = new LinkedList<Object[]>();
	private boolean done = false;
	Object[] lastFieldValues;
	private boolean shouldCloseConnection = true;
	private SqlContext context = null;
	DataSource ds = null;
	final UsageMonitor<T> usageMonitor;
	private boolean initted = false;
	long count = 0;

	private Constructor<T> joinConstructor = null;

	private boolean finishedNatually = false;

	DBRowIterator(final DBQuery<T> dbQuery) {
		this(dbQuery, true);
	}
	DBRowIterator(final DBQuery<T> dbQuery, final boolean useWarnings) {
		if (useWarnings && Context.usageWarningsEnabled()) {
			// make sure usage monitor has loaded stats for all the tables we care about
			for (final TableInfo tableInfo : dbQuery.getAllTableInfos()) {
				UsageMonitor.loadStatsFor(tableInfo.tableClass);
			}
			usageMonitor = new UsageMonitor<T>(dbQuery);
			this.query = usageMonitor.getSelectOptimizedQuery();
		} else {
			usageMonitor = null;
			this.query = dbQuery;
		}
		final List<Field<?>> selectFieldsList = query.getSelectFields(false);
		selectedFields = toArray(selectFieldsList);
		if (this.usageMonitor!=null) {
			this.usageMonitor.setSelectedFields(selectedFields);
		}
		ds  = query.getDataSource();
	}

	void init() {
		// old iterator method before merging
		try {
			final Tuple2<Connection,Boolean> connInfo = DBQuery.getConnR(ds);
			conn = connInfo.a;
			shouldCloseConnection  = connInfo.b;
			context  = new SqlContext(query);
			final Tuple2<String, List<Object>> ret = getSQL(context);
			Util.log(sql, ret.b);
			query._preExecute(context, conn);
			ps = conn.prepareStatement(ret.a);
			query.setBindings(ps, ret.b);
			ps.execute();
			rs = ps.getResultSet();
			done = false;
		} catch (final SQLException e) {
			log.severe(sql + "\n => " + e.getMessage());
			e.printStackTrace();
			try {
				if (conn!=null && !conn.isClosed()) conn.close();
			} catch (final SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		} catch (final SecurityException e) {
			e.printStackTrace();
			throw e;
		}
		initted  = true;
	}

	protected String getSQL() {
		return getSQL(new SqlContext(query)).a;
	}

	DBQuery<T> getUnderlyingQuery() {
		return query;
	}

	protected Tuple2<String,List<Object>> getSQL(final SqlContext context) {
		selectedBoundFields = toArray(query.getSelectFields(true));
		final StringBuffer sb = new StringBuffer();
		sb.append("select ");
		if (query.distinct) sb.append("distinct ");
		if (context.dbType==DB_TYPE.SQLSERVER && query.top>0 && query.joinsToMany.size()==0) {
			sb.append(" top ").append(query.top).append(" ");
		}
		if (query.globallyAppliedSelectFunction == null) {
			sb.append(Util.joinFields(context, ", ", selectedBoundFields));
		} else {
			final String[] x = new String[selectedBoundFields.length];
			for (int i=0; i < x.length; ++i) {
				x[i] = query.globallyAppliedSelectFunction + "("+ selectedBoundFields[i].getSQL(context) +")";
			}
			sb.append(Util.join(", ", x));
		}
		sb.append(query.getFromClause(context));
		final Tuple2<String, List<Object>> ret = query.getWhereClauseAndBindings(context);
		sb.append(ret.a);

		final List<DIRECTION> directions = query.getOrderByDirections();
		final List<Field<?>> fields = query.getOrderByFields();
		if (!context.inInnerQuery() && directions!=null & fields!=null) {
			sb.append(" order by ");
			final int x = Math.min(directions.size(), fields.size());
			final String[] tmp = new String[x];
			for (int i=0; i<x; ++i) {
				final DIRECTION direction = directions.get(i);
				tmp[i] = Util.derefField(fields.get(i), context) + (direction==DESCENDING ? " DESC" : "");
			}
			sb.append(Util.join(", ", tmp));
		}

		if (context.dbType!=DB_TYPE.SQLSERVER && query.top>0 && query.joinsToMany.size()==0) {
			sb.append(" limit ").append(query.top);
		}

		if (selectedFields.length > context.maxFields) {
			Util.log(sb.toString(), null);
			throw new RuntimeException("too many fields selected: "+ selectedFields.length
					+" > "+ context.maxFields +" (note: inner queries inside a 'where x in " +
							"()' can have at most one returned column.  use onlyFields()" +
							"in the inner query)");
		}

		sql = sb.toString();
		return new Tuple2<String,List<Object>>(sb.toString(), ret.b);
	}

	static Field<?>[] toArray(final List<Field<?>> fields) {
		final Field<?>[] ret = new Field<?>[fields.size()];
		int i = 0;
		for (final Field<?> field : fields) {
			ret[i++] = field;
		}
		return ret;
	}

	public Object[] next() {
		peek();
		return nextRows.poll();
	}

	public Object[] peek() {
		if (!done && nextRows.isEmpty()) {
			try {
				readNextRows(BATCH_SIZE);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		if (nextRows.isEmpty()) {
			this.finishedNatually = true;
			return null;
		}
		else return nextRows.peek();
	}

	private int readNextRows(final int max) throws SQLException {
		if (rs == null) return 0;
		int c = 0;
		while (c < max) {
			if (!rs.next()) {
				close();
				//preFetchOtherJoins();
				return c;
			}
			++c;
			final Object[] nextRow = new Object[selectedFields.length];
			for (int i=0; i<selectedFields.length; ++i) {
				nextRow[i] = Util.getTypedValueFromRS(rs, i+1, selectedFields[i]);
			}
			nextRows.add(nextRow);
			if (usageMonitor!=null) ++usageMonitor.rowCount;
		}
		//preFetchOtherJoins();
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasNext() {
		if (!this.initted) init();
		if (query.top>0 && count >= query.top) {
			close();
			return false;
		}
		final boolean hasNext = peek()!=null;
		if (!hasNext) close();
		return hasNext;
	}

	@Override
	public synchronized void close() {
		if (done) return;
		try {
			query._postExecute(context, conn);
		} catch (final SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if (!finishedNatually && rs!=null && !rs.isClosed()) {
				ps.cancel();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		try {
			if (rs!=null && !rs.isClosed()) rs.close();
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		try {
			if (ps!=null && !ps.isClosed()) ps.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (AbstractMethodError e) {
			if ("org.sqlite.RS".equals(rs.getClass().getName())) {
				// ignore - bad jdbc driver
			} else {
				throw e;
			}
		}
		if (shouldCloseConnection) {
			try {
				if (conn!=null && !conn.isClosed()) conn.close();
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (usageMonitor!=null && finishedNatually) {
			usageMonitor.saveSizeOfQuery();
		}
		done = true;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
