package org.nosco;

import static org.nosco.Constants.DIRECTION.ASCENDING;
import static org.nosco.Constants.DIRECTION.DESCENDING;

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
import org.nosco.datasource.MirroredDataSource;
import org.nosco.datasource.SingleConnectionDataSource;


class DBQuery<T extends Table> implements Query<T> {

	// genned once and cached
	private String sql;
	private Field<?>[] fields;
	private Field<?>[] boundFields;
	List<Object> bindings = null;
	Map<String,Set<String>> tableNameMap = null;
	DB_TYPE detectedDbType = null;

	// these should be cloned
	Class<? extends Table> type = null;
	List<Condition> conditions = null;
	List<Table> tables = new ArrayList<Table>();
	Set<String> usedTableNames = new HashSet<String>();
	List<TableInfo> tableInfos = new ArrayList<TableInfo>();
	List<Join> joinsToOne = new ArrayList<Join>();
	List<Join> joinsToMany = new ArrayList<Join>();
	private Set<Field<?>> deferSet = null;
	private Set<Field<?>> onlySet = null;
	private List<DIRECTION> orderByDirections = null;
	private List<Field<?>> orderByFields = null;
	int top = 0;
	private Map<Field<?>,Object> data = null;
	boolean distinct = false;
	DataSource ds = null;
	DataSource defaultDS = null;
	String globallyAppliedSelectFunction = null;
	DB_TYPE dbType = null;

	DBQuery(Table table) {
		addTable(table);
	}

	private TableInfo addTable(Table table) {
		tables.add(table);
		String tableName = genTableName(table, usedTableNames);
		usedTableNames.add(tableName);
		TableInfo info = new TableInfo(table, tableName, null);
		info.nameAutogenned = true;
		tableInfos.add(info);
		return info;
	}

	DBQuery(DBQuery<T> q) {
		if (q.conditions!=null) {
			conditions = new ArrayList<Condition>();
			conditions.addAll(q.conditions);
		}
		type = q.type;
		tables.addAll(q.tables);
		usedTableNames.addAll(q.usedTableNames);
		try {
			for (Join x : q.joinsToOne) joinsToOne.add((Join) x.clone());
			for (Join x : q.joinsToMany) joinsToMany.add((Join) x.clone());
			for (TableInfo x : q.tableInfos) {
				TableInfo clone = (TableInfo) x.clone();
				for (Join y : joinsToOne) {
					if (y.reffedTableInfo == x) y.reffedTableInfo = clone;
					if (y.reffingTableInfo == x) y.reffingTableInfo = clone;
				}
				for (Join y : joinsToMany) {
					if (y.reffedTableInfo == x) y.reffedTableInfo = clone;
					if (y.reffingTableInfo == x) y.reffingTableInfo = clone;
				}
				tableInfos.add(clone);
			}
		} catch (CloneNotSupportedException e) { /* ignore */ }
		if (q.deferSet!=null) {
			deferSet = new HashSet<Field<?>>();
			deferSet.addAll(q.deferSet);
		}
		if (q.onlySet!=null) {
			onlySet = new HashSet<Field<?>>();
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
	}

	DBQuery(Class<? extends Table> tableClass) {
		try {
			type = tableClass;
			Table table = tableClass.getConstructor().newInstance();
			addTable(table);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	private DataSource getDefaultDS() {
		try {
			if (defaultDS != null) return defaultDS;
			java.lang.reflect.Field field = type.getDeclaredField("__DEFAULT_DATASOURCE");
			field.setAccessible(true);
			defaultDS = (DataSource) field.get(null);
			return defaultDS;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchFieldException e) {
			String msg = "No default datasource defined for "+ type +".  Please either call " +
					"your query with .use(DataSource ds), or define the 'datasource' field " +
					"in the org.nosco.ant.CodeGenerator ant task.";
			throw new RuntimeException(msg, e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	DBQuery(Class<? extends Table> tableClass, DataSource ds) {
		this(tableClass);
		this.ds = ds;
	}

	public DBQuery(__Alias<? extends Table> alias) {
		type = alias.table;
		try {
			Table table = alias.table.getConstructor().newInstance();
			tables.add(table);
			usedTableNames.add(alias.alias);
			TableInfo info = new TableInfo(table, alias.alias, null);
			info.nameAutogenned = false;
			tableInfos.add(info);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterator<T> iterator() {
		sanityCheckToManyJoins();
		return new Select<T>(this).iterator();
	}

	private void sanityCheckToManyJoins() {
		Field<?>[] selectFields = getSelectFields();
		for (Join join : this.joinsToMany) {
			List<Field<?>> missing = new ArrayList<Field<?>>();
			for (Field<?> f1 : Util.getPK(join.reffedTableInfo.table).GET_FIELDS()) {
				boolean found = false;
				for (Field<?> f2 : selectFields) {
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
				String msg = "The following fields have been excluded from your selected fields list: "
						+ missing +" but are required by the join you're using: "+ join.fk;
				throw new RuntimeException(msg);
			}
		}
	}

	@Override
	public Query<T> where(Condition... conditions) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.conditions = new ArrayList<Condition>();
		if (this.conditions!=null) q.conditions.addAll(this.conditions);
		for (Condition c : conditions) {
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

	Tuple2<Connection,Boolean> getConnR(DataSource ds) throws SQLException {
		Context context = Context.getThreadContext();
		if (context.inTransaction(ds)) {
			return new Tuple2<Connection,Boolean>(context.getConnection(ds), false);
		}
		try {
			if (ds.isWrapperFor(MirroredDataSource.class)) {
				return new Tuple2<Connection,Boolean>(ds.unwrap(MirroredDataSource.class).getMirroredConnection(), true);
			}
		} catch (AbstractMethodError e) {
			/* ignore - mysql doesn't implement this method */
		}
		return new Tuple2<Connection,Boolean>(ds.getConnection(), true);
	}

	Tuple2<Connection,Boolean> getConnRW(DataSource ds) throws SQLException {
		Context context = Context.getThreadContext();
		if (context.inTransaction(ds)) {
			return new Tuple2<Connection,Boolean>(context.getConnection(ds), false);
		}
		return new Tuple2<Connection,Boolean>(ds.getConnection(), true);
	}

	@Override
	public int count() throws SQLException {
		SqlContext context = new SqlContext(this);
		String sql = "select count(1)"+ getFromClause(context) + getWhereClauseAndSetBindings();
		Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		Connection conn = connInfo.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		_preExecute(context, conn);
		Util.log(sql, null);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		rs.next();
		int count = rs.getInt(1);
		rs.close();
		ps.close();
		_postExecute(context, conn);
		if (connInfo.b) {
			conn.close();
		}
		return count;
	}

	@Override
	public T get(Condition... conditions) {
		//Field[] fields = tables.get(0).FIELDS();
		DBQuery<T> q = new DBQuery<T>(this);
		if (conditions!=null && conditions.length>0) {
			if (q.conditions == null) q.conditions = new ArrayList<Condition>();
			for (Condition condition : conditions) {
				q.conditions.add(condition);
			}
		}
		return q.getTheOnly();
	}

	@Override
	public Query<T> exclude(Condition... conditions) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.conditions = new ArrayList<Condition>();
		if (conditions!=null) q.conditions.addAll(this.conditions);
		for (Condition c : conditions) {
			q.conditions.add(c.not());
		}
		return q;
	}

	@Override
	public Query<T> orderBy(Field<?>... fields) {
		return orderBy(ASCENDING, fields);
	}

	@Override
	public Query<T> distinct() {
		DBQuery<T> q = new DBQuery<T>(this);
		q.distinct  = true;
		return q;
	}

	@Override
	public Query<T> deferFields(Field<?>... fields) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.deferSet = new HashSet<Field<?>>();
		if (deferSet!=null) q.deferSet.addAll(deferSet);
		for (Field<?> field : fields) {
			q.deferSet.add(field);
		}
		return q;
	}

	@Override
	public Query<T> onlyFields(Field<?>... fields) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.onlySet = new HashSet<Field<?>>();
		//if (onlySet!=null) q.onlySet.addAll(onlySet);
		/*for (Field<?> f : Table.GET_TABLE_PK(q.tables.get(0)).GET_FIELDS()) {
			q.onlySet.add(f);
		} //*/
		for (Field<?> field : fields) {
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
	public T latest(Field<?> field) {
		for(T t : orderBy(DESCENDING, field).top(1)) {
			return t;
		}
		return null;
	}

	@Override
	public boolean isEmpty() throws SQLException {
		return this.count()==0;
	}

	@Override
	public int update() throws SQLException {
		SqlContext context = new SqlContext(this);
		if (data==null || data.size()==0) return 0;
		DataSource ds = getDataSource();
		Table table = tables.get(0);
		this.tableInfos.get(0).tableName = null;
		String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(Context.getSchemaToUse(ds, table.SCHEMA_NAME()) +sep+ table.TABLE_NAME());
		sb.append(" set ");
		String[] fields = new String[data.size()];
		List<Object> bindings = new ArrayList<Object>();
		int i=0;
		for (Entry<Field<?>, Object> entry : data.entrySet()) {
			fields[i++] = entry.getKey().NAME+"=?";
			bindings.add(entry.getValue());
		}
		sb.append(Util.join(", ", fields));
		sb.append(" ");
		sb.append(getWhereClauseAndSetBindings(false));
		bindings.addAll(this.bindings);
		String sql = sb.toString();

		Util.log(sql, bindings);
		Tuple2<Connection,Boolean> info = getConnRW(ds);
		Connection conn = info.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps, bindings);
		_preExecute(context, conn);
		ps.execute();
		int count = ps.getUpdateCount();
		ps.close();
		_postExecute(context, conn);
		if (info.b) {
			if (!conn.getAutoCommit()) conn.commit();
			conn.close();
		}

		return count;
	}

	@Override
	public int deleteAll() throws SQLException {
		DBQuery<T> q = new DBQuery<T>(this);
		DataSource ds = getDataSource();
		Tuple2<Connection,Boolean> info = q.getConnRW(ds);
		Connection conn = info.a;
		if (q.getDBType()==DB_TYPE.MYSQL) {
			if (q.tables.size() > 1) throw new RuntimeException("MYSQL multi-table delete " +
					"is not yet supported");
			Table t = q.tables.get(0);
			q.tableInfos.get(0).tableName = null;
			String sql = "delete from "+ Context.getSchemaToUse(ds, t.SCHEMA_NAME())
					+ "." + t.TABLE_NAME() + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;

		} else if (getDBType()==DB_TYPE.SQLSERVER) {
			if (q.tables.size() > 1) throw new RuntimeException("SQLSERVER multi-table delete " +
					"is not yet supported");
			Table t = q.tables.get(0);
			q.tableInfos.get(0).tableName = null;
			String sql = "delete from "+ Context.getSchemaToUse(ds, t.SCHEMA_NAME())
					+ ".dbo." + t.TABLE_NAME() + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;

		} else {
			String sql = "delete from "+ Util.join(", ", q.getTableNameList()) + q.getWhereClauseAndSetBindings();
			Util.log(sql, null);
			PreparedStatement ps = conn.prepareStatement(sql);
			q.setBindings(ps);
			ps.execute();
			int count = ps.getUpdateCount();
			ps.close();
			if (info.b) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		}
	}

	@Override
	public Statistics stats(Field<?>... field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<T> all() {
		return new Select<T>(this);
	}

	@Override
	public Iterable<T> none() {
		return Collections.emptyList();
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
	Tuple2<String,List<Object>> getWhereClauseAndBindings(SqlContext context) {
		StringBuffer sb = new StringBuffer();
		List<Object> bindings = new ArrayList<Object>();
		if (conditions!=null && conditions.size()>0) {
			sb.append(" where");
			String[] tmp = new String[conditions.size()];
			int i=0;
			for (Condition condition : conditions) {
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
	String getWhereClauseAndSetBindings(boolean bindTables) {
		if (sql==null) {

			StringBuffer sb = new StringBuffer();
			bindings = new ArrayList<Object>();

			tableNameMap = new HashMap<String,Set<String>>();
			for (TableInfo ti : tableInfos) {
				String id = ti.table.SCHEMA_NAME() +"."+ ti.table.TABLE_NAME();
				if (!tableNameMap.containsKey(id)) tableNameMap.put(id, new HashSet<String>());
				tableNameMap.get(id).add(bindTables ? ti.tableName : ti.table.TABLE_NAME());
			}

			SqlContext context = new SqlContext(this);

			if (conditions!=null && conditions.size()>0) {
				sb.append(" where");
				String[] tmp = new String[conditions.size()];
				int i=0;
				for (Condition condition : conditions) {
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

	Class<? extends Table> getType() {
		return tables.get(0).getClass();
	}

	/**
	 * @param context
	 * @return
	 */
	String getJoinClause(SqlContext context) {
		StringBuffer sb = new StringBuffer();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = getDBType();
		String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		ArrayList<Join> joinsToOne = new ArrayList<Join>(this.joinsToOne);
		ArrayList<Join> joinsToMany = new ArrayList<Join>(this.joinsToMany);
		Set<Class> seen = new HashSet<Class>();
		seen.add(getType());
		while (!joinsToOne.isEmpty() || !joinsToMany.isEmpty()) {
			System.err.println("woot1");
			for (int i=0; i<joinsToOne.size(); ++i) {
				Join join  = joinsToOne.get(i);
				if (!seen.contains(join.reffingTableInfo.tableClass)) continue;
				seen.add(join.reffedTableInfo.tableClass);
				joinsToOne.remove(i--);
				TableInfo tableInfo = join.reffedTableInfo;
				Table table = tableInfo.table;
				sb.append(" ");
				sb.append(join.type);
				sb.append(" ");
				sb.append(context.getFullTableName(table) +" "+ tableInfo.tableName);
				sb.append(" on ");
				sb.append(join.condition.getSQL(context));
			}
			for (int i=0; i<joinsToMany.size(); ++i) {
				Join join  = joinsToMany.get(i);
				if (!seen.contains(join.reffedTableInfo.tableClass)) continue;
				seen.add(join.reffingTableInfo.tableClass);
				joinsToMany.remove(i--);
				TableInfo tableInfo = join.reffingTableInfo;
				Table table = tableInfo.table;
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
	public Query<T> orderBy(DIRECTION direction, Field<?>... fields) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.orderByDirections = new ArrayList<DIRECTION>();
		if (orderByDirections!=null) q.orderByDirections.addAll(orderByDirections);
		q.orderByFields = new ArrayList<Field<?>>();
		if (orderByFields!=null) q.orderByFields.addAll(orderByFields);
		for (Field<?> field : fields) {
			q.orderByDirections.add(direction);
			q.orderByFields.add(field);
		}
		q.orderByDirections = Collections.unmodifiableList(q.orderByDirections);
		q.orderByFields = Collections.unmodifiableList(q.orderByFields);
		return q;
	}

	@Override
	public Query<T> top(int i) {
		return limit(i);
	}

	@Override
	public Query<T> limit(int i) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.top  = i;
		return q;
	}

	private String genTableName(Table table, Collection<String> tableNames) {
		String name = table.TABLE_NAME();
		String base = "";
		for (String s : name.split("_")) base += s.length() > 0 ? s.substring(0, 1) : "";
		String proposed = null;
		int i = 1;
		while (tableNames.contains((proposed = base + (i==1 ? "" : String.valueOf(i))))) ++i;
		return proposed;
	}

	List<TableInfo> getAllTableInfos() {
		List<TableInfo> all = new ArrayList<TableInfo>(tableInfos);
		for (Join join : joinsToOne) {
			all.add(join.reffedTableInfo);
		}
		for (Join join : joinsToMany) {
			all.add(join.reffingTableInfo);
		}
		return all;
	}

	Field<?>[] getSelectFields(boolean bind) {
		if (!bind && fields==null || bind && boundFields==null) {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			int c = 0;
			int position = 0;
			for (TableInfo ti : getAllTableInfos()) {
				ti.position = position;
				position += 1;
				ti.start = c;
				String tableName = bind ? ti.tableName : null;
				for (Field<?> field : ti.table.FIELDS()) {
					if(onlySet!=null) {
						for (Field<?> other : onlySet) {
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
					} else {
						if(deferSet==null || !deferSet.contains(field)) {
							fields.add(bind ? field.from(tableName) : field);
							++c;
						}
					}
				}
				ti.end = c;
			}
			if (bind) {
				this.boundFields = new Field[fields.size()];
				fields.toArray(this.boundFields);
			} else {
				this.fields = new Field[fields.size()];
				fields.toArray(this.fields);
			}
		}
		return bind ? boundFields.clone() : fields.clone();
	}

	List<DIRECTION> getOrderByDirections() {
		return orderByDirections;
	}

	List<Field<?>> getOrderByFields() {
		return orderByFields;
	}

	public void setBindings(PreparedStatement ps) throws SQLException {
		setBindings(ps, bindings);
	}

	public void setBindings(PreparedStatement ps, List<Object> bindings) throws SQLException {
		Table main = tables.get(0);
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
	public Query<T> set(Field<?> field, Object value) {
		DBQuery<T> q = new DBQuery<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.put(field, value);
		return q;
	}

	@Override
	public Query<T> set(Map<Field<?>, Object> values) {
		DBQuery<T> q = new DBQuery<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.putAll(values);
		return q;
	}

	@Override
	public Object insert() throws SQLException {
		DBQuery<T> q = new DBQuery<T>(this);
		SqlContext context = new SqlContext(q);
		DataSource ds = getDataSource();
		Table table = q.tables.get(0);
		String sep = getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(Context.getSchemaToUse(ds, table.SCHEMA_NAME()));
		sb.append(sep+ table.TABLE_NAME());
		sb.append(" (");
		String[] fields = new String[q.data.size()];
		String[] bindStrings = new String[q.data.size()];
		q.bindings = new ArrayList<Object>();
		int i=0;
		for (Entry<Field<?>, Object> entry : q.data.entrySet()) {
			fields[i] = entry.getKey().NAME;
			bindStrings[i] = "?";
			q.bindings.add(entry.getValue());
			++i;
		}
		sb.append(Util.join(", ", fields));
		sb.append(") values (");
		sb.append(Util.join(", ", bindStrings));
		sb.append(")");
		String sql = sb.toString();

		Util.log(sql, q.bindings);
		Tuple2<Connection,Boolean> info = getConnRW(ds);
		Connection conn = info.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		q.setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		int count = ps.getUpdateCount();
		ps.close();
		_postExecute(context, conn);

		if (count==1) {
			if (getDBType()==DB_TYPE.MYSQL) {
				Statement s = conn.createStatement();

				s.execute("SELECT LAST_INSERT_ID()");
				ResultSet rs = s.getResultSet();
				if (rs.next()) {
					Integer pk = rs.getInt(1);
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

	List<String> getTableNameList(SqlContext context) {
		DBQuery<?> rootQuery = context == null ? this : context.getRootQuery();
		DataSource ds = rootQuery.getDataSource();
		DB_TYPE dbType = context == null ? null : context.dbType;
		if (dbType == null) dbType = rootQuery.getDBType();
		List<String> names = new ArrayList<String>();
		String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		for (TableInfo ti : tableInfos) {
			Table t = ti.table;
			names.add(context.getFullTableName(t) +" "+ ti.tableName);
		}
		return names;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> with(final Field.FK... fkFields) {
		DBQuery<T> q = new DBQuery<T>(this);
		if (q.orderByFields == null) {
			Table t = q.tables.get(0);
			PK pk = Util.getPK(tables.get(0));
			if (pk != null) q = (DBQuery<T>) q.orderBy(pk.GET_FIELDS());
			else q = (DBQuery<T>) q.orderBy(t.FIELDS());
		}
		if (q.conditions==null) q.conditions = new ArrayList<Condition>();
		//if (q.fks==null) q.fks = new Tree<Field.FK>();
		try {

			final Table[] baseTables = new Table[fkFields.length+1];
			baseTables[0] = q.tables.get(0);
			for (int i=0; i<fkFields.length; ++i) {
				Field.FK field = fkFields[i];
				Field[] refingFields = field.REFERENCING_FIELDS();
				Field[] reffedFields = field.REFERENCED_FIELDS();
				Table refingTable = (Table) refingFields[0].TABLE.newInstance();
				Table reffedTable = (Table) reffedFields[0].TABLE.newInstance();
				FK[] prevPath = new FK[i];
				System.arraycopy(fkFields, 0, prevPath, 0, i);
				FK[] path = new FK[i+1];
				System.arraycopy(fkFields, 0, path, 0, i+1);
				boolean alreadyAdded = false;
				List<TableInfo> allTableInfos = q.getAllTableInfos();
				TableInfo prevTableInfo = allTableInfos.get(0);
				for (TableInfo ti : allTableInfos) {
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
					Condition condition2 = refingFields[j].eq(reffedFields[j]);
					if (condition == null) condition = condition2;
					else condition = condition.and(condition2);
				}

				if (Util.sameTable(refingTable, baseTables[i])) {
					baseTables[i+1] = reffedTable;
					String tableName = genTableName(reffedTable, q.usedTableNames);
					q.usedTableNames.add(tableName);
					TableInfo info = new TableInfo(reffedTable, tableName, path);
					info.nameAutogenned = true;
					Join join = new Join();
					join.condition = condition;
					join.reffingTableInfo  = prevTableInfo;
					join.reffedTableInfo = info;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToOne.add(join);
				} else if (Util.sameTable(reffedTable, baseTables[i])) {
					baseTables[i+1] = refingTable;
					String tableName = genTableName(refingTable, q.usedTableNames);
					q.usedTableNames.add(tableName);
					TableInfo info = new TableInfo(refingTable, tableName, path);
					info.nameAutogenned = true;
					Join join = new Join();
					join.condition = condition;
					join.reffingTableInfo = info;
					join.reffedTableInfo = prevTableInfo;
					info.join = join;
					join.type = "left outer join";
					join.fk  = field;
					q.joinsToMany.add(join);
				} else {
					throw new IllegalArgumentException("you have a break in your FK chain");
				}
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return q;
	}

	@SuppressWarnings("rawtypes")
	static String genTableNameFromFKPathKey(FK[] fkFields, int offset, Table refingTable) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<offset; ++i) {
			FK fkField = fkFields[i];
			sb.append(fkField.toString());
			sb.append("/");
		}
		sb.append(refingTable.SCHEMA_NAME());
		sb.append(".");
		sb.append(refingTable.TABLE_NAME());
		return sb.toString();
	}

	@Override
	public T first() {
		for(T t : this.top(1)) {
			return t;
		}
		return null;
	}

	@Override
	public T getTheOnly() {
		T x = null;
		for (T t : this.top(2)) {
			if (x==null) x = t;
			else throw new RuntimeException("more than one result found in Query.getTheOnly()");
		}
		return x;
	}

	@Override
	public int size() throws SQLException {
		return count();
	}

	static class Join implements Cloneable {
		public FK fk = null;
		public TableInfo reffingTableInfo = null;
		String type = null;
		TableInfo reffedTableInfo = null;
		Condition condition = null;
		@Override
		protected Object clone() throws CloneNotSupportedException {
			Join j = new Join();
			j.fk = fk;
			j.reffingTableInfo = reffingTableInfo;
			j.type = type;
			j.reffedTableInfo = reffedTableInfo;
			j.condition = condition;
			return j;
		}
	}

	@Override
	public List<T> asList() {
		List<T> list = new ArrayList<T>();
		for (T t : this) list.add(t);
		return list;
	}

	@Override
	public Set<T> asSet() {
		Set<T> set = new HashSet<T>();
		for (T t : this) set.add(t);
		return set;
	}

	String getFromClause(SqlContext context) {
		List<String> tableNames = getTableNameList(context);
		String firstTableName = tableNames.get(0);
		StringBuilder sb = new StringBuilder();
		sb.append(" from "+ firstTableName +" "+ getJoinClause(context));
		for (String tableName : tableNames.subList(1, tableNames.size())) {
			sb.append(", ");
			sb.append(tableName);
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> Map<S, Double> sumBy(Field<? extends Number> sumField, Field<S> byField)
			throws SQLException {
		SqlContext context = new SqlContext(this);
		String sql = getFromClause(context) + getWhereClauseAndSetBindings();
		sql = "select "+ Util.derefField(byField, context)
				+", sum("+ Util.derefField(sumField, context) +") "+ sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		Connection conn = connInfo.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		Map<Object, Double> result = new LinkedHashMap<Object, Double>();
		while (rs.next()) {
			Object key = null;
			if (byField.TYPE == Long.class) key = rs.getLong(1); else
			if (byField.TYPE == Double.class) key = rs.getDouble(1); else
			key = rs.getObject(1);
			Double value = rs.getDouble(2);
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
	public Double sum(Field<? extends Number> sumField) throws SQLException {
		SqlContext context = new SqlContext(this);
		String sql = getFromClause(context) + getWhereClauseAndSetBindings();
		sql = "select sum("+ Util.derefField(sumField, context) +")"+ sql;
		Util.log(sql, null);
		Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		Connection conn = connInfo.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		rs.next();
		Double ret = rs.getDouble(1);
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
	public <S> Map<S, Integer> countBy(Field<S> byField) throws SQLException {
		SqlContext context = new SqlContext(this);
		String sql = getFromClause(context)
				+ getWhereClauseAndSetBindings();
		sql = "select "+ Util.derefField(byField, context)
				+", count("+ Util.derefField(byField, context) +")"+ sql
				+" group by "+ Util.derefField(byField, context);
		Util.log(sql, null);
		Tuple2<Connection,Boolean> connInfo = getConnR(getDataSource());
		Connection conn = connInfo.a;
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		_preExecute(context, conn);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		Map<Object, Integer> result = new LinkedHashMap<Object, Integer>();
		while (rs.next()) {
			Object key = null;
			if (byField.TYPE == Long.class) key = rs.getLong(1); else
			if (byField.TYPE == Double.class) key = rs.getDouble(1); else
			key = rs.getObject(1);
			int value = rs.getInt(2);
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

	public Query<T> use(DataSource ds) {
		final DBQuery<T> q = new DBQuery<T>(this);
		q.ds = ds;
		return q;
	}

	@Override
	public Query<T> cross(Table t) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.addTable(t);
		for (Field<?> field : t.FIELDS()) {
			this.deferFields(field.from("i2"));
		}
		return q;
	}

	@Override
	public Query<T> cross(Class<? extends Table> tableClass) {
		DBQuery<T> q = new DBQuery<T>(this);
		try {
			Table table = tableClass.getConstructor().newInstance();
			q.addTable(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}

	@Override
	public Query<T> cross(__Alias<? extends Table> tableAlias) {
		DBQuery<T> q = new DBQuery<T>(this);
		try {
			Table table = tableAlias.table.getConstructor().newInstance();
			q.tables.add(table);
			if (q.usedTableNames.contains(tableAlias.alias)) {
				throw new RuntimeException("table alias "+ tableAlias.alias
						+" already exists in this query");
			}
			q.usedTableNames.add(tableAlias.alias);
			q.tableInfos.add(new TableInfo(table, tableAlias.alias, null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q;
	}

	@Override
	public <S> Map<S, T> mapBy(Field<S> byField) throws SQLException {
		Map<S, T> ret = new LinkedHashMap<S, T>();
		for (T t : this) {
			ret.put(t.get(byField), t);
		}
		return ret;
	}

	@Override
	public Query<T> toMemory() {
		return new InMemoryQuery<T>(this);
	}

	@Override
	public Query<T> max() {
		DBQuery<T> q = new DBQuery<T>(this);
		q.globallyAppliedSelectFunction = "max";
		return q;
	}

	@Override
	public Query<T> min() {
		DBQuery<T> q = new DBQuery<T>(this);
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
		DataSource ds = Context.getDataSource(type);
		if (ds != null) return ds;
		return getDefaultDS();
	}

	@Override
	public <S> Iterable<S> select(final Field<S> field) {
		return new SelectSingleColumn<S>(this, field);
	}

	@Override
	public <S> List<S> asList(Field<S> field) {
		List<S> ret = new ArrayList<S>();
		for (S s : select(field)) {
			ret.add(s);
		}
		return ret;
	}

	@Override
	public T get(__PrimaryKey<T> pk) {
		return get(Util.getPK((T)tables.get(0)).eq(pk));
	}

	@Override
	public Query<T> use(Connection conn) {
		return use(new SingleConnectionDataSource(conn));
	}

	void _preExecute(SqlContext context, Connection conn) throws SQLException {
		for (Table table : tables) {
			table.__NOSCO_PRIVATE_preExecute(context, conn);
		}
		if (conditions != null) {
			for (Condition c : conditions) {
				c._preExecute(conn);
			}
		}
	}

	void _postExecute(SqlContext context, Connection conn) throws SQLException {
		if (conditions != null) {
			for (Condition c : conditions) {
				c._postExecute(conn);
			}
		}
		for (Table table : tables) {
			table.__NOSCO_PRIVATE_postExecute(context, conn);
		}
	}

	@Override
	public Field<?>[] getSelectFields() {
		return this.getSelectFields(false);
	}

	@Override
	public Iterable<Object[]> asIterableOfObjectArrays() {
		return new SelectAsObjectArrayIterable(new Select<T>(this));
	}

	@Override
	public Query<T> use(DB_TYPE type) {
		DBQuery<T> q = new DBQuery<T>(this);
		q.dbType = type;
		return q;
	}

}
