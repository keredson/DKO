package org.nosco;

import static org.nosco.Constants.DIRECTION.ASCENDING;
import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.Field.PK;
import org.nosco.Table.__Alias;
import org.nosco.Table.__PrimaryKey;
import org.nosco.Tuple.Tuple2;
import org.nosco.datasource.MirroredDataSource;
import org.nosco.datasource.SingleConnectionDataSource;


class DBQuery<T extends Table> extends AbstractQuery<T> {

	// make sure the UsageMonitor is class-loaded
	static { UsageMonitor.doNothing(); }

	// genned once and cached
	private String sql;
	private List<Field<?>> fields;
	private List<Field<?>> boundFields;
	List<Object> bindings = null;
	Map<String,Set<String>> tableNameMap = null;
	DB_TYPE detectedDbType = null;

	// these should be cloned
	List<Condition> conditions = null;
	List<Table> tables = new ArrayList<Table>();
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
	private boolean onlySelectFromFirstTableAndJoins = true;

	DBQuery(final T table) {
		super(table.getClass());
		addTable(table);
	}

	private TableInfo addTable(final Table table) {
		tables.add(table);
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
		tables.addAll(q.tables);
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
	}

	DBQuery(final Class<T> tableClass) {
		super(tableClass);
		try {
			final Table table = tableClass.getConstructor().newInstance();
			addTable(table);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	<S extends Table> DBQuery(final DBQuery<T> q, final String joinType, final Class<S> other, String alias, final Condition condition) {
		this(q);
		final JoinInfo<T,S> ji = new JoinInfo<T,S>();
		ji.lType = type;
		ji.rType = other;
		ji.type = "inner join";
		ji.condition = condition;
		Table otherInstance;
		try {
			otherInstance = other.newInstance();
			final boolean autogenName = alias == null;
			if (autogenName) alias = genTableName(otherInstance, usedTableNames);
			usedTableNames.add(alias);
			ji.reffedTableInfo = new TableInfo(otherInstance, alias, null);
			ji.reffedTableInfo.nameAutogenned = autogenName;
			joins.add(ji);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private DataSource getDefaultDS() {
		try {
			if (defaultDS != null) return defaultDS;
			final java.lang.reflect.Field field = type.getDeclaredField("__DEFAULT_DATASOURCE");
			field.setAccessible(true);
			defaultDS = (DataSource) field.get(null);
			return defaultDS;
		} catch (final SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (final NoSuchFieldException e) {
			final String msg = "No default datasource defined for "+ type +".  Please either call " +
					"your query with .use(DataSource ds), or define the 'datasource' field " +
					"in the org.nosco.ant.CodeGenerator ant task.";
			throw new RuntimeException(msg, e);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	DBQuery(final Class<T> tableClass, final DataSource ds) {
		this(tableClass);
		this.ds = ds;
	}

	public DBQuery(final __Alias<T> alias) {
		super(alias.table);
		try {
			final Table table = alias.instance;
			tables.add(table);
			usedTableNames.add(alias.alias);
			final TableInfo info = new TableInfo(table, alias.alias, null);
			info.nameAutogenned = false;
			tableInfos.add(info);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}
	}

	<T1 extends Table, T2 extends Table> DBQuery(final Class<? extends Join.J> type, final Query<T1> q, final Class<T2> other, final String joinType) {
		super(type);
		copy((DBQuery<T>) q);
		final JoinInfo<T1,T2> ji = new JoinInfo<T1,T2>();
		ji.lType = q.getType();
		ji.rType = other;
		ji.type = joinType;
		ji.condition = null;
		String alias = null;
		try {
			final Table otherInstance = other.newInstance();
			final boolean autogenName = alias == null;
			if (autogenName) alias = genTableName(otherInstance, usedTableNames);
			usedTableNames.add(alias);
			ji.reffedTableInfo = new TableInfo(otherInstance, alias, null);
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
		return new Select<T>(this);
	}

	private void sanityCheckToManyJoins() {
		final List<Field<?>> selectFields = getSelectFields();
		for (final JoinInfo join : this.joinsToMany) {
			final List<Field<?>> missing = new ArrayList<Field<?>>();
			for (final Field<?> f1 : Util.getPK(join.reffedTableInfo.table).GET_FIELDS()) {
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
		final String sql = "select count(1)"+ getFromClause(context) + getWhereClauseAndSetBindings();
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		Util.log(sql, bindings);
		final PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		rs.next();
		final long count = rs.getLong(1);
		rs.close();
		ps.close();
		_postExecute(context, conn);
		if (connInfo.b) {
			conn.close();
		}
		return count;
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
		final DBQuery<T> q = new DBQuery<T>(this);
		q.deferSet = new HashSet<Field<?>>();
		if (deferSet!=null) q.deferSet.addAll(deferSet);
		for (final Field<?> field : fields) {
			q.deferSet.add(field);
		}
		return q;
	}

	@Override
	public Query<T> deferFields(final Collection<Field<?>> fields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.deferSet = new HashSet<Field<?>>();
		if (deferSet!=null) q.deferSet.addAll(deferSet);
		q.deferSet.addAll(fields);
		return q;
	}

	@Override
	public Query<T> onlyFields(final Field<?>... fields) {
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
		return q;
	}

	@Override
	public Query<T> onlyFields(final Collection<Field<?>> fields) {
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
		return q;
	}

	@Override
	public int update() throws SQLException {
		final SqlContext context = new SqlContext(this);
		if (data==null || data.size()==0) return 0;
		final DataSource ds = getDataSource();
		final Table table = tables.get(0);
		this.tableInfos.get(0).tableName = null;
		final String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		final StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(Context.getSchemaToUse(ds, table.SCHEMA_NAME()) +sep+ table.TABLE_NAME());
		sb.append(" set ");
		final String[] fields = new String[data.size()];
		final List<Object> bindings = new ArrayList<Object>();
		int i=0;
		for (final Entry<Field<?>, Object> entry : data.entrySet()) {
			fields[i++] = entry.getKey().getSQL(context)+"=?";
			bindings.add(entry.getValue());
		}
		sb.append(Util.join(", ", fields));
		sb.append(" ");
		sb.append(getWhereClauseAndSetBindings(false));
		bindings.addAll(this.bindings);
		final String sql = sb.toString();

		Util.log(sql, bindings);
		final Tuple2<Connection,Boolean> info = getConnRW(ds);
		final Connection conn = info.a;
		final PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps, bindings);
		_preExecute(context, conn);
		ps.execute();
		final int count = ps.getUpdateCount();
		ps.close();
		_postExecute(context, conn);
		if (info.b) {
			if (!conn.getAutoCommit()) conn.commit();
			conn.close();
		}

		return count;
	}

	@Override
	public int delete() throws SQLException {
		final DBQuery<T> q = new DBQuery<T>(this);
		final DataSource ds = getDataSource();
		final Tuple2<Connection,Boolean> info = q.getConnRW(ds);
		final Connection conn = info.a;
		if (q.getDBType()==DB_TYPE.MYSQL) {
			if (q.tables.size() > 1) throw new RuntimeException("MYSQL multi-table delete " +
					"is not yet supported");
			final Table t = q.tables.get(0);
			q.tableInfos.get(0).tableName = null;
			final String sql = "delete from "+ Context.getSchemaToUse(ds, t.SCHEMA_NAME())
					+ "." + t.TABLE_NAME() + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			final PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			final int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;

		} else if (getDBType()==DB_TYPE.SQLSERVER) {
			if (q.tables.size() > 1) throw new RuntimeException("SQLSERVER multi-table delete " +
					"is not yet supported");
			final Table t = q.tables.get(0);
			q.tableInfos.get(0).tableName = null;
			final String sql = "delete from "+ Context.getSchemaToUse(ds, t.SCHEMA_NAME())
					+ ".dbo." + t.TABLE_NAME() + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			final PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			final int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;

		} else {
			final String sql = "delete from "+ Util.join(", ", q.getTableNameList()) + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			final PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			final int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
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
				return new Select<T>(q);
			}
		};
	}

///	@Override
//	public Query<Table> join(Field field, Relationship equals, Field field2) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	String getWhereClauseAndSetBindings() {
		return getWhereClauseAndSetBindings(true);
	}

	@SuppressWarnings("unchecked")
	Tuple2<String,List<Object>> getWhereClauseAndBindings(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		final List<Object> bindings = new ArrayList<Object>();
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

	@SuppressWarnings("unchecked")
	String getWhereClauseAndSetBindings(final boolean bindTables) {
		if (sql==null) {

			final StringBuffer sb = new StringBuffer();
			bindings = new ArrayList<Object>();

			tableNameMap = new HashMap<String,Set<String>>();
			for (final TableInfo ti : tableInfos) {
				final String id = ti.table.SCHEMA_NAME() +"."+ ti.table.TABLE_NAME();
				if (!tableNameMap.containsKey(id)) tableNameMap.put(id, new HashSet<String>());
				tableNameMap.get(id).add(bindTables ? ti.tableName : ti.table.TABLE_NAME());
			}

			final SqlContext context = new SqlContext(this);

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

			sql = sb.toString();
			if (bindings!=null) bindings = Collections.unmodifiableList(bindings);
		}
		return sql;
	}

	List<Object> getSQLBindings() {
		return bindings;
	}

	/**
	 * @param context
	 * @return
	 */
	String getJoinClause(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = getDBType();
		final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";

		// explicit joins
		for (final JoinInfo join : joins) {
			final TableInfo tableInfo = join.reffedTableInfo;
			final Table table = tableInfo.table;
			sb.append(" ");
			sb.append(join.type);
			sb.append(" ");
			sb.append(context.getFullTableName(table) +" "+ tableInfo.tableName);
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
				final Table table = tableInfo.table;
				sb.append(" ");
				sb.append(join.type);
				sb.append(" ");
				sb.append(context.getFullTableName(table) +" "+ tableInfo.tableName);
				sb.append(" on ");
				sb.append(join.condition.getSQL(context));
			}
			for (int i=0; i<joinsToMany.size(); ++i) {
				final JoinInfo join  = joinsToMany.get(i);
				if (!seen.contains(join.reffedTableInfo.tableClass)) continue;
				seen.add(join.reffingTableInfo.tableClass);
				joinsToMany.remove(i--);
				final TableInfo tableInfo = join.reffingTableInfo;
				final Table table = tableInfo.table;
				sb.append(" ");
				sb.append(join.type);
				sb.append(" ");
				sb.append(context.getFullTableName(table) +" "+ tableInfo.tableName);
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

	private String genTableName(final Table table, final Collection<String> tableNames) {
		final String name = table.TABLE_NAME();
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
		for (final TableInfo ti : tableInfos) {
			all.add(ti);
			ti.position = position++;
		}
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
				for (final Field<?> other : onlySet) {
					for (final TableInfo ti : allTableInfos) {
						ti.start = c;
						final String tableName = bind ? ti.tableName : null;
						for (final Field<?> field : ti.table.FIELDS()) {
							if (field == other && ti.nameAutogenned) {
								fields.add(bind ? field.from(tableName) : field);
								++c;
								continue;
							}
							if (other.isBound() && other.boundTable.equals(ti.tableName)
									&& field.sameField(other)) {
								fields.add(bind ? field.from(tableName) : field);
								++c;
								continue;
							}
						}
						ti.end = c;
					}
				}
			} else {
				for (final TableInfo ti : allTableInfos) {
					ti.start = c;
					final String tableName = bind ? ti.tableName : null;
					for (final Field<?> field : ti.table.FIELDS()) {
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

	public void setBindings(final PreparedStatement ps) throws SQLException {
		setBindings(ps, bindings);
	}

	public void setBindings(final PreparedStatement ps, final List<Object> bindings) throws SQLException {
		final Table main = tables.get(0);
		int i=1;
		if (bindings!=null) {
			for (Object o : bindings) {
				o = main.__NOSCO_PRIVATE_mapType(o);
				// hack for sql server which otherwise gives:
				// com.microsoft.sqlserver.jdbc.SQLServerException:
				// The conversion from UNKNOWN to UNKNOWN is unsupported.
				if (o instanceof Character) ps.setString(i++, o.toString());
				else ps.setObject(i++, o);
				//System.err.print("\t"+ o +"");
			}
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
		final Table table = q.tables.get(0);
		final String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		final StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(Context.getSchemaToUse(ds, table.SCHEMA_NAME()));
		sb.append(sep+ table.TABLE_NAME());
		sb.append(" (");
		final String[] fields = new String[q.data.size()];
		final String[] bindStrings = new String[q.data.size()];
		q.bindings = new ArrayList<Object>();
		int i=0;
		for (final Entry<Field<?>, Object> entry : q.data.entrySet()) {
			fields[i] = entry.getKey().getSQL(context);
			bindStrings[i] = "?";
			q.bindings.add(entry.getValue());
			++i;
		}
		sb.append(Util.join(", ", fields));
		sb.append(") values (");
		sb.append(Util.join(", ", bindStrings));
		sb.append(")");
		final String sql = sb.toString();

		Util.log(sql, q.bindings);
		final Tuple2<Connection,Boolean> info = getConnRW(ds);
		final Connection conn = info.a;
		final PreparedStatement ps = conn.prepareStatement(sql);
		q.setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		final int count = ps.getUpdateCount();
		ps.close();
		_postExecute(context, conn);

		if (count==1) {
			if (getDBType()==DB_TYPE.MYSQL) {
				final Statement s = conn.createStatement();

				s.execute("SELECT LAST_INSERT_ID()");
				final ResultSet rs = s.getResultSet();
				if (rs.next()) {
					final Integer pk = rs.getInt(1);
					return pk;
				}
			}
		}
		if (info.b) {
			if (!conn.getAutoCommit()) conn.commit();
			conn.close();
		}

		return null;
	}

	List<String> getTableNameList() {
		return getTableNameList(null);
	}

	List<String> getTableNameList(final SqlContext context) {
		final DBQuery<?> rootQuery = context == null ? this : context.getRootQuery();
		final DataSource ds = rootQuery.getDataSource();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = rootQuery.getDBType();
		final List<String> names = new ArrayList<String>();
		final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		for (final TableInfo ti : tableInfos) {
			final Table t = ti.table;
			final String fullTableName = context==null ? t.SCHEMA_NAME()+"."+t.TABLE_NAME() : context.getFullTableName(t);
			names.add(fullTableName +" "+ ti.tableName);
		}
		return names;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> with(final Field.FK... fkFields) {
		final DBQuery<T> q = new DBQuery<T>(this);
		if (q.conditions==null) q.conditions = new ArrayList<Condition>();
		//if (q.fks==null) q.fks = new Tree<Field.FK>();
		try {

			final Table[] baseTables = new Table[fkFields.length+1];
			baseTables[0] = q.tables.get(0);
			for (int i=0; i<fkFields.length; ++i) {
				final Field.FK field = fkFields[i];
				final Field[] refingFields = field.REFERENCING_FIELDS();
				final Field[] reffedFields = field.REFERENCED_FIELDS();
				final Table refingTable = (Table) refingFields[0].TABLE.newInstance();
				final Table reffedTable = (Table) reffedFields[0].TABLE.newInstance();
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
						baseTables[i+1] = ti.table;
						break;
					}
				}
				if (alreadyAdded) continue;

				// create the condition
				Condition condition = null;
				for (int j=0; j<refingFields.length; ++j) {
					final Condition condition2 = refingFields[j].eq(reffedFields[j]);
					if (condition == null) condition = condition2;
					else condition = condition.and(condition2);
				}

				if (Util.sameTable(refingTable, baseTables[i])) {
					baseTables[i+1] = reffedTable;
					final String tableName = genTableName(reffedTable, q.usedTableNames);
					q.usedTableNames.add(tableName);
					final TableInfo info = new TableInfo(reffedTable, tableName, path);
					info.nameAutogenned = true;
					final JoinInfo join = new JoinInfo();
					join.condition = condition;
					join.reffingTableInfo  = prevTableInfo;
					join.reffedTableInfo = info;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToOne.add(join);
				} else if (Util.sameTable(reffedTable, baseTables[i])) {
					baseTables[i+1] = refingTable;
					final String tableName = genTableName(refingTable, q.usedTableNames);
					q.usedTableNames.add(tableName);
					final TableInfo info = new TableInfo(refingTable, tableName, path);
					info.nameAutogenned = true;
					final JoinInfo join = new JoinInfo();
					join.condition = condition;
					join.reffingTableInfo = info;
					join.reffedTableInfo = prevTableInfo;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToMany.add(join);
					if (q.orderByFields == null) {
						q.orderByDirections = new ArrayList<DIRECTION>();
						q.orderByFields = new ArrayList<Field<?>>();
					}
					final PK pk = Util.getPK(reffedTable);
					final List<Field<?>> fields = pk==null ? reffedTable.FIELDS() : pk. GET_FIELDS();
					for (final Field<?> f : fields) {
						if (q.orderByFields.contains(f)) continue;
						q.orderByFields.add(f);
						q.orderByDirections.add(ASCENDING);
					}
				} else {
					throw new IllegalArgumentException("you have a break in your FK chain");
				}
			}

		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
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
		sb.append(refingTable.SCHEMA_NAME());
		sb.append(".");
		sb.append(refingTable.TABLE_NAME());
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
	}

	String getFromClause(final SqlContext context) {
		final List<String> tableNames = getTableNameList(context);
		final String firstTableName = tableNames.get(0);
		final StringBuilder sb = new StringBuilder();
		sb.append(" from "+ firstTableName +" "+ getJoinClause(context));
		for (final String tableName : tableNames.subList(1, tableNames.size())) {
			sb.append(", ");
			sb.append(tableName);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> Map<S, Double> sumBy(final Field<? extends Number> sumField, final Field<S> byField)
			throws SQLException {
		final SqlContext context = new SqlContext(this);
		String sql = getFromClause(context) + getWhereClauseAndSetBindings();
		sql = "select "+ Util.derefField(byField, context)
				+", sum("+ Util.derefField(sumField, context) +") "+ sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		final Map<Object, Double> result = new LinkedHashMap<Object, Double>();
		while (rs.next()) {
			Object key = null;
			if (byField.TYPE == Long.class) key = rs.getLong(1); else
			if (byField.TYPE == Double.class) key = rs.getDouble(1); else
			key = rs.getObject(1);
			final Double value = rs.getDouble(2);
			result.put(key, value);
		}
		rs.close();
		ps.close();
		if (connInfo.b) {
			conn.close();
		}
		return (Map<S, Double>) result;
	}

	@Override
	public Double sum(final Field<? extends Number> sumField) throws SQLException {
		final SqlContext context = new SqlContext(this);
		String sql = getFromClause(context) + getWhereClauseAndSetBindings();
		sql = "select sum("+ Util.derefField(sumField, context) +")"+ sql;
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		final ResultSet rs = ps.getResultSet();
		rs.next();
		final Double ret = rs.getDouble(1);
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
		String sql = getFromClause(context)
				+ getWhereClauseAndSetBindings();
		sql = "select "+ Util.derefField(byField, context)
				+", count("+ Util.derefField(byField, context) +")"+ sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		final Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		final Connection conn = connInfo.a;
		final PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
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

	public Query<T> use(final DataSource ds) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.ds = ds;
		return q;
	}

	@Override
	public Query<T> cross(final Class<? extends Table> tableClass) {
		final DBQuery<T> q = new DBQuery<T>(this);
		try {
			final Table table = tableClass.getConstructor().newInstance();
			q.addTable(table);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		return q;
	}

	@Override
	public Query<T> cross(final __Alias<? extends Table> tableAlias) {
		final DBQuery<T> q = new DBQuery<T>(this);
		try {
			q.tables.add(tableAlias.instance);
			if (q.usedTableNames.contains(tableAlias.alias)) {
				throw new RuntimeException("table alias "+ tableAlias.alias
						+" already exists in this query");
			}
			q.usedTableNames.add(tableAlias.alias);
			q.tableInfos.add(new TableInfo(tableAlias.instance, tableAlias.alias, null));
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
	public Condition exists() {
		return new Condition.Exists(this);
	}

	@Override
	public DataSource getDataSource() {
		if (ds != null) return ds;
		final DataSource ds = Context.getDataSource(type);
		if (ds != null) return ds;
		return getDefaultDS();
	}

	@Override
	public <S> Iterable<S> select(final Field<S> field) {
		return new SelectSingleColumn<S>(this, field);
	}

	@Override
	public T get(final __PrimaryKey<T> pk) {
		return get(Util.getPK((T)tables.get(0)).eq(pk));
	}

	@Override
	public Query<T> use(final Connection conn) {
		return use(new SingleConnectionDataSource(conn));
	}

	void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
		for (final Table table : tables) {
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
		for (final Table table : tables) {
			table.__NOSCO_PRIVATE_postExecute(context, conn);
		}
	}

	@Override
	public List<Field<?>> getSelectFields() {
		return this.getSelectFields(false);
	}

	@Override
	public Iterable<Object[]> asIterableOfObjectArrays() {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.onlySelectFromFirstTableAndJoins  = false;
		return new SelectAsObjectArrayIterable<T>(q);
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
		result = prime * result
				+ ((defaultDS == null) ? 0 : defaultDS.hashCode());
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
		result = prime * result + ((tables == null) ? 0 : tables.hashCode());
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
		if (tables == null) {
			if (other.tables != null)
				return false;
		} else if (!tables.equals(other.tables))
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
	public <S extends Table> Query<T> crossJoin(final Class<S> other) {
		return new DBQuery<T>(this, "cross join", other, null, null);
	}

	@Override
	public <S extends Table> Query<T> crossJoin(final __Alias<S> alias) {
		return new DBQuery<T>(this, "cross join", alias.table, alias.alias, null);
	}

	@Override
	public <S extends Table> Query<T> leftJoin(final Class<S> other,
			final Condition condition) {
		return new DBQuery<T>(this, "left outer join", other, null, condition);
	}

	@Override
	public <S extends Table> Query<T> leftJoin(final __Alias<S> alias,
			final Condition condition) {
		return new DBQuery<T>(this, "left outer join", alias.table, alias.alias, condition);
	}

	@Override
	public <S extends Table> Query<T> rightJoin(final Class<S> other,
			final Condition condition) {
		return new DBQuery<T>(this, "right outer join", other, null, condition);
	}

	@Override
	public <S extends Table> Query<T> rightJoin(final __Alias<S> alias,
			final Condition condition) {
		return new DBQuery<T>(this, "right outer join", alias.table, alias.alias, condition);
	}

	@Override
	public <S extends Table> Query<T> outerJoin(final Class<S> other,
			final Condition condition) {
		return new DBQuery<T>(this, "full outer join", other, null, condition);
	}

	@Override
	public <S extends Table> Query<T> outerJoin(final __Alias<S> alias,
			final Condition condition) {
		return new DBQuery<T>(this, "full outer join", alias.table, alias.alias, condition);
	}

	@Override
	public <S extends Table> Query<T> innerJoin(final Class<S> other,
			final Condition condition) {
		return new DBQuery<T>(this, "inner join", other, null, condition);
	}

	@Override
	public <S extends Table> Query<T> innerJoin(final __Alias<S> alias, final Condition condition) {
		return new DBQuery<T>(this, "inner join", alias.table, alias.alias, condition);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query<T> in(final Collection<T> set) {
		final String tmpTableName = "#NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		//final String aliasName = "tmp_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		final String aliasName = tmpTableName.replace("#NOSCO_", "tmp_");
		final PK<T> pk = Util.getPK(type);
		final T tmp = TemporaryTableFactory.createTemporaryTable(type, pk.GET_FIELDS(), set);
		final Table.__Alias<T> alias = new Table.__Alias<T>(tmp, aliasName);
		Query<T> q = cross(alias);
		for (@SuppressWarnings("rawtypes") final Field field : pk.GET_FIELDS()) {
			q = q.where(field.eq(field.from(aliasName)));
		}
		return q;
	}

}
