package org.nosco;

import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.util.Misc;
import org.nosco.util.Tree.Callback;



class Select<T extends Table> implements Iterable<T>, Iterator<T> {

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		rs.close();
		ps.close();
	}

	private String sql;
	private QueryImpl<T> query;
	private PreparedStatement ps;
	private ResultSet rs;
	private T next;
	private Field<?>[] selectedFields;
	private Field<?>[] selectedBoundFields;
	private Object[] fieldValues;
	private Constructor<T> constructor;
	private Map<Field.FK,Constructor<T>> fkConstructors;
	private Map<Class<? extends Table>,Method> fkSetMethods = null;
	private Connection conn;

	@SuppressWarnings("unchecked")
	Select(QueryImpl<T> query) {
		this.query = query;
		try {
			constructor = (Constructor<T>) query.getType().getDeclaredConstructor(
					new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
			constructor.setAccessible(true);
			if (query.fks != null) {
				fkConstructors = new HashMap<Field.FK,Constructor<T>>();
				fkSetMethods = new HashMap<Class<? extends Table>,Method>();
				query.fks.visit(new Callback<Field.FK>() {
					public void call(FK fk, int depth, FK[] path) {
						if (fk==null && depth==0) return;
						try {
							Constructor<T> c = (Constructor<T>) fk.REFERENCED_FIELDS()[0].TABLE.getDeclaredConstructor(
									new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
							c.setAccessible(true);
							fkConstructors.put(fk, c);
							Class<? extends Table> reffedTable = fk.REFERENCED_FIELDS()[0].TABLE;
							Method setFKMethod  = (Method) reffedTable.getDeclaredMethod("SET_FK",
									Field.FK.class, Object.class);
							setFKMethod.setAccessible(true);
							fkSetMethods.put(reffedTable, setFKMethod);
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
					}
				});
				Method setFKMethod  = (Method) query.getType().getDeclaredMethod("SET_FK",
						Field.FK.class, Object.class);
				setFKMethod.setAccessible(true);
				fkSetMethods.put(query.getType(), setFKMethod);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	protected String getSQL() {
		return getSQL(false);
	}

	protected String getSQL(boolean innerQuery) {
		if (sql==null) {

			selectedFields = query.getSelectFields(false);
			selectedBoundFields = query.getSelectFields(true);
			fieldValues = new Object[selectedFields.length];
			StringBuffer sb = new StringBuffer();
			sb.append("select ");
			if (query.distinct) sb.append("distinct ");
			if (query.getDBType()==DB_TYPE.SQLSERVER && query.top>0) {
				sb.append(" top ").append(query.top).append(" ");
			}
			if (query.globallyAppliedSelectFunction == null) {
				sb.append(Misc.join(", ", selectedBoundFields));
			} else {
				String[] x = new String[selectedBoundFields.length];
				for (int i=0; i < x.length; ++i) {
					x[i] = query.globallyAppliedSelectFunction + "("+ selectedBoundFields[i] +")";
				}
				sb.append(Misc.join(", ", x));
			}
			sb.append(" from ");
			sb.append(Misc.join(", ", query.getTableNameList()));
			sb.append(query.getWhereClauseAndSetBindings());

			List<DIRECTION> directions = query.getOrderByDirections();
			List<Field<?>> fields = query.getOrderByFields();
			if (!innerQuery && directions!=null & fields!=null) {
				sb.append(" order by ");
				int x = Math.min(directions.size(), fields.size());
				String[] tmp = new String[x];
				for (int i=0; i<x; ++i) {
					DIRECTION direction = directions.get(i);
					tmp[i] = fields.get(i) + (direction==DESCENDING ? " DESC" : "");
				}
				sb.append(Misc.join(", ", tmp));
			}

			if (query.getDBType()!=DB_TYPE.SQLSERVER && query.top>0) {
				sb.append(" limit ").append(query.top);
			}

			if (innerQuery && selectedFields.length > 1) {
				Misc.log(sb.toString(), null);
				throw new RuntimeException("inner queries cannot have more than one selected" +
						"field - this query has "+ selectedFields.length);
			}

			sql = sb.toString();
		}
		return sql;
	}

	protected List<Object> getSQLBindings() {
		return query.getSQLBindings();
	}

	@Override
	public Iterator<T> iterator() {
		try {
			conn = query.getConnR();
			ps = conn.prepareStatement(getSQL());
			Misc.log(sql, query.bindings);
			query.setBindings(ps);
			ps.execute();
			rs = ps.getResultSet();
			//m = query.getType().getMethod("INSTANTIATE", Map.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return this;
	}

	@Override
	public boolean hasNext() {
		if (next!=null) return true;
		try {
			if (!rs.next()) {
				cleanUp();
				return false;
			}
			for (int i=0; i<selectedFields.length; ++i) {
				if (selectedFields[i].TYPE == Long.class) fieldValues[i] = rs.getLong(i+1);
				else if (selectedFields[i].TYPE == Double.class) {
					fieldValues[i] = rs.getDouble(i+1);
					if (rs.wasNull()) fieldValues[i] = null;
				}
				else if (selectedFields[i].TYPE == Character.class) {
					String s = rs.getString(i+1);
					if (s != null && s.length() > 0) fieldValues[i] = s.charAt(0);
				}
				else fieldValues[i] = rs.getObject(i+1);
			}
			Object[] objects = new Object[query.tableInfos.size()];
			for (int i=0; i<query.tableInfos.size(); ++i) {
				QueryImpl.TableInfo ti = query.tableInfos.get(i);
				if (ti.path == null) {
					if (next != null) continue;
					//System.out.println(ti.start +" "+ ti.end);
					next = (T) constructor.newInstance(selectedFields, fieldValues, ti.start, ti.end);
					next.__NOSCO_GOT_FROM_DATABASE= true;
					objects[i] = next;
				} else {
					FK fk = ti.path[ti.path.length-1];
					Table fkv = fkConstructors.get(fk).newInstance(selectedFields, fieldValues, ti.start, ti.end);
					objects[i] = fkv;
				}
			}
			for (int i=0; i<query.tableInfos.size(); ++i) {
				QueryImpl.TableInfo ti = query.tableInfos.get(i);
				for (int j=i+1; j<query.tableInfos.size(); ++j) {
					QueryImpl.TableInfo tj = query.tableInfos.get(j);
					if (tj.path == null) continue;
					if(startsWith(tj.path, ti.path)) {
						FK fk = tj.path[tj.path.length-1];
						if (!fk.referencing.equals(objects[i].getClass())) continue;
						if (!fk.referenced.equals(objects[j].getClass())) continue;
						fkSetMethods.get(objects[i].getClass()).invoke(objects[i], fk, objects[j]);
					}
				}
			}




			/*
			if (fkConstructors != null) {
				Set<Table> createdObjects = new HashSet<Table>();
				for (Entry<Field.FK, Constructor<T>> e : fkConstructors.entrySet()) {
					FK fk = e.getKey();
					Table fkv = e.getValue().newInstance(selectedFields, fieldValues);
					Table refTable = (Table)fk.REFERENCING_FIELDS()[0].TABLE.newInstance();
					if (next.sameTable(refTable)) {
						fkSetMethods.get(next.getClass()).invoke(next, fk, fkv);
						createdObjects.add(fkv);
					} else {
						for (Table t : createdObjects) {
							if (t.sameTable(refTable)) {
								fkSetMethods.get(t.getClass()).invoke(t, fk, fkv);
								createdObjects.add(fkv);
							}
						}
					}
				}
			}//*/
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		boolean hasNext = next != null;
		if (!hasNext) cleanUp();
		return hasNext;
	}

	private void cleanUp() {
		if (!ThreadContext.inTransaction(query.ds)) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private boolean startsWith(FK[] path, FK[] path2) {
		if (path2 == null) return true;
		for (int i=0; i<path2.length; ++i) {
			if (path[i] != path2[i]) return false;
		}
		return true;
	}

	@Override
	public T next() {
		T t = next;
		next = null;
		return t;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
