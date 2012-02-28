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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.Table.TableAlias;
import org.nosco.util.Misc;
import org.nosco.util.Tree;


class QueryImpl<T extends Table> implements Query<T> {

	// genned once and cached
	private String sql;
	private Field<?>[] fields;
	private Field<?>[] boundFields;
	List<Object> bindings = null;
	private Map<String,Set<String>> tableNameMap = null;

	// these should be cloned
	List<Condition> conditions = null;
	List<Table> tables = new ArrayList<Table>();
	List<String> tableNames = new ArrayList<String>();
	List<TableInfo> tableInfos = new ArrayList<TableInfo>();
	private Set<Field<?>> deferSet = null;
	private Set<Field<?>> onlySet = null;
	Tree<Field.FK> fks = null;
	Map<String,String> fkPathTableToTableNameMap = null;
	private List<DIRECTION> orderByDirections = null;
	private List<Field<?>> orderByFields = null;
	int top = 0;
	private Map<Field<?>,Object> data = null;
	private boolean distinct = false;
	DataSource ds = null;

	QueryImpl(Table table) {
		addTable(table);
	}

	private void addTable(Table table) {
		tables.add(table);
		String tableName = genTableName(table, tableNames);
		tableNames.add(tableName);
		TableInfo info = new TableInfo(table, tableName, null);
		info.nameAutogenned = true;
		tableInfos.add(info);
	}

	QueryImpl(QueryImpl<T> q) {
		if (q.conditions!=null) {
			conditions = new ArrayList<Condition>();
			conditions.addAll(q.conditions);
		}
		if (q.fks!=null) {
			fks = q.fks.clone();
		}
		if (q.fkPathTableToTableNameMap!=null) {
			fkPathTableToTableNameMap = new HashMap<String,String>(q.fkPathTableToTableNameMap);
		}
		tables.addAll(q.tables);
		tableNames.addAll(q.tableNames);
		tableInfos.addAll(q.tableInfos);
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
	}

	QueryImpl(Class<? extends Table> tableClass) {
		try {
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

	@Override
	public Iterator<T> iterator() {
		return new Select<T>(this).iterator();
	}

	@Override
	public Query<T> where(Condition... conditions) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		q.conditions = new ArrayList<Condition>();
		if (this.conditions!=null) q.conditions.addAll(this.conditions);
		for (Condition c : conditions) {
			q.conditions.add(c);
		}
		return q;
	}

	DB_TYPE getDBType() {
		//return DB_TYPE.MYSQL;
		return DB_TYPE.SQLSERVER;
		//return ConnectionManager.instance().getDBType(tables.get(0).SCHEMA_NAME());
	}

	Connection getConnR() throws SQLException {
		if (TransactionThread.inTransaction(ds)) {
			return TransactionThread.getConnection(ds);
		}
		if (ds.isWrapperFor(MirroredDataSource.class)) {
			return ds.unwrap(MirroredDataSource.class).getMirroredConnection();
		}
		return ds.getConnection();
	}

	Connection getConnRW() throws SQLException {
		if (TransactionThread.inTransaction(ds)) {
			return TransactionThread.getConnection(ds);
		}
		return ds.getConnection();
	}

	@Override
	public int count() throws SQLException {
		String sql = "select count(1) from "+ Misc.join(", ", getTableNameList()) + getWhereClauseAndSetBindings();
		log(sql);
		Connection conn = getConnR();
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		rs.next();
		int count = rs.getInt(1);
		rs.close();
		ps.close();
		if (!TransactionThread.inTransaction(ds)) {
			conn.close();
		}
		return count;
	}

	@Override
	public T get(Condition... conditions) {
		//Field[] fields = tables.get(0).FIELDS();
		QueryImpl<T> q = new QueryImpl<T>(this);
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
		QueryImpl<T> q = new QueryImpl<T>(this);
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
		QueryImpl<T> q = new QueryImpl<T>(this);
		q.distinct  = true;
		return q;
	}

	@Override
	public Query<T> deferFields(Field<?>... fields) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		q.deferSet = new HashSet<Field<?>>();
		if (deferSet!=null) q.deferSet.addAll(deferSet);
		for (Field field : fields) {
			q.deferSet.add(field);
		}
		return q;
	}

	@Override
	public Query<T> onlyFields(Field<?>... fields) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		q.onlySet = new HashSet<Field<?>>();
		//if (onlySet!=null) q.onlySet.addAll(onlySet);
		/*for (Field<?> f : Table.GET_TABLE_PK(q.tables.get(0)).GET_FIELDS()) {
			q.onlySet.add(f);
		} //*/
		for (Field<?> field : fields) {
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
		if (data==null || data.size()==0) return 0;
		Table table = tables.get(0);
		String sep = getDBType()==DB_TYPE.SQLSERVER ? ".." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(table.SCHEMA_NAME() +sep+ table.TABLE_NAME());
		sb.append(" set ");
		String[] fields = new String[data.size()];
		List<Object> bindings = new ArrayList<Object>();
		int i=0;
		for (Entry<Field<?>, Object> entry : data.entrySet()) {
			fields[i++] = entry.getKey().toString()+"=?";
			bindings.add(entry.getValue());
		}
		sb.append(Misc.join(", ", fields));
		sb.append(" ");
		sb.append(getWhereClauseAndSetBindings(false));
		bindings.addAll(this.bindings);
		String sql = sb.toString();

		Misc.log(sql, bindings);
		Connection conn = getConnRW();
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps, bindings);
		ps.execute();
		int count = ps.getUpdateCount();
		ps.close();
		if (!TransactionThread.inTransaction(ds)) {
			if (!conn.getAutoCommit()) conn.commit();
			conn.close();
		}

		return count;
	}

	@Override
	public int deleteAll() throws SQLException {
		Connection conn = getConnRW();
		if (getDBType()==DB_TYPE.MYSQL) {
			if (this.tables.size() > 1) throw new RuntimeException("MYSQL multi-table delete " +
					"is not yet supported");
			Table t = tables.get(0);
			String sql = "delete from "+ t.SCHEMA_NAME() + "." + t.TABLE_NAME() + getWhereClauseAndSetBindings();
			log(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			setBindings(ps);
			ps.execute();
			int count = ps.getUpdateCount();
			ps.close();
			if (!TransactionThread.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;

		} else {
			String sql = "delete from "+ Misc.join(", ", getTableNameList()) + getWhereClauseAndSetBindings();
			log(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			setBindings(ps);
			ps.execute();
			int count = ps.getUpdateCount();
			ps.close();
			if (!TransactionThread.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		}
	}

	void log(String sql) {
		System.err.println("==> "+ sql +"");
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

			if (conditions!=null && conditions.size()>0) {
				sb.append(" where");
				String[] tmp = new String[conditions.size()];
				int i=0;
				for (Condition condition : conditions) {
					tmp[i++] = condition.getSQL(tableNameMap, tableInfos);
					bindings.addAll(condition.getSQLBindings());
				}
				sb.append(Misc.join(" and", tmp));
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

	@Override
	public Query<T> orderBy(DIRECTION direction, Field<?>... fields) {
		QueryImpl<T> q = new QueryImpl<T>(this);
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
		QueryImpl<T> q = new QueryImpl<T>(this);
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

	Field<?>[] getSelectFields(boolean bind) {
		if (!bind && fields==null || bind && boundFields==null) {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			int c = 0;
			for (TableInfo ti : tableInfos) {
				ti.start = c;
				String tableName = bind ? ti.tableName : null;
				for (Field<?> field : ti.table.FIELDS()) {
					if(onlySet!=null) {
						if(onlySet.contains(field) && (deferSet==null || !deferSet.contains(field))) {
							fields.add(bind ? field.from(tableName) : field);
							++c;
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
		int i=1;
		if (bindings!=null) {
			for (Object o : bindings) {
				ps.setObject(i++, o);
				//System.err.print("\t"+ o +"");
			}
		}
	}

	@Override
	public Query<T> set(Field<?> field, Object value) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.put(field, value);
		return q;
	}

	@Override
	public Query<T> set(Map<Field<?>, Object> values) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		if (q.data==null) q.data = new HashMap<Field<?>, Object>();
		q.data.putAll(values);
		return q;
	}

	@Override
	public Object insert() throws SQLException {
		QueryImpl<T> q = new QueryImpl<T>(this);
		Table table = q.tables.get(0);
		String sep = getDBType()==DB_TYPE.SQLSERVER ? ".." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(table.SCHEMA_NAME() +sep+ table.TABLE_NAME());
		sb.append(" (");
		String[] fields = new String[q.data.size()];
		String[] bindStrings = new String[q.data.size()];
		q.bindings = new ArrayList<Object>();
		int i=0;
		for (Entry<Field<?>, Object> entry : q.data.entrySet()) {
			fields[i] = entry.getKey().toString();
			bindStrings[i] = "?";
			q.bindings.add(entry.getValue());
			++i;
		}
		sb.append(Misc.join(", ", fields));
		sb.append(") values (");
		sb.append(Misc.join(", ", bindStrings));
		sb.append(")");
		String sql = sb.toString();

		Misc.log(sql, q.bindings);
		Connection conn = getConnRW();
		PreparedStatement ps = conn.prepareStatement(sql);
		q.setBindings(ps);
		ps.execute();
		int count = ps.getUpdateCount();
		ps.close();

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
		if (!TransactionThread.inTransaction(ds)) {
			if (!conn.getAutoCommit()) conn.commit();
			conn.close();
		}

		return null;
	}

	public Collection<String> getTableNameList() {
		List<String> names = new ArrayList<String>();
		List<String> tableNames = new LinkedList<String>(this.tableNames);
		String sep = getDBType()==DB_TYPE.SQLSERVER ? ".." : ".";
		for (Table t : tables) {
			names.add(t.SCHEMA_NAME() + sep + t.TABLE_NAME() +" "+ tableNames.remove(0));
		}
		return names;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> with(final Field.FK... fkFields) {
		final QueryImpl<T> q = new QueryImpl<T>(this);
		if (q.conditions==null) q.conditions = new ArrayList<Condition>();
		if (q.fks==null) q.fks = new Tree<Field.FK>();
		try {

			final Table[] baseTables = new Table[fkFields.length+1];
			baseTables[0] = q.tables.get(0);
			for (int i=0; i<fkFields.length; ++i) {
				Field.FK field = fkFields[i];
				Field[] refingFields = field.REFERENCING_FIELDS();
				Field[] reffedFields = field.REFERENCED_FIELDS();
				Table refingTable = (Table) refingFields[0].TABLE.newInstance();
				Table reffedTable = (Table) reffedFields[0].TABLE.newInstance();
				if (refingTable.sameTable(baseTables[i])) {
					baseTables[i+1] = reffedTable;
				} else if (reffedTable.sameTable(baseTables[i])) {
					throw new IllegalArgumentException("reverse rels not supported yet");
				} else {
					throw new IllegalArgumentException("you have a break in your FK chain");
				}
			}

			q.fks.add(new Tree.Callback<Field.FK>() {
				public void call(FK fkField, int offset, FK[] path) {
					try {
						Field[] refingFields = fkField.REFERENCING_FIELDS();
						Field[] reffedFields = fkField.REFERENCED_FIELDS();
						Table refingTable = (Table) refingFields[0].TABLE.newInstance();
						Table reffedTable = (Table) reffedFields[0].TABLE.newInstance();
						if (refingTable.sameTable(baseTables[offset-1])) {
							String reffedTableName = genTableName(reffedTable, q.tableNames);
							q.tables.add(reffedTable);
							q.tableNames.add(reffedTableName);
							FK[] actualPath = new FK[offset];
							System.arraycopy(path, 0, actualPath, 0, offset);
							q.tableInfos.add(new TableInfo(reffedTable, reffedTableName, actualPath));
							if (q.fkPathTableToTableNameMap == null) q.fkPathTableToTableNameMap = new HashMap<String,String>();
							q.fkPathTableToTableNameMap.put(
									genTableNameFromFKPathKey(fkFields, offset, reffedTable),
									reffedTableName);
							String refingTableName =q.fkPathTableToTableNameMap.get(
									genTableNameFromFKPathKey(fkFields, offset, refingTable));
							for (int j=0; j<refingFields.length; ++j) {
								q.conditions.add(new Condition.Binary(refingFields[j].from(
										refingTableName),
										"=",
										reffedFields[j].from(reffedTableName))
									.or(refingFields[j].from(refingTableName).isNull()));
							}
						} else if (reffedTable.sameTable(baseTables[offset-1])) {
							throw new IllegalArgumentException("reverse rels not supported yet");
						} else {
							throw new IllegalArgumentException("you have a break in your FK chain");
						}
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}, fkFields);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return q;
	}

	private static String genTableNameFromFKPathKey(FK[] fkFields, int offset, Table refingTable) {
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

	static class TableInfo implements Cloneable{

		Table table = null;
		String tableName = null;
		int start = -1;
		int end = -1;
		FK[] path = null;
		boolean nameAutogenned = false;

		public TableInfo(Table table, String tableName, FK[] path) {
			this.table = table;
			this.tableName = tableName;
			this.path  = path;
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

	@SuppressWarnings("unchecked")
	@Override
	public <S> Map<S, Double> sumBy(Field<? extends Number> sumField, Field<S> byField)
			throws SQLException {
		String sql = Misc.join(", ", getTableNameList()) + getWhereClauseAndSetBindings();
		sql = "select "+ Condition.derefField(byField, tableNameMap, tableInfos)
				+", sum("+ Condition.derefField(sumField, tableNameMap, tableInfos) +") from "+ sql
				+" group by "+ Condition.derefField(byField, tableNameMap, tableInfos);
		log(sql);
		Connection conn = getConnR();
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
		if (!TransactionThread.inTransaction(ds)) {
			conn.close();
		}
		return (Map<S, Double>) result;
	}

	@Override
	public Double sum(Field<? extends Number> sumField) throws SQLException {
		String sql = Misc.join(", ", getTableNameList()) + getWhereClauseAndSetBindings();
		sql = "select sum("+ Condition.derefField(sumField, tableNameMap, tableInfos) +") from "+ sql;
		log(sql);
		Connection conn = getConnR();
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
		ps.execute();
		ResultSet rs = ps.getResultSet();
		rs.next();
		Double ret = rs.getDouble(1);
		rs.close();
		ps.close();
		if (!TransactionThread.inTransaction(ds)) {
			conn.close();
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> Map<S, Integer> countBy(Field<S> byField) throws SQLException {
		String sql = Misc.join(", ", getTableNameList()) + getWhereClauseAndSetBindings();
		sql = "select "+ Condition.derefField(byField, tableNameMap, tableInfos)
				+", count("+ Condition.derefField(byField, tableNameMap, tableInfos) +") from "+ sql
				+" group by "+ Condition.derefField(byField, tableNameMap, tableInfos);
		log(sql);
		Connection conn = getConnR();
		PreparedStatement ps = conn.prepareStatement(sql);
		setBindings(ps);
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
		if (!TransactionThread.inTransaction(ds)) {
			conn.close();
		}
		return (Map<S, Integer>) result;
	}

	public Query<T> use(DataSource ds) {
		final QueryImpl<T> q = new QueryImpl<T>(this);
		q.ds  = ds;
		return q;
	}

	@Override
	public Query<T> cross(Table t) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		q.addTable(t);
		for (Field<?> field : t.FIELDS()) {
			this.deferFields(field.from("i2"));
		}
		return q;
	}

	@Override
	public Query<T> cross(Class<? extends Table> tableClass) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		try {
			Table table = tableClass.getConstructor().newInstance();
			q.addTable(table);
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
		return q;
	}

	@Override
	public Query<T> cross(TableAlias tableAlias) {
		QueryImpl<T> q = new QueryImpl<T>(this);
		try {
			Table table = tableAlias.table.getConstructor().newInstance();
			q.tables.add(table);
			if (q.tableNames.contains(tableAlias.alias)) {
				throw new RuntimeException("table alias "+ tableAlias.alias
						+" already exists in this query");
			}
			q.tableNames.add(tableAlias.alias);
			q.tableInfos.add(new TableInfo(table, tableAlias.alias, null));
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
		return q;
	}

	/*	@Override
	public <S> Map<S, T> mapBy(Field<S> byField) throws SQLException {
		Map<S, T> ret = new LinkedHashMap<S, T>();
		for (T t : this) {
			//t.
		}
		return null;
	} //*/

}
