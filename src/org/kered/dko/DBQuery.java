package org.kered.dko;

import static org.kered.dko.Constants.DIRECTION.ASCENDING;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Field.FK;
import org.kered.dko.Field.PK;
import org.kered.dko.Table.__Alias;
import org.kered.dko.Table.__PrimaryKey;
import org.kered.dko.Tuple.Tuple2;
import org.kered.dko.datasource.MirroredDataSource;
import org.kered.dko.datasource.SingleConnectionDataSource;


class DBQuery<T extends Table> extends AbstractQuery<T> {

	// make sure the UsageMonitor is class-loaded
	static { UsageMonitor.doNothing(); }

	// genned once and cached
	transient private List<Field<?>> fields;
	transient private List<Field<?>> boundFields;
	transient Map<String,Set<String>> tableNameMap = null;
	transient DB_TYPE detectedDbType = null;

	// these should be cloned
	List<Condition> conditions = null;
	Set<String> usedTableNames = new HashSet<String>();
	List<TableInfo> tableInfos = new ArrayList<TableInfo>();
	List<JoinInfo> joins = new ArrayList<JoinInfo>();
	List<JoinInfo> joinsToOne = new ArrayList<JoinInfo>();
	List<JoinInfo> joinsToMany = new ArrayList<JoinInfo>();
	private Set<Field<?>> deferSet = null;
	private Set<Field<?>> onlySet = null;
	private List<DIRECTION> orderByDirections = null;
	private List<Field<?>> orderByFields = null;
	long top = 0;
	private Map<Field<?>,Object> data = null;
	boolean distinct = false;
	DataSource ds = null;
	DataSource defaultDS = null;
	String globallyAppliedSelectFunction = null;
	DB_TYPE dbType = null;
	private boolean includeCrossInSelect = false;
	private boolean onlySelectFromFirstTableAndJoins = true;
	List<Union<T>> unions = null;
	private Integer timeout = null;

	private TableInfo addTable(final Class<? extends Table> table) {
		final String tableName = genTableName(table, usedTableNames);
		usedTableNames.add(tableName);
		final TableInfo info = new TableInfo(table, tableName, null);
		info.nameAutogenned = true;
		tableInfos.add(info);
		return info;
	}

	DBQuery(final DBQuery<T> q) {
		super(q);
		copy(q);
	}

	private void copy(final DBQuery<T> q) {
		if (q.conditions!=null) {
			conditions = new ArrayList<Condition>();
			conditions.addAll(q.conditions);
		}
		usedTableNames.addAll(q.usedTableNames);
		try {
			for (final JoinInfo x : q.joins) joins.add((JoinInfo) x.clone());
			for (final JoinInfo x : q.joinsToOne) joinsToOne.add((JoinInfo) x.clone());
			for (final JoinInfo x : q.joinsToMany) joinsToMany.add((JoinInfo) x.clone());
			for (final TableInfo x : q.tableInfos) {
				final TableInfo clone = (TableInfo) x.clone();
				for (final JoinInfo y : joinsToOne) {
					if (y.reffedTableInfo == x) y.reffedTableInfo = clone;
					if (y.reffingTableInfo == x) y.reffingTableInfo = clone;
				}
				for (final JoinInfo y : joinsToMany) {
					if (y.reffedTableInfo == x) y.reffedTableInfo = clone;
					if (y.reffingTableInfo == x) y.reffingTableInfo = clone;
				}
				tableInfos.add(clone);
			}
		} catch (final CloneNotSupportedException e) { /* ignore */ }
		if (q.deferSet!=null) {
			deferSet = new HashSet<Field<?>>();
			deferSet.addAll(q.deferSet);
		}
		if (q.onlySet!=null) {
			onlySet = new LinkedHashSet<Field<?>>();
			onlySet.addAll(q.onlySet);
		}
		if (q.orderByDirections!=null) {
			orderByDirections = new ArrayList<DIRECTION>();
			orderByDirections.addAll(q.orderByDirections);
		}
		if (q.orderByFields!=null) {
			orderByFields = new ArrayList<Field<?>>();
			orderByFields.addAll(q.orderByFields);
		}
		top = q.top;
		if (q.data!=null) {
			data = new HashMap<Field<?>,Object>();
			data.putAll(q.data);
		}
		distinct = q.distinct;
		ds = q.ds;
		globallyAppliedSelectFunction = q.globallyAppliedSelectFunction;
		dbType = q.dbType;
		defaultDS = q.defaultDS;
		onlySelectFromFirstTableAndJoins = q.onlySelectFromFirstTableAndJoins;
		if (q.unions!=null) {
			unions = new ArrayList<Union<T>>(q.unions);
		}
		timeout = q.timeout;
	}

	DBQuery(final Class<T> tableClass) {
		super(tableClass);
		addTable(tableClass);
	}

	<S extends Table> DBQuery(final DBQuery<T> q, final String joinType, final Class<S> other, String alias, final Condition condition) {
		this(q);
		final JoinInfo<T,S> ji = new JoinInfo<T,S>();
		ji.lType = ofType;
		ji.rType = other;
		ji.type = "inner join";
		ji.condition = condition;
		try {
			final boolean autogenName = alias == null;
			if (autogenName) alias = genTableName(other, usedTableNames);
			usedTableNames.add(alias);
			ji.reffedTableInfo = new TableInfo(other, alias, null);
			ji.reffedTableInfo.nameAutogenned = autogenName;
			joins.add(ji);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	DataSource getDefaultDataSource() {
		if (defaultDS != null) return defaultDS;
		defaultDS = Util.getDefaultDataSource(ofType);
		if (defaultDS!=null) return defaultDS;
		for (final TableInfo ti : this.tableInfos) {
			defaultDS = Util.getDefaultDataSource(ti.tableClass);
			if (defaultDS!=null) return defaultDS;
		}
		return null;
	}


	DBQuery(final Class<T> tableClass, final DataSource ds) {
		this(tableClass);
		this.ds = ds;
	}

	public DBQuery(final __Alias<T> alias) {
		super(alias.table);
		try {
			usedTableNames.add(alias.alias);
			final TableInfo info = new TableInfo(alias.table, alias.alias, null);
			info.nameAutogenned = false;
			tableInfos.add(info);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
	}

	<T1 extends Table, T2 extends Table> DBQuery(final Class<? extends Join> type, final Query<T1> q, final Class<T2> other, final String joinType, final Condition on) {
		this(type, q, other, null, joinType, on);
	}

	<T1 extends Table, T2 extends Table> DBQuery(final Class<? extends Join> type, final Query<T1> q, final __Alias<T2> other, final String joinType, final Condition on) {
		this(type, q, other.table, other.alias, joinType, on);
	}

	<T1 extends Table, T2 extends Table> DBQuery(final Class<? extends Join> type, final Query<T1> q, final Class<T2> other, String alias, final String joinType, final Condition on) {
		super(type);
		copy((DBQuery<T>) q);
		final JoinInfo<T1,T2> ji = new JoinInfo<T1,T2>();
		ji.lType = q.getType();
		ji.rType = other;
		ji.type = joinType;
		ji.condition = on;
		try {
			final boolean autogenName = alias == null;
			if (autogenName) alias = genTableName(other, usedTableNames);
			usedTableNames.add(alias);
			ji.reffedTableInfo = new TableInfo(other, alias, null);
			ji.reffedTableInfo.nameAutogenned = autogenName;
			joins.add(ji);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		this.onlySelectFromFirstTableAndJoins = false;
	}

	<T1 extends Table, T2 extends Table> DBQuery(final Class<? extends Join> type, final DBQuery<T1> q, final DBQuery<T2> other, String alias, final String joinType, final Condition on) {
		super(type);
		copy((DBQuery<T>) q);
		final JoinInfo<T1,T2> ji = new JoinInfo<T1,T2>();
		ji.lType = q.getType();
		ji.rType = null;
		ji.type = joinType;
		ji.condition = on;
		try {
			final boolean autogenName = alias == null;
			if (autogenName) alias = genTableName(other.getType(), usedTableNames);
			usedTableNames.add(alias);
			ji.reffedTableInfo = new TableInfo(other, alias, null);
			ji.reffedTableInfo.nameAutogenned = autogenName;
			joins.add(ji);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		this.onlySelectFromFirstTableAndJoins = false;
	}

	@Override
	public Iterator<T> iterator() {
		//sanityCheckToManyJoins();
		return new SelectFromOAI<T>(this);
	}

	private void sanityCheckToManyJoins() {
		final List<Field<?>> selectFields = getSelectFields();
		for (final JoinInfo join : this.joinsToMany) {
			final List<Field<?>> missing = new ArrayList<Field<?>>();
			for (final Field<?> f1 : Util.getPK(join.reffedTableInfo.tableClass).GET_FIELDS()) {
				boolean found = false;
				for (final Field<?> f2 : selectFields) {
					if (f1.sameField(f2)) {
						found = true;
						break;
					}
				}
				if (!found) {
					missing.add(f1);
				}
			}
			if (!missing.isEmpty()) {
				final String msg = "The following fields have been excluded from your selected fields list: "
						+ missing +" but are required by the join you're using: "+ join.fk;
				throw new RuntimeException(msg);
			}
		}
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.conditions = new ArrayList<Condition>();
		if (this.conditions!=null) q.conditions.addAll(this.conditions);
		for (final Condition c : conditions) {
			q.conditions.add(c);
		}
		return q;
	}

	DB_TYPE getDBType() {
		if (dbType != null) return dbType;
		if (detectedDbType != null) return detectedDbType;
		detectedDbType = DB_TYPE.detect(getDataSource());
		if (detectedDbType == null) detectedDbType = DB_TYPE.SQL92;
		return detectedDbType;
	}

	static Tuple2<Connection,Boolean> getConnR(final DataSource ds) throws SQLException {
		if (Context.inTransaction(ds)) {
			return new Tuple2<Connection,Boolean>(Context.getConnection(ds), false);
		}
		try {
			if (ds.isWrapperFor(MirroredDataSource.class)) {
				return new Tuple2<Connection,Boolean>(ds.unwrap(MirroredDataSource.class).getMirroredConnection(), true);
			}
		} catch (final AbstractMethodError e) {
			/* ignore - mysql doesn't implement this method */
		} catch (final java.sql.SQLFeatureNotSupportedException e) {
			/* ignore - postgres doesn't implement this method */
		} catch (final java.sql.SQLException e) {
			if ("Object does not wrap anything with requested interface".equals(e.getMessage())) {
				/* ignore - why does oracle throw this instead of returning false?!? */
			} else {
				throw e;
			}
		}
		return new Tuple2<Connection,Boolean>(ds.getConnection(), true);
	}

	static Tuple2<Connection,Boolean> getConnRW(final DataSource ds) throws SQLException {
		if (Context.inTransaction(ds)) {
			return new Tuple2<Connection,Boolean>(Context.getConnection(ds), false);
		}
		return new Tuple2<Connection,Boolean>(ds.getConnection(), true);
	}

	@Override
	public long count() throws SQLException {
		final SqlContext context = new SqlContext(this);
		initTableNameMap(true);
		final List<Object> bindings = new ArrayList<Object>();
		final String fromClause = getFromClause(context, bindings);
		final Tuple2<String, List<Object>> wcab = getWhereClauseAndBindings(context);
		bindings.addAll(wcab.b);
		final String sql = "select count(1)"+ fromClause + wcab.a;
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		Util.log(sql, bindings);
		PreparedStatement ps;
		try {
			ps = createPS(sql, conn);
			setBindings(ps, bindings);
			_preExecute(context, conn);
			ps.execute();
			final ResultSet rs = ps.getResultSet();
			rs.next();
			final long count = rs.getLong(1);
			rs.close();
			ps.close();
			_postExecute(context, conn);
			return count;
		} catch (final SQLException e) {
			throw e;
		} finally {
			if (connInfo.b) {
				conn.close();
			}
		}
	}

	@Override
	public Query<T> exclude(final Condition... conditions) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.conditions = new ArrayList<Condition>();
		if (conditions!=null) q.conditions.addAll(this.conditions);
		for (final Condition c : conditions) {
			q.conditions.add(c.not());
		}
		return q;
	}

	@Override
	public Query<T> orderBy(final Field<?>... fields) {
		return orderBy(ASCENDING, fields);
	}

	@Override
	public Query<T> distinct() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.distinct  = true;
		return q;
	}

	@Override
	public Query<T> deferFields(final Field<?>... fields) {
		return deferFields(Arrays.asList(fields));
	}

	@Override
	public DBQuery<T> deferFields(final Collection<Field<?>> fields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.deferSet = new HashSet<Field<?>>();
		if (deferSet!=null) q.deferSet.addAll(deferSet);
		q.deferSet.addAll(fields);
		if (q.unions != null) {
			for (Union<T> u : unions) {
				u.q = u.q.deferFields(fields);
			}
		}
		return q;
	}

	@Override
	public Query<T> onlyFields(final Field<?>... fields) {
		return onlyFields(Arrays.asList(fields));
	}

	@Override
	public DBQuery<T> onlyFields(final Collection<Field<?>> fields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.onlySet = new LinkedHashSet<Field<?>>();
		for (final Field<?> field : fields) {
			if (field.isBound() && !field.boundTable.equals(tableInfos.get(0).tableName)) {
				throw new RuntimeException("cannot use bound fields " +
						"(ie: field.from(\"x\")) in onlyFields() if you're not bound to the" +
						"primary/first table");
			}
			q.onlySet.add(field);
		}
		if (q.unions != null) {
			for (Union<T> u : unions) {
				u.q = u.q.onlyFields(fields);
			}
		}
		return q;
	}

	@Override
	public int update() throws SQLException {
		final SqlContext context = new SqlContext(this);
		if (data==null || data.size()==0) return 0;
		final DataSource ds = getDataSource();
		this.tableInfos.get(0).tableName = null;
		final String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		final StringBuffer sb = new StringBuffer();
		sb.append("update ");
		final String schema = Util.getSchemaName(ofType);
		if (schema!=null && !"".equals(schema)) {
			sb.append(Context.getSchemaToUse(ds, schema)).append(sep);
		}
		sb.append(Util.getTableName(ofType));
		sb.append(" set ");
		final String[] fields = new String[data.size()];
		final List<Object> bindings = new ArrayList<Object>();
		int i=0;
		for (final Entry<Field<?>, Object> entry : data.entrySet()) {
			final Field<?> field = entry.getKey();
			final Object other = entry.getValue();
			if (other instanceof Field) {
				fields[i++] = field.getSQL(context)+"="+((Field)other).getSQL(context);
				bindings.add(other);
			} else if (other instanceof SQLFunction) {
				final StringBuffer sb2 = new StringBuffer();
				sb2.append(field.getSQL(context)).append("=");
				((SQLFunction)other).getSQL(sb2, bindings, context);
				fields[i++] = sb2.toString();
			} else {
				fields[i++] = field.getSQL(context)+"=?";
				bindings.add(other);
			}
		}
		sb.append(Util.join(", ", fields));
		sb.append(" ");
		initTableNameMap(false);
		final Tuple2<String, List<Object>> wcab = getWhereClauseAndBindings(context);
		sb.append(wcab.a);
		bindings.addAll(wcab.b);
		final String sql = sb.toString();

		Util.log(sql, bindings);
		final Tuple2<Connection,Boolean> info = getConnRW(ds);
		final Connection conn = info.a;
		try {
			final PreparedStatement ps = createPS(sql, conn);
			setBindings(ps, bindings);
			_preExecute(context, conn);
			ps.execute();
			final int count = ps.getUpdateCount();
			ps.close();
			_postExecute(context, conn);
			return count;
		} finally {
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
		}
	}

	@Override
	public int delete() throws SQLException {
		final DBQuery<T> q = new DBQuery<T>(this);
		final SqlContext context = new SqlContext(q);
		final DataSource ds = getDataSource();
		final Tuple2<Connection,Boolean> info = q.getConnRW(ds);
		final Connection conn = info.a;
		q.initTableNameMap(true);
		// this forces the table field dereferencing to use the full table name, not the alias (which is not allowed on deletes)
		q.tableInfos.get(0).tableName = null;
		final Tuple2<String, List<Object>> wcab = q.getWhereClauseAndBindings(context);
		try {
			final String schema = Context.getSchemaToUse(ds, Util.getSchemaName(ofType));
			String schemaWithDot = "".equals(schema) ? "" : schema + ".";
			if (q.getDBType()==DB_TYPE.MYSQL) {
				if (q.tableInfos.size() > 1 || !q.joins.isEmpty()) throw new RuntimeException("MYSQL multi-table delete " +
						"is not yet supported");
				final String sql = "delete from " + schemaWithDot + Util.getTableName(ofType) + wcab.a;
				Util.log(sql, wcab.b);
				final PreparedStatement ps = createPS(sql, conn);
				q.setBindings(ps, wcab.b);
				ps.execute();
				final int count = ps.getUpdateCount();
				ps.close();
				return count;
			} else if (getDBType()==DB_TYPE.SQLITE3) {
				if (q.tableInfos.size() > 1 || !q.joins.isEmpty()) throw new RuntimeException("SQLITE3 multi-table delete " +
						"is not yet supported");
				final String sql = "delete from " + schemaWithDot + Util.getTableName(ofType) + wcab.a;
				Util.log(sql, wcab.b);
				final PreparedStatement ps = createPS(sql, conn);
				q.setBindings(ps, wcab.b);
				ps.execute();
				final int count = ps.getUpdateCount();
				ps.close();
				return count;
			} else if (getDBType()==DB_TYPE.SQLSERVER) {
				if (q.tableInfos.size() > 1 || !q.joins.isEmpty()) throw new RuntimeException("SQLSERVER multi-table delete " +
						"is not yet supported");
				if (!"".equals(schemaWithDot)) schemaWithDot = schemaWithDot + ".";
				final String sql = "delete from "+ schemaWithDot + Util.getTableName(ofType) + wcab.a;
				Util.log(sql, wcab.b);
				final PreparedStatement ps = createPS(sql, conn);
				q.setBindings(ps, wcab.b);
				ps.execute();
				final int count = ps.getUpdateCount();
				ps.close();
				return count;
			} else {
				if (q.tableInfos.size() > 1 || !q.joins.isEmpty()) throw new RuntimeException("multi-table delete " +
						"is not yet supported");
				final String sql = "delete from " + schemaWithDot + Util.getTableName(ofType) + wcab.a;
				Util.log(sql, wcab.b);
				final PreparedStatement ps = createPS(sql, conn);
				q.setBindings(ps, wcab.b);
				ps.execute();
				final int count = ps.getUpdateCount();
				ps.close();
				return count;
			}
		} finally {
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
		}
	}

//	@Override
//	public Statistics stats(final Field<?>... field) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public Iterable<T> all() {
		final DBQuery<T> q = this;
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				return new SelectFromOAI<T>(q);
			}
		};
	}

	@SuppressWarnings("unchecked")
	Tuple2<String,List<Object>> getWhereClauseAndBindings(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		final List<Object> bindings = new ArrayList<Object>();
		List<Condition> conditions = this.conditions;
		if (context.dbType==DB_TYPE.ORACLE && top>0) {
			conditions = conditions==null ? new ArrayList<Condition>() : new ArrayList<Condition>(conditions);
			conditions.add(new Condition.Binary2(new SQLFunction.SQLLiteral("rownum"), "<", top));
		}
		if (conditions!=null && conditions.size()>0) {
			sb.append(" where");
			final String[] tmp = new String[conditions.size()];
			int i=0;
			for (final Condition condition : conditions) {
				tmp[i++] = condition.getSQL(context);
				bindings.addAll(condition.getSQLBindings());
			}
			sb.append(Util.join(" and", tmp));
		}
		return new Tuple2<String,List<Object>>(
				sb.toString(),
				Collections.unmodifiableList(bindings));
	}

	private void initTableNameMap(final boolean bindTables) {
		tableNameMap = new HashMap<String,Set<String>>();
		for (final TableInfo ti : tableInfos) {
			final String id = Util.getSchemaName(ti.tableClass) +"."+ Util.getTableName(ti.tableClass);
			if (!tableNameMap.containsKey(id)) tableNameMap.put(id, new HashSet<String>());
			tableNameMap.get(id).add(bindTables ? ti.tableName : Util.getTableName(ti.tableClass));
		}
		for (final JoinInfo join : joins) {
			TableInfo ti = join.reffingTableInfo;
			if (ti!=null) {
				final String id = Util.getSchemaName(ti.tableClass) +"."+ Util.getTableName(ti.tableClass);
				if (!tableNameMap.containsKey(id)) tableNameMap.put(id, new HashSet<String>());
				tableNameMap.get(id).add(bindTables ? ti.tableName : Util.getTableName(ti.tableClass));
			}
			ti = join.reffedTableInfo;
			final String id = Util.getSchemaName(ti.tableClass) +"."+ Util.getTableName(ti.tableClass);
			if (!tableNameMap.containsKey(id)) tableNameMap.put(id, new HashSet<String>());
			tableNameMap.get(id).add(bindTables ? ti.tableName : Util.getTableName(ti.tableClass));
		}
	}

	/**
	 * @param context
	 * @param bindings2
	 * @return
	 */
	String getJoinClause(final SqlContext context, final List<Object> bindings) {
		final StringBuffer sb = new StringBuffer();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = getDBType();
		final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";

		// explicit joins
		for (final JoinInfo join : joins) {
			final TableInfo tableInfo = join.reffedTableInfo;
			sb.append(" ");
			sb.append(join.type);
			sb.append(" ");
			if (tableInfo.innerQuery!=null) {
				sb.append("(");
				final SqlContext innerContext = new SqlContext(tableInfo.innerQuery, context);
				final Tuple2<String, List<Object>> ret = new DBRowIterator(tableInfo.innerQuery).getSQL(innerContext);
				sb.append(ret.a);
				if (bindings!=null) bindings.addAll(ret.b);
				sb.append(")");
				sb.append(" "+ tableInfo.tableName);
			} else {
				sb.append(context.getFullTableName(tableInfo) +" "+ tableInfo.tableName);
			}
			if (join.condition != null) {
				sb.append(" on ");
				sb.append(join.condition.getSQL(context));
			}
		}

		// "with" joins
		final ArrayList<JoinInfo> joinsToOne = new ArrayList<JoinInfo>(this.joinsToOne);
		final ArrayList<JoinInfo> joinsToMany = new ArrayList<JoinInfo>(this.joinsToMany);
		final Set<Class> seen = new HashSet<Class>();
		seen.add(getType());
		while (!joinsToOne.isEmpty() || !joinsToMany.isEmpty()) {
			for (int i=0; i<joinsToOne.size(); ++i) {
				final JoinInfo join  = joinsToOne.get(i);
				if (!seen.contains(join.reffingTableInfo.tableClass)) continue;
				seen.add(join.reffedTableInfo.tableClass);
				joinsToOne.remove(i--);
				final TableInfo tableInfo = join.reffedTableInfo;
				sb.append(" ");
				sb.append(join.type);
				sb.append(" ");
				sb.append(context.getFullTableName(tableInfo) +" "+ tableInfo.tableName);
				sb.append(" on ");
				sb.append(join.condition.getSQL(context));
			}
			for (int i=0; i<joinsToMany.size(); ++i) {
				final JoinInfo join  = joinsToMany.get(i);
				if (!seen.contains(join.reffedTableInfo.tableClass)) continue;
				seen.add(join.reffingTableInfo.tableClass);
				joinsToMany.remove(i--);
				final TableInfo tableInfo = join.reffingTableInfo;
				sb.append(" ");
				sb.append(join.type);
				sb.append(" ");
				sb.append(context.getFullTableName(tableInfo) +" "+ tableInfo.tableName);
				sb.append(" on ");
				sb.append(join.condition.getSQL(context));
			}
		}
		return sb.toString();
	}

	@Override
	public Query<T> orderBy(final DIRECTION direction, final Field<?>... fields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.orderByDirections = new ArrayList<DIRECTION>();
		if (orderByDirections!=null) q.orderByDirections.addAll(orderByDirections);
		q.orderByFields = new ArrayList<Field<?>>();
		if (orderByFields!=null) q.orderByFields.addAll(orderByFields);
		for (final Field<?> field : fields) {
			if (q.orderByFields.contains(field)) continue;
			q.orderByDirections.add(direction);
			q.orderByFields.add(field);
		}
		q.orderByDirections = Collections.unmodifiableList(q.orderByDirections);
		q.orderByFields = Collections.unmodifiableList(q.orderByFields);
		return q;
	}

	@Override
	public Query<T> limit(final long i) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.top  = i;
		return q;
	}

	private String genTableName(final Class<? extends Table> table, final Collection<String> tableNames) {
		final String name = Util.getTableName(table);
		String base = "";
		for (final String s : name.split("_")) base += s.length() > 0 ? s.substring(0, 1) : "";
		String proposed = null;
		int i = 1;
		while (tableNames.contains((proposed = base + (i==1 ? "" : String.valueOf(i))))) ++i;
		return proposed;
	}

	List<TableInfo> getAllTableInfos() {
		final List<TableInfo> all = new ArrayList<TableInfo>();
		int position = 0;
		final Iterator<TableInfo> it = tableInfos.iterator();
		final TableInfo base = it.next();
		all.add(base);
		base.position = position++;
		for (final JoinInfo join : joins) {
			all.add(join.reffedTableInfo);
			join.reffedTableInfo.position = position++;
		}
		for (final JoinInfo join : joinsToOne) {
			all.add(join.reffedTableInfo);
			join.reffedTableInfo.position = position++;
		}
		for (final JoinInfo join : joinsToMany) {
			all.add(join.reffingTableInfo);
			join.reffingTableInfo.position = position++;
		}
		while (includeCrossInSelect && it.hasNext()) {
			final TableInfo ti = it.next();
			all.add(ti);
			ti.position = position++;
		}
		return all;
	}

	private List<TableInfo> getSelectableTableInfos() {
		final List<TableInfo> all = new ArrayList<TableInfo>();
		all.add(tableInfos.get(0));
		for (final JoinInfo join : joinsToOne) {
			all.add(join.reffedTableInfo);
		}
		for (final JoinInfo join : joinsToMany) {
			all.add(join.reffingTableInfo);
		}
		return all;
	}

	List<Field<?>> getSelectFields(final boolean bind) {
		if (!bind && fields==null || bind && boundFields==null) {
			final List<Field<?>> fields = new ArrayList<Field<?>>();
			int c = 0;
			final List<TableInfo> allTableInfos = onlySelectFromFirstTableAndJoins ?
					getSelectableTableInfos() : getAllTableInfos();
			if(onlySet!=null) {
				final LinkedHashSet<Field<?>> unusedOnlySet = new LinkedHashSet<Field<?>>(onlySet);
				for (final TableInfo ti : allTableInfos) {
					ti.start = c;
					final String tableName = bind ? ti.tableName : null;
					for (final Field<?> other : onlySet) {
						final List<Field<?>> fields_ti = ti.innerQuery==null ? Util.getFields(ti.tableClass) : ti.innerQuery.getSelectFields();
						for (final Field<?> field : fields_ti) {
							if (field.sameField(other) && ti.nameAutogenned) {
								fields.add(bind ? other.from(tableName) : other);
								unusedOnlySet.remove(other);
								++c;
								continue;
							}
							if (other.isBound() && other.boundTable.equals(ti.tableName)
									&& field.sameField(other)) {
								fields.add(bind ? other.from(tableName) : other);
								unusedOnlySet.remove(other);
								++c;
								continue;
							}
						}
					}
					ti.end = c;
				}
				fields.addAll(unusedOnlySet);
			} else {
				for (final TableInfo ti : allTableInfos) {
					ti.start = c;
					final String tableName = bind ? ti.tableName : null;
					for (final Field<?> field : Util.getFields(ti.tableClass)) {
						if(deferSet==null || !deferSet.contains(field)) {
							fields.add(bind ? field.from(tableName) : field);
							++c;
						}
					}
					ti.end = c;
				}
			}
			if (bind) {
				this.boundFields = Collections.unmodifiableList(fields);
			} else {
				this.fields = Collections.unmodifiableList(fields);
			}
		}
		return bind ? boundFields : fields;
	}

	List<DIRECTION> getOrderByDirections() {
		return orderByDirections;
	}

	List<Field<?>> getOrderByFields() {
		return orderByFields;
	}

	public void setBindings(final PreparedStatement ps, final List<Object> bindings) throws SQLException {
		Table main;
		try {
			main = tableInfos.get(0).tableClass.newInstance();
			int i=1;
			if (bindings!=null) {
				for (Object o : bindings) {
					o = main.__NOSCO_PRIVATE_mapType(o);
					Util.setBindingWithTypeFixes(ps, i, o);
					++i;
				}
			}
		} catch (final InstantiationException e) {
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Query<T> set(final Field<?> field, final Object value) {
		final DBQuery<T> q = new DBQuery<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.put(field, value);
		return q;
	}

	@Override
	public Query<T> set(final Map<Field<?>, Object> values) {
		final DBQuery<T> q = new DBQuery<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.putAll(values);
		return q;
	}

	@Override
	public Object insert() throws SQLException {
		final DBQuery<T> q = new DBQuery<T>(this);
		final SqlContext context = new SqlContext(q);
		final DataSource ds = getDataSource();
		final String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		final StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		final String schema = Context.getSchemaToUse(ds, Util.getSchemaName(ofType));
		if (schema != null && !"".equals(schema)) sb.append(schema).append(sep);
		sb.append(Util.getTableName(ofType));
		sb.append(" (");
		final String[] fields = new String[q.data.size()];
		final String[] bindStrings = new String[q.data.size()];
		final List<Object> bindings = new ArrayList<Object>();
		int i=0;
		for (final Entry<Field<?>, Object> entry : q.data.entrySet()) {
			fields[i] = entry.getKey().getSQL(context);
			bindStrings[i] = "?";
			bindings.add(entry.getValue());
			++i;
		}
		sb.append(Util.join(", ", fields));
		sb.append(") values (");
		sb.append(Util.join(", ", bindStrings));
		sb.append(")");
		final String sql = sb.toString();

		Util.log(sql, bindings);
		final Tuple2<Connection,Boolean> info = getConnRW(ds);
		final Connection conn = info.a;
		try {
			if (getDBType()==DB_TYPE.MYSQL) {
				// mysql doesn't clear this var if no auto-increment field exists.
				// set it to a known value so we can test if it was changed by
				// our insert.
				final Statement s = conn.createStatement();
				try {
					s.execute("SELECT LAST_INSERT_ID(0)");
				} finally {
					s.close();
				}
			}
			final PreparedStatement ps = createPS(sql, conn);
			q.setBindings(ps, bindings);
			_preExecute(context, conn);
			ps.execute();
			final int count = ps.getUpdateCount();
			ps.close();
			_postExecute(context, conn);

			if (count==1) {
				if (getDBType()==DB_TYPE.MYSQL) {
					final Statement s = conn.createStatement();
					try {
						s.execute("SELECT LAST_INSERT_ID()");
						final ResultSet rs = s.getResultSet();
						try {
							if (rs.next()) {
								final long pk = rs.getLong(1);
								System.err.println("LAST_INSERT_ID() == "+ pk);
								if (pk!=0) return pk;
							}
						} finally {
							rs.close();
						}
					} finally {
						s.close();
					}
				}
				if (getDBType()==DB_TYPE.SQLITE3) {
					final Statement s = conn.createStatement();
					try {
						s.execute("SELECT last_insert_rowid()");
						final ResultSet rs = s.getResultSet();
						try {
							if (rs.next()) {
								final long pk = rs.getLong(1);
								return pk;
							}
						} finally {
							rs.close();
						}
					} finally {
						s.close();
					}
				}
			}
		} finally {
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
		}

		return null;
	}

	PreparedStatement createPS(final String sql, final Connection conn)
			throws SQLException {
		PreparedStatement ps = conn.prepareStatement(sql);
		if (timeout != null) ps.setQueryTimeout(timeout);
		return ps;
	}

	List<String> getTableNameList(final SqlContext context) {
		final DBQuery<?> rootQuery = context == null ? this : context.getRootQuery();
		final DataSource ds = rootQuery.getDataSource();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = rootQuery.getDBType();
		final List<String> names = new ArrayList<String>();
		for (final TableInfo ti : tableInfos) {
			if (ti.dummyTable != null) {
				names.add((dbType==DB_TYPE.SQLSERVER ? "#" : "") + ti.dummyTable.name +" "+ ti.tableName);
			} else {
				final String schema = Context.getSchemaToUse(ds, Util.getSchemaName(ti.tableClass));
				final String noContextTableName = "".equals(schema) ? Util.getTableName(ti.tableClass) : schema+"."+Util.getTableName(ti.tableClass);
				final String fullTableName = context==null ? noContextTableName : context.getFullTableName(ti);
				String tblNameWithAlias = fullTableName +(ti.tableName==null ? "" : " "+ ti.tableName);
				names.add(tblNameWithAlias);
			}
		}
		//System.err.println(names);
		return names;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> with(final Field.FK... fkFields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		if (q.conditions==null) q.conditions = new ArrayList<Condition>();
		//if (q.fks==null) q.fks = new Tree<Field.FK>();
		try {

			final Class<? extends Table>[] baseTables = new Class[fkFields.length+1];
			baseTables[0] = q.tableInfos.get(0).tableClass;
			for (int i=0; i<fkFields.length; ++i) {
				final Field.FK field = fkFields[i];
				final Field[] refingFields = field.REFERENCING_FIELDS();
				final Field[] reffedFields = field.REFERENCED_FIELDS();
				final Class reffingClass = refingFields[0].TABLE;
				final Class reffedClass = reffedFields[0].TABLE;
				final FK[] prevPath = new FK[i];
				System.arraycopy(fkFields, 0, prevPath, 0, i);
				final FK[] path = new FK[i+1];
				System.arraycopy(fkFields, 0, path, 0, i+1);
				boolean alreadyAdded = false;
				final List<TableInfo> allTableInfos = q.getAllTableInfos();
				TableInfo prevTableInfo = allTableInfos.get(0);
				for (final TableInfo ti : allTableInfos) {
					if (Util.deepEqual(prevPath, ti.path)) {
						prevTableInfo = ti;
					}
					if (Util.deepEqual(path, ti.path)) {
						// we've already added this FK
						alreadyAdded = true;
						// set this so our FK chain detection works
						baseTables[i+1] = ti.tableClass;
						break;
					}
				}
				if (alreadyAdded) continue;

				if (Util.sameTable(reffingClass, baseTables[i])) {
					baseTables[i+1] = reffedClass;
					final String tableName = genTableName(reffedClass, q.usedTableNames);
					q.usedTableNames.add(tableName);
					final TableInfo info = new TableInfo(reffedClass, tableName, path);
					info.nameAutogenned = true;
					final JoinInfo join = new JoinInfo();
					// create the condition
					Condition condition = null;
					for (int j=0; j<refingFields.length; ++j) {
						final Condition condition2 = refingFields[j].from(prevTableInfo).eq(reffedFields[j].from(info));
						if (condition == null) condition = condition2;
						else condition = condition.and(condition2);
					}
					join.condition = condition;
					join.reffingTableInfo  = prevTableInfo;
					join.reffedTableInfo = info;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToOne.add(join);
				} else if (Util.sameTable(reffedClass, baseTables[i])) {
					baseTables[i+1] = reffingClass;
					final String tableName = genTableName(reffingClass, q.usedTableNames);
					q.usedTableNames.add(tableName);
					final TableInfo info = new TableInfo(reffingClass, tableName, path);
					info.nameAutogenned = true;
					final JoinInfo join = new JoinInfo();
					// create the condition
					Condition condition = null;
					for (int j=0; j<refingFields.length; ++j) {
						final Condition condition2 = refingFields[j].from(info).eq(reffedFields[j].from(prevTableInfo));
						if (condition == null) condition = condition2;
						else condition = condition.and(condition2);
					}
					join.condition = condition;
					join.reffingTableInfo = info;
					join.reffedTableInfo = prevTableInfo;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToMany.add(join);
					// i don't think this ordering is necessary...
//					if (q.orderByFields == null) {
//						q.orderByDirections = new ArrayList<DIRECTION>();
//						q.orderByFields = new ArrayList<Field<?>>();
//					}
//					final PK pk = Util.getPK(reffedClass);
//					final List<Field<?>> fields = pk==null ? Util.getFIELDS(reffedClass) : pk.GET_FIELDS();
//					for (final Field<?> f : fields) {
//						if (q.orderByFields.contains(f)) continue;
//						q.orderByFields.add(f);
//						q.orderByDirections.add(ASCENDING);
//					}
				} else {
					throw new IllegalArgumentException("you have a break in your FK chain");
				}
			}

		} catch (final SecurityException e) {
			e.printStackTrace();
		}
		return q;
	}

	@SuppressWarnings("rawtypes")
	static String genTableNameFromFKPathKey(final FK[] fkFields, final int offset, final Table refingTable) {
		final StringBuffer sb = new StringBuffer();
		for (int i=0; i<offset; ++i) {
			final FK fkField = fkFields[i];
			sb.append(fkField.toString());
			sb.append("/");
		}
		sb.append(Util.getSchemaName(refingTable.getClass()));
		sb.append(".");
		sb.append(Util.getTableName(refingTable.getClass()));
		return sb.toString();
	}

	static class JoinInfo<L extends Table, R extends Table> implements Cloneable {
		public FK fk = null;
		public TableInfo reffingTableInfo = null;
		String type = null;
		Class<L> lType = null;
		Class<R> rType = null;
		TableInfo reffedTableInfo = null;
		Condition condition = null;
		@Override
		protected Object clone() throws CloneNotSupportedException {
			final JoinInfo j = new JoinInfo();
			j.fk = fk;
			j.reffingTableInfo = reffingTableInfo;
			j.type = type;
			j.reffedTableInfo = reffedTableInfo;
			j.condition = condition;
			j.lType = lType;
			j.rType = rType;
			return j;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((condition == null) ? 0 : condition.hashCode());
			result = prime * result + ((fk == null) ? 0 : fk.hashCode());
			result = prime * result + ((lType == null) ? 0 : lType.getName().hashCode());
			result = prime * result + ((rType == null) ? 0 : rType.getName().hashCode());
			result = prime
					* result
					+ ((reffedTableInfo == null) ? 0 : reffedTableInfo
							.hashCode());
			result = prime
					* result
					+ ((reffingTableInfo == null) ? 0 : reffingTableInfo
							.hashCode());
			result = prime * result + ((type == null) ? 0 : type.hashCode());
			return result;
		}
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final JoinInfo other = (JoinInfo) obj;
			if (condition == null) {
				if (other.condition != null)
					return false;
			} else if (!condition.equals(other.condition))
				return false;
			if (fk == null) {
				if (other.fk != null)
					return false;
			} else if (!fk.equals(other.fk))
				return false;
			if (lType == null) {
				if (other.lType != null)
					return false;
			} else if (!lType.equals(other.lType))
				return false;
			if (rType == null) {
				if (other.rType != null)
					return false;
			} else if (!rType.equals(other.rType))
				return false;
			if (reffedTableInfo == null) {
				if (other.reffedTableInfo != null)
					return false;
			} else if (!reffedTableInfo.equals(other.reffedTableInfo))
				return false;
			if (reffingTableInfo == null) {
				if (other.reffingTableInfo != null)
					return false;
			} else if (!reffingTableInfo.equals(other.reffingTableInfo))
				return false;
			if (type == null) {
				if (other.type != null)
					return false;
			} else if (!type.equals(other.type))
				return false;
			return true;
		}

	}

	String getFromClause(final SqlContext context, final List<Object> bindings) {
		final List<String> tableNames = getTableNameList(context);
		final String firstTableName = tableNames.get(0);
		final StringBuilder sb = new StringBuilder();
		sb.append(" from "+ firstTableName +" "+ getJoinClause(context, bindings));
		for (final String tableName : tableNames.subList(1, tableNames.size())) {
			sb.append(", ");
			sb.append(tableName);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R, S extends Number> Map<R, S> sumBy(final Field<S> sumField, final Field<R> byField)
			throws SQLException {
		final Map<R, S> ret = new LinkedHashMap<R, S>();
		for (final Entry<R, Map<Field<Number>, Number>> e : sumBy(byField, sumField).entrySet()) {
			ret.put(e.getKey(), (S) e.getValue().get(sumField));
		}
		return ret;
	}

	// i'm not sure i'm ready to expose this yet
	@SuppressWarnings("unchecked")
	//@Override
	public <R> Map<R, Map<Field<Number>,Number>> sumBy(final Field<R> byField, final Field<? extends Number>... sumFields)
			throws SQLException {
		final String function = "sum";
		final Map<R, Map<Field<Number>, Number>> result = terminalAggFunctionBy(
				byField, function, sumFields);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R, S extends Number> Map<R, S> averageBy(final Field<S> sumField, final Field<R> byField)
			throws SQLException {
		final Map<R, S> ret = new LinkedHashMap<R, S>();
		for (final Entry<R, Map<Field<Number>, Number>> e : averageBy(byField, sumField).entrySet()) {
			ret.put(e.getKey(), (S) e.getValue().get(sumField));
		}
		return ret;
	}

	// i'm not sure i'm ready to expose this yet
	@SuppressWarnings("unchecked")
	//@Override
	public <R> Map<R, Map<Field<Number>,Number>> averageBy(final Field<R> byField, final Field<? extends Number>... sumFields) throws SQLException {
		return terminalAggFunctionBy(byField, "avg", sumFields);
	}

	// i'm not sure i'm ready to expose this yet
	@SuppressWarnings("unchecked")
	//@Override
	public <R,S extends Comparable> Map<R, Map<Field<S>,S>> maxBy(final Field<R> byField, final Field<? extends Comparable>... sumFields) throws SQLException {
		return terminalAggFunctionBy(byField, "max", sumFields);
	}

	// i'm not sure i'm ready to expose this yet
	@SuppressWarnings("unchecked")
	//@Override
	public <R,S extends Comparable> Map<R, Map<Field<S>,S>> minBy(final Field<R> byField, final Field<? extends Comparable>... sumFields) throws SQLException {
		return terminalAggFunctionBy(byField, "min", sumFields);
	}

	private <R,S> Map<R, Map<Field<S>, S>> terminalAggFunctionBy(
			final Field<R> byField, final String function,
			final Field<?>... sumFields) throws SQLException {
		final SqlContext context = new SqlContext(this);
		final List<Object> bindings = new ArrayList<Object>();
		final String fromClause = getFromClause(context, bindings);
		initTableNameMap(true);
		final Tuple2<String, List<Object>> wcab = this.getWhereClauseAndBindings(context);
		bindings.addAll(wcab.b);
		String sql = fromClause + wcab.a;
		String sums = "";
		for (final Field<?> sumField : sumFields) {
			sums += ", "+ function +"("+ Util.derefField(sumField, context) +") ";
		}
		sql = "select "+ Util.derefField(byField, context)
				+ sums + sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = createPS(sql, conn);
		setBindings(ps, bindings);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		final Map<R,Map<Field<S>, S>> result = new LinkedHashMap<R,Map<Field<S>, S>>();
		while (rs.next()) {
			final R key = Util.getTypedValueFromRS(rs, 1, byField);
			final Map<Field<S>, S> value = new LinkedHashMap<Field<S>,S>();
			for (int i=0; i<sumFields.length; ++i) {
				value.put((Field<S>) sumFields[i], (S) Util.getTypedValueFromRS(rs, 2+i, sumFields[i]));
			}
			result.put(key, value);
		}
		rs.close();
		ps.close();
		if (connInfo.b) {
			conn.close();
		}
		return result;
	}

	@Override
	public <S extends Number> S sum(final Field<S> sumField) throws SQLException {
		final String function = "sum";
		final S ret = terminalAggFunction(sumField, function);
		return ret;
	}

	@Override
	public <S extends Number> S average(final Field<S> sumField) throws SQLException {
		final String function = "avg";
		final S ret = terminalAggFunction(sumField, function);
		return ret;
	}

	@Override
	public <S extends Comparable> S max(final Field<S> f) throws SQLException {
		final String function = "max";
		final S ret = terminalAggFunction(f, function);
		return ret;
	}

	@Override
	public <R, S extends Comparable> Map<R, S> maxBy(final Field<S> maxField, final Field<R> byField) throws SQLException {
		final Map<R, S> ret = new LinkedHashMap<R, S>();
		for (final Entry<R, Map<Field<Comparable>, Comparable>> e : maxBy(byField, maxField).entrySet()) {
			ret.put(e.getKey(), (S) e.getValue().get(maxField));
		}
		return ret;
	}

	@Override
	public <S extends Comparable> S min(final Field<S> f) throws SQLException {
		final String function = "min";
		final S ret = terminalAggFunction(f, function);
		return ret;
	}

	@Override
	public <R, S extends Comparable> Map<R, S> minBy(final Field<S> minField, final Field<R> byField) throws SQLException {
		final Map<R, S> ret = new LinkedHashMap<R, S>();
		for (final Entry<R, Map<Field<Comparable>, Comparable>> e : maxBy(byField, minField).entrySet()) {
			ret.put(e.getKey(), (S) e.getValue().get(minField));
		}
		return ret;
	}

	private <S> S terminalAggFunction(final Field<S> sumField,
			final String function) throws SQLException {
		final SqlContext context = new SqlContext(this);
		final List<Object> bindings = new ArrayList<Object>();
		final String fromClause = getFromClause(context, bindings);
		initTableNameMap(true);
		final Tuple2<String, List<Object>> wcab = getWhereClauseAndBindings(context);
		bindings.addAll(wcab.b);
		String sql = fromClause + wcab.a;
		sql = "select "+ function +"("+ Util.derefField(sumField, context) +")"+ sql;
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = createPS(sql, conn);
		setBindings(ps, bindings);
		_preExecute(context, conn);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		rs.next();
		final S ret = Util.getTypedValueFromRS(rs, 1, sumField);
		rs.close();
		ps.close();
		_postExecute(context, conn);
		if (connInfo.b) {
			conn.close();
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> Map<S, Integer> countBy(final Field<S> byField) throws SQLException {
		final SqlContext context = new SqlContext(this);
		final List<Object> bindings = new ArrayList<Object>();
		final String fromClause = getFromClause(context, bindings);
		initTableNameMap(true);
		final Tuple2<String, List<Object>> wcab = getWhereClauseAndBindings(context);
		bindings.addAll(wcab.b);
		String sql = fromClause + wcab.a;
		sql = "select "+ Util.derefField(byField, context)
				+", count("+ Util.derefField(byField, context) +")"+ sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = createPS(sql, conn);
		setBindings(ps, bindings);
		_preExecute(context, conn);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		final Map<Object, Integer> result = new LinkedHashMap<Object, Integer>();
		while (rs.next()) {
			Object key = null;
			if (byField.TYPE == Long.class) key = rs.getLong(1); else
			if (byField.TYPE == Double.class) key = rs.getDouble(1); else
			key = rs.getObject(1);
			final int value = rs.getInt(2);
			result.put(key, value);
		}
		rs.close();
		ps.close();
		_postExecute(context, conn);
		if (connInfo.b) {
			conn.close();
		}
		return (Map<S, Integer>) result;
	}

	@Override
	public Query<T> use(final DataSource ds) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.ds = ds;
		return q;
	}

	@Override
	public Query<T> cross(final Class<? extends Table> tableClass) {
		Util.getDefaultDataSource(tableClass);
		final DBQuery<T> q = new DBQuery<T>(this);
		try {
			final TableInfo tableInfo = q.addTable(tableClass);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return q;
	}

	@Override
	public Query<T> cross(final __Alias<? extends Table> tableAlias) {
		final DBQuery<T> q = new DBQuery<T>(this);
		try {
			if (q.usedTableNames.contains(tableAlias.alias)) {
				throw new RuntimeException("table alias "+ tableAlias.alias
						+" already exists in this query");
			}
			q.usedTableNames.add(tableAlias.alias);
			final TableInfo tableInfo = new TableInfo(tableAlias, null);
			q.tableInfos.add(tableInfo);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return q;
	}

	@Override
	public Query<T> toMemory() {
		return new InMemoryQuery<T>(this);
	}

	@Override
	public Query<T> avg() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.globallyAppliedSelectFunction = "avg";
		return q;
	}

	@Override
	public Query<T> max() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.globallyAppliedSelectFunction = "max";
		return q;
	}

	@Override
	public Query<T> min() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.globallyAppliedSelectFunction = "min";
		return q;
	}

	@Override
	public DataSource getDataSource() {
		if (ds != null) return ds;
		final DataSource ds = Context.getDataSource(ofType);
		if (ds != null) return ds;
		return getDefaultDataSource();
	}

	@Override
	public T get(final __PrimaryKey<T> pk) {
		return get(Util.getPK(ofType).eq(pk));
	}

	@Override
	public Query<T> use(final Connection conn) {
		return use(new SingleConnectionDataSource(conn));
	}

	void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
		for (final TableInfo table : tableInfos) {
			table.__NOSCO_PRIVATE_preExecute(context, conn);
		}
		if (conditions != null) {
			for (final Condition c : conditions) {
				c._preExecute(context, conn);
			}
		}
	}

	void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
		if (conditions != null) {
			for (final Condition c : conditions) {
				c._postExecute(context, conn);
			}
		}
		for (final TableInfo table : tableInfos) {
			table.__NOSCO_PRIVATE_postExecute(context, conn);
		}
	}

	@Override
	public List<Field<?>> getSelectFields() {
		return this.getSelectFields(false);
	}

	@Override
	public Iterable<Map<Field<?>, Object>> asIterableOfMaps() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.onlySelectFromFirstTableAndJoins  = false;
		q.includeCrossInSelect = true;
		return new SelectAsMapIterable<T>(q);
	}

	@Override
	public Iterable<Object[]> asIterableOfObjectArrays() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.onlySelectFromFirstTableAndJoins  = false;
		q.includeCrossInSelect = true;
		return new Iterable<Object[]>() {
			@Override
			public Iterator<Object[]> iterator() {
				return new DBRowIterator<T>(q, false);
			}
		};
	}

	@Override
	public Query<T> use(final DB_TYPE type) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.dbType = type;
		return q;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((conditions == null) ? 0 : conditions.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((dbType == null) ? 0 : dbType.hashCode());
//		result = prime * result
//				+ ((defaultDS == null) ? 0 : defaultDS.hashCode());
		result = prime * result
				+ ((deferSet == null) ? 0 : deferSet.hashCode());
		result = prime * result + (distinct ? 1231 : 1237);
		result = prime
				* result
				+ ((globallyAppliedSelectFunction == null) ? 0
						: globallyAppliedSelectFunction.hashCode());
		result = prime * result
				+ ((joinsToMany == null) ? 0 : joinsToMany.hashCode());
		result = prime * result
				+ ((joinsToOne == null) ? 0 : joinsToOne.hashCode());
		result = prime * result + ((onlySet == null) ? 0 : onlySet.hashCode());
		result = prime
				* result
				+ ((orderByDirections == null) ? 0 : orderByDirections
						.hashCode());
		result = prime * result
				+ ((orderByFields == null) ? 0 : orderByFields.hashCode());
		result = prime * result
				+ ((tableInfos == null) ? 0 : tableInfos.hashCode());
		result = prime * result + (int)top;
		result = prime * result
				+ ((usedTableNames == null) ? 0 : usedTableNames.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final DBQuery other = (DBQuery) obj;
		if (conditions == null) {
			if (other.conditions != null)
				return false;
		} else if (!conditions.equals(other.conditions))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (dbType != other.dbType)
			return false;
		if (defaultDS == null) {
			if (other.defaultDS != null)
				return false;
		} else if (!defaultDS.equals(other.defaultDS))
			return false;
		if (deferSet == null) {
			if (other.deferSet != null)
				return false;
		} else if (!deferSet.equals(other.deferSet))
			return false;
		if (distinct != other.distinct)
			return false;
		if (globallyAppliedSelectFunction == null) {
			if (other.globallyAppliedSelectFunction != null)
				return false;
		} else if (!globallyAppliedSelectFunction
				.equals(other.globallyAppliedSelectFunction))
			return false;
		if (joinsToMany == null) {
			if (other.joinsToMany != null)
				return false;
		} else if (!joinsToMany.equals(other.joinsToMany))
			return false;
		if (joinsToOne == null) {
			if (other.joinsToOne != null)
				return false;
		} else if (!joinsToOne.equals(other.joinsToOne))
			return false;
		if (onlySet == null) {
			if (other.onlySet != null)
				return false;
		} else if (!onlySet.equals(other.onlySet))
			return false;
		if (orderByDirections == null) {
			if (other.orderByDirections != null)
				return false;
		} else if (!orderByDirections.equals(other.orderByDirections))
			return false;
		if (orderByFields == null) {
			if (other.orderByFields != null)
				return false;
		} else if (!orderByFields.equals(other.orderByFields))
			return false;
		if (tableInfos == null) {
			if (other.tableInfos != null)
				return false;
		} else if (!tableInfos.equals(other.tableInfos))
			return false;
		if (top != other.top)
			return false;
		if (usedTableNames == null) {
			if (other.usedTableNames != null)
				return false;
		} else if (!usedTableNames.equals(other.usedTableNames))
			return false;
		return true;
	}

	boolean optimizeSelectFields() {
		if (distinct) return false;
		if (onlySet!=null) return false;
		if (deferSet!=null) return false;
		return true;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final Class<S> other) {
		if (!Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, other, null, "cross join", null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final __Alias<S> alias) {
		if (!Util.sameDataSource(this, alias)) return super.crossJoin(alias);
		return new DBQuery<Join<T,S>>(Join.class, this, alias.table, alias.alias, "cross join", null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Class<S> other, final Condition condition) {
		if (!Util.sameDataSource(this, other)) return super.leftJoin(other, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, other, null, "left outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final __Alias<S> alias, final Condition condition) {
		if (!Util.sameDataSource(this, alias)) return super.leftJoin(alias, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, alias.table, alias.alias, "left outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Class<S> other, final Condition condition) {
		if (!Util.sameDataSource(this, other)) return super.rightJoin(other, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, other, null, "right outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final __Alias<S> alias, final Condition condition) {
		if (!Util.sameDataSource(this, alias)) return super.rightJoin(alias, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, alias.table, alias.alias, "right outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Class<S> other, final Condition condition) {
		if (!Util.sameDataSource(this, other)) return super.outerJoin(other, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, other, null, "full outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final __Alias<S> alias, final Condition condition) {
		if (!Util.sameDataSource(this, alias)) return super.outerJoin(alias, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, alias.table, alias.alias, "full outer join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Class<S> other, final Condition condition) {
		if (!Util.sameDataSource(this, other)) return super.innerJoin(other, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, other, null, "inner join", condition);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final __Alias<S> alias, final Condition condition) {
		if (!Util.sameDataSource(this, alias)) return super.innerJoin(alias, condition);
		return new DBQuery<Join<T,S>>(Join.class, this, alias.table, alias.alias, "inner join", condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query<T> in(final Collection<T> set) {
		final String tmpTableName = "#NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		//final String aliasName = "tmp_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		final String aliasName = tmpTableName.replace("#NOSCO_", "tmp_");
		final PK<T> pk = Util.getPK(ofType);
		final TemporaryTableFactory.DummyTableWithName<T> tmp = TemporaryTableFactory.createTemporaryTable(ofType, pk.GET_FIELDS(), set);
		final Table.__Alias<T> alias = new Table.__Alias<T>(tmp, aliasName);
		Query<T> q = cross(alias);
		for (@SuppressWarnings("rawtypes") final Field field : pk.GET_FIELDS()) {
			q = q.where(field.eq(field.from(aliasName)));
		}
		return q;
	}

	@Override
	public Query<T> alsoSelect(final Field<?>... fields) {
		final Collection<Field<?>> fs = new ArrayList<Field<?>>();
		for (final Field<?> f : fields) fs.add(f);
		return alsoSelect(fs);
	}

	@Override
	public Query<T> alsoSelect(final Collection<Field<?>> fields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		if (onlySet==null) {
			q.onlySet = new LinkedHashSet<Field<?>>();
			final Set<Field<?>> left = new LinkedHashSet<Field<?>>(fields);
			for (final Field<?> defaultField : this.getSelectFields()) {
				boolean foundMatch = false;
				for (final Field<?> field : fields) {
					if (field.sameField(defaultField)) {
						foundMatch = true;
						if (field.isBound() && !field.boundTable.equals(tableInfos.get(0).tableName)) {
							throw new RuntimeException("cannot use bound fields " +
									"(ie: field.from(\"x\")) in onlyFields() if you're not bound to the" +
									"primary/first table");
						}
						q.onlySet.add(field);
						left.remove(field);
						break;
					}
				}
				if (!foundMatch) q.onlySet.add(defaultField);
			}
			for (final Field<?> field : left) {
				if (field.isBound() && !field.boundTable.equals(tableInfos.get(0).tableName)) {
					throw new RuntimeException("cannot use bound fields " +
							"(ie: field.from(\"x\")) in onlyFields() if you're not bound to the" +
							"primary/first table");
				}
				q.onlySet.add(field);
			}
		} else {
			q.onlySet = new LinkedHashSet<Field<?>>(onlySet);
			for (final Field<?> field : fields) {
				if (field.isBound() && !field.boundTable.equals(tableInfos.get(0).tableName)) {
					throw new RuntimeException("cannot use bound fields " +
							"(ie: field.from(\"x\")) in onlyFields() if you're not bound to the" +
							"primary/first table");
				}
				q.onlySet.add(field);
			}
		}
		return q;
	}

	@Override
	public <S> Field<S> asInnerQueryOf(final Field<S> field) {
		return new SubQueryField<T,S>(field, (DBQuery<T>) this.onlyFields(field));
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		sb.append("DBQuery<").append(this.getType().getSimpleName()).append(">");
		sb.append("#").append(Integer.toHexString(this.hashCode()));
		return sb.toString();
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final Query<S> other) {
		if (!(other instanceof DBQuery<?>) || !Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, (DBQuery<S>) other, null, "cross join", null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Query<S> other, final Condition on) {
		if (!(other instanceof DBQuery<?>) || !Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, (DBQuery<S>) other, null, "left join", on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Query<S> other, final Condition on) {
		if (!(other instanceof DBQuery<?>) || !Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, (DBQuery<S>) other, null, "right join", on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Query<S> other, final Condition on) {
		if (!(other instanceof DBQuery<?>) || !Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, (DBQuery<S>) other, null, "inner join", on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Query<S> other, final Condition on) {
		if (!(other instanceof DBQuery<?>) || !Util.sameDataSource(this, other)) return super.crossJoin(other);
		return new DBQuery<Join<T,S>>(Join.class, this, (DBQuery<S>) other, null, "outer join", on);
	}

//	@Override
//	public <S extends Table> Query<T> alsoSelect(Query<S> subquery) {
//		if (!(subquery instanceof DBQuery)) return super.alsoSelect(subquery);
//		List<Field<?>> sqFields = subquery.getSelectFields();
//		if (sqFields.size() != 1) {
//			throw new RuntimeException("SQL subqueries are limited to returning only one column.  This query returns "
//					+ sqFields.size() +".  Please select one of the following with subquery.onlyFields(x): "+ sqFields);
//		}
//		Field theField = sqFields.get(0);
//		final DBQuery<T> q = new DBQuery<T>(this);
//		q.onlySet = onlySet==null ? new LinkedHashSet<Field<?>>(this.getSelectFields()) : new LinkedHashSet<Field<?>>(onlySet);
//		q.onlySet.add(new SubQueryField<S,Object>(theField, (DBQuery<S>) subquery));
//		return q;
//	}

	@Override
	public String explainAsText() throws SQLException {
		final DBRowIterator<T> i = new DBRowIterator<T>(this, false);
		return i.explainAsText();
	}

	static class Union<T extends Table> {
		DBQuery<T> q;
		final boolean all;

		Union(DBQuery<T> q, boolean all) {
			this.q = q;
			this.all = all;
		}
	}

	@Override
	public Query<T> union(Query<T> other) {
		return union(other, false);
	}

	private Query<T> union(Query<T> other, boolean all) {
		if (!(other instanceof DBQuery)) {
			throw new RuntimeException("Cannot union a database-backed query to a non-database-backed query.");
		}
		DBQuery<T> q = new DBQuery<T>(this);
		if (q.unions == null) q.unions = new ArrayList<Union<T>>();
		q.unions.add(new Union<T>((DBQuery<T>) other, all));
		return q;
	}

	@Override
	public Query<T> unionAll(Query<T> other) {
		return union(other, true);
	}

	@Override
	public Query<T> setQueryTimeout(int seconds) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.timeout = seconds;
		return q;
	}

}
