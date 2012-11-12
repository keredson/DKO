package org.kered.dko;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Table.__Alias;



/**
 * The Query interface is the center of the Nosco API. &nbsp; When you generate your JAR file
 * (with {@code org.kered.dko.ant.CodeGenerator}) each object gets its own static {@code Query} instance. &nbsp;
 * For example, if your database
 * table looked like this:
 * <table border="1" cellpadding="4" style="margin-left: 2em;">
 * <tr><th colspan="2">some_class</th></tr>
 * <tr><th>id</th><th>name</th></tr>
 * <tr><td>123</td><td>my name</td></tr>
 * <tr><td>456</td><td>your name</td></tr>
 * </table>
 * <p>
 * The {@code CodeGenerator} would create the following for you (simplified):
 * <pre>  {@code public class SomeClass extends Table {
 *     final static Query<SomeClass> ALL = new Query<SomeClass>();
 *     final static Field<Integer> ID = new Field<Integer>();
 *     final static Field<String> NAME = new Field<String>();
 *  }}</pre>
 * The constant {@code SomeClass.ALL} will generally be your starting point for all queries
 * expected to return {@code SomeClass} objects. &nbsp;
 * For example, if you wanted to iterate over all of them you could do this:
 * <pre>  {@code for (SomeClass x : SomeClass.ALL)
 *     System.out.println(x);}</pre>
 * If you only want a specific one (assuming "id" was the primary key for the table) you could do this:
 * <pre>  {@code SomeClass x = SomeClass.ALL.get(SomeClass.ID.eq(123)))}</pre>
 * {@code SomeClass.ID} is a {@code Field<Integer>}.  All {@code Field<R>} objects contain a {@code eq(<R> x)}
 * which returns a {@code Condition} object.  (here passed into {@code Query.get(Condition... c)}) &nbsp; Similarly:
 * <pre>  {@code for (SomeClass x : SomeClass.ALL.where(SomeClass.NAME.like("%me%")))
 *     System.out.println(x);}</pre>
 * would print out all rows named like {@code "me"}.
 * @author Derek Anderson
 * @param <T> the type of object this will return
 */
public interface Query<T extends Table> extends Iterable<T> {

	/**
	 * Adds conditions to the query.  Usually conditions are created off the fields of tables.
	 * Example: SomeClass.SOME_FIELD.eq("abc") would return a Condition.
	 * So for a full query: SomeClass.ALL.where(SomeClass.SOME_FIELD.eq("abc"))
	 * Multiple conditions are ANDed together.
	 * @param conditions
	 * @return
	 */
	public Query<T> where(Condition... conditions);

	/**
	 * Returns the only element that matches the conditions.  If more than one match, throws a RuntimeException.
	 * Equivalent to .where(conditions).getTheOnly()
	 * @param conditions
	 * @return
	 */
	public T get(Condition... conditions);

	/**
	 * Returns the only element that matches the conditions.  If more than one match, throws a RuntimeException.
	 * Equivalent to .where(conditions).getTheOnly()
	 * @param conditions
	 * @return
	 */
	public T get(Table.__PrimaryKey<T> pk);

	/**
	 * Returns the database-calculated count of the query.
	 * Does not download the objects into the JVM.
	 * Much faster than .asList().size() or counting the objects yourself.
	 * WARNING: Joins can inflate this number and cause a apparent discrepancy with .asList().size().
	 * For example, if table A has a FK to table B and you do a query like {@code B.ALL.with(A.B_FK)}
	 * the size counted by the database likely will be different from the size counted by you counting
	 * objects.  This is because the database does not merge objects into sets for you like DKOs do.
	 * @return
	 * @throws SQLException
	 */
	public long count() throws SQLException;

	/**
	 * Same as .count()
	 * @return
	 * @throws SQLException
	 */
	public long size() throws SQLException;

	/**
	 * Excludes elements that match the conditions.
	 * Equivalent to: .where(condition.not())
	 * @param conditions
	 * @return
	 */
	public Query<T> exclude(Condition... conditions);

	/**
	 * Sets the ordering of the database query.
	 * For descending order, see: .orderBy(DIRECTION, fields)
	 * @param fields
	 * @return
	 */
	public Query<T> orderBy(Field<?>... fields);

	/**
	 * Returns the first n rows of the query.
	 * Same as .limit(n)
	 * Note: If any to-many joins are in this query, this is computed on the java side.
	 * (with the result set terminated mid-stream)
	 * @param n
	 * @return
	 */
	public Query<T> top(long n);

	/**
	 * Returns the first n rows of the query.
	 * Same as .top(n)
	 * Note: If any to-many joins are in this query, this is computed on the java side.
	 * (with the result set terminated mid-stream)
	 * @param n
	 * @return
	 */
	public Query<T> limit(long n);

	/**
	 * Sets the distinct keyword in the select statement.
	 * @return
	 */
	public Query<T> distinct();

	/**
	 * Sets the max function on the selected columns in this statement.
	 * @return
	 */
	public Query<T> max();

	/**
	 * Sets the min function on the selected columns in this statement.
	 * @return
	 */
	public Query<T> min();

	/**
	 * Joins on foreign keys.  FKed objects are populated under the .getFK() style methods.
	 * Avoids O(n) SQL calls when accessing FKs inside a loop.
	 * @param fields
	 * @return
	 */
	public Query<T> with(Field.FK... fields);

	/**
	 * Don't include the following fields in the select statement.
	 * Note: The returned object will still contain a .getField() method.  If it is called another
	 * SQL call will be made to fetch this value.  (assuming the PK was not also excluded with this call)
	 * @param fields
	 * @return
	 */
	public Query<T> deferFields(Field<?>... fields);

	/**
	 * Don't include the following fields in the select statement.
	 * Note: The returned object will still contain a .getField() method.  If it is called another
	 * SQL call will be made to fetch this value.  (assuming the PK was not also excluded with this call)
	 * @param fields
	 * @return
	 */
	public Query<T> deferFields(Collection<Field<?>> fields);

	/**
	 * Only include the following fields in the select statement.
	 * Note: The returned object will still contain all .getField() methods.  If any are called that were not in this list, another
	 * SQL call will be made to fetch each value.  (assuming the PK was included with this call)
	 * @param fields
	 * @return
	 */
	public Query<T> onlyFields(Collection<Field<?>> fields);

	/**
	 * Only include the following fields in the select statement.
	 * Note: The returned object will still contain all .getField() methods.  If any are called that were not in this list, another
	 * SQL call will be made to fetch each value.  (assuming the PK was included with this call)
	 * @param fields
	 * @return
	 */
	public Query<T> onlyFields(Field<?>... fields);

	/**
	 * Returns the last value that would be returned by the query.
	 * Same as: .orderBy(DESCENDING, field).top(1).getTheOnly()
	 * @param field
	 * @return
	 */
	public T latest(Field<?> field);

	/**
	 * Gets the first item in the query.
	 * Same as: .top(1).getTheOnly()
	 * Note: If any to-many joins are in this query, this is computed on the java side.
	 * (with the result set terminated mid-stream)
	 * @return
	 */
	public T first();

	/**
	 * Returns whether the SQL returned any rows or not.
	 * Same as: .count() == 0
	 * @return
	 * @throws SQLException
	 */
	public boolean isEmpty() throws SQLException;

	/**
	 * Executes and update statement populated w/ data from .where() and .set().
	 * Example:  SomeClass.ALL.set(SomeClass.SOME_FIELD, "xyz")
	 *                        .where(SomeClass.SOME_FIELD.eq("abc"))
	 *                        .update();
	 * @return
	 * @throws SQLException
	 */
	public int update() throws SQLException;

	/**
	 * Deletes all rows matching data set with: .where()
	 * Example:  SomeClass.ALL.where(SomeClass.SOME_FIELD.eq("abc")).delete()
	 * Use with caution!
	 * @return
	 * @throws SQLException
	 */
	public int delete() throws SQLException;

	/**
	 * @deprecated use delete()
	 */
	public int deleteAll() throws SQLException;

//	/**
//	 * Not implemented yet.
//	 * @param field
//	 * @return
//	 */
//	public Statistics stats(Field<?>... field);

	/**
	 * Returns an Iterable for this query.  Not usually necessary as the Query itself is Iterable, but useful if you want to
	 * prevent further filtering or updating for some reason.
	 * @return
	 */
	public Iterable<T> all();

	/**
	 * Returns an always-empty Iterable.  I'm not sure this has any practical use,
	 * but it seemed to be a good corollary to: .all()
	 * @return
	 */
	public Iterable<T> none();

	/**
	 * Same as .orderBy(fields), but allows you to specify the direction.
	 * Note: The direction is applied to all the fields.  to specify different
	 * directions to different fields, chain the calls like this:
	 * SomeClass.ALL.orderBy(DESCENDING, SomeClass.SOME_FIELD).orderBy(ASCENDING, SomeClass.SOME_OTHER_FIELD)
	 * @param direction
	 * @param fields
	 * @return
	 */
	public Query<T> orderBy(DIRECTION direction, Field<?>... fields);

	/**
	 * Sets the field to the given value.  Chainable.  Does not execute any SQL until .update() is called.
	 * @param key
	 * @param value
	 * @return
	 */
	public Query<T> set(Field<?> key, Object value);

	/**
	 * Same as calling .set(key, value) for each entry in the Map.
	 * @param values
	 * @return
	 */
	public Query<T> set(Map<Field<?>,Object> values);

	/**
	 * Inserts the values set by .set(key,value).
	 * Note: May be easier to create the object with new SomeClass(),
	 * then call its setter methods and then .insert() in it.
	 * @return
	 * @throws SQLException
	 */
	public Object insert() throws SQLException;

	/**
	 * Returns the only object returned by this query.  If multiple rows are returned,
	 * throws a RuntimeException.
	 * @return
	 */
	public T getTheOnly();

	/**
	 * Runs the query, populating a list with all the values returned.
	 * Useful if you need non-linear access to your objects, but take note all
	 * objects must be able to fit into memory at the same time.
	 * @return
	 */
	public List<T> asList();

	/**
	 * Runs the query, populating a list of the values only in the given field. &nbsp;
	 * If you want an list of more than one field type, you should use
	 * {@code onlyFields(field1, field2, ...).asList()}. &nbsp; This will return you an
	 * list of T instances, with only those fields populated. &nbsp;
	 * If Java adds typed tuple support at some point this method may be extended.
	 * @return
	 */
	public <S> List<S> asList(Field<S> field);

	/**
	 * Same as asList(), but puts them into a HashSet.
	 * @return
	 */
	public Set<T> asSet();

	/**
	 * Sums the value of a field grouped by another field.
	 * Sum is calculated by the database.  Objects are not transferred to the JVM.
	 * @param sumField
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <R, S extends Number> Map<R, S> sumBy(Field<S> sumField, Field<R> byField)
			throws SQLException;

	/**
	 * Sums the value of a given field.
	 * Sum is calculated by the database.  Objects are not transferred to the JVM.
	 * @param f
	 * @return
	 * @throws SQLException
	 */
	public <S extends Number> S sum(Field<S> f) throws SQLException;

	/**
	 * Evaluates your query into a map, keyed by whatever field you specify.
	 * If more than one instance has the same key value, the last instance
	 * read will be what the map contains.  (use orderBy() to control ordering)<br>
	 * Note: this reads your entire query into memory.
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <S> Map<S,T> mapBy(Field<S> byField) throws SQLException;

	/**
	 * Evaluates your query into a map, keyed by whatever fields you specify.
	 * If more than one instance has the same key value, the last instance
	 * read will be what the map contains.  (use orderBy() to control ordering)<br>
	 * Note: this reads your entire query into memory.
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <S,U> Map<S,Map<U,T>> mapBy(Field<S> byField1, Field<U> byField2) throws SQLException;

	/**
	 * Evaluates your query into a map, keyed by whatever field you specify.
	 * Multiple instances having the same key value are grouped into one collection.<br>
	 * Note: this reads your entire query into memory.
	 * @param byField
	 * @return
	 * @throws SQLException
	 * @deprecated Use {@link #collectBy(Field&lt;S&gt;)} instead
	 */
	public <S> Map<S, Collection<T>> multiMapBy(Field<S> byField) throws SQLException;

	/**
	 * Evaluates your query into a map, keyed by whatever field you specify.
	 * Multiple instances having the same key value are grouped into one collection.<br>
	 * Note: this reads your entire query into memory.
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <S> Map<S, Collection<T>> collectBy(Field<S> byField) throws SQLException;

	/**
	 * Evaluates your query into a map, keyed by whatever field you specify.
	 * Multiple instances having the same key value are grouped into one collection.<br>
	 * Note: this reads your entire query into memory.
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <S,U> Map<S, Map<U,Collection<T>>> collectBy(Field<S> byField1, Field<U> byField2) throws SQLException;

	/**
	 * Counts the rows grouped by a given field.
	 * Sum is calculated by the database.  Objects are not transferred to the JVM.
	 * @param byField
	 * @return
	 * @throws SQLException
	 */
	public <S> Map<S, Integer> countBy(Field<S> byField) throws SQLException;

	/**
	 * Use a given javax.sql.DataSource.
	 * @param ds
	 * @return
	 */
	public Query<T> use(DataSource ds);

	/**
	 * Use a given java.sql.Connection. &nbsp;
	 * Note user is responsible for eventually closing the connection.
	 * @param ds
	 * @return
	 */
	public Query<T> use(Connection conn);

	/**
	 * Use a given database type. &nbsp;
	 * This is usually automatically detected from the {@code DataSource} or the {@code Connection}.
	 * @param type
	 * @return
	 */
	public Query<T> use(DB_TYPE type);

	/**
	 * Performs a cross join.  Note that this can result in an extraordinary number
	 * of rows returned if not paired with additional where() clauses.
	 * Note you will not be able to access the data in the second table (unless there is
	 * a FK relationship between the &lt;T&gt; and the given object - which if true why
	 * are you using this instead of with()?)
	 * Use with care.
	 * @param t
	 * @return
	 */
	public Query<T> cross(__Alias<? extends Table> t);

	/**
	 * Performs a cross join.  Note that this can result in an extraordinary number
	 * of rows returned if not paired with additional where() clauses.
	 * Note you will not be able to access the data in the second table (unless there is
	 * a FK relationship between the &lt;T&gt; and the given object - which if true why
	 * are you using this instead of with()?)
	 * Use with care.
	 * @param t
	 * @return
	 */
	public Query<T> cross(Class<? extends Table> t);

	/**
	 * Filters the returned results by the specified values.  If the type has a primary key,
	 * this is used for the comparison.  If querying a database a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Query<T> in(Collection<T> set);

	/**
	 * Filters the returned results by the specified values.  If the type has a primary key,
	 * this is used for the comparison.  If querying a database a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Query<T> in(T... ts);

//	/**
//	 * Calculates the intersection between this set and the query.
//	 * If the type has a primary key, this is used for the comparison.
//	 * @param set
//	 * @return
//	 */
//	public Query<T> intersection(Collection<T> set);

	/**
	 * Returns you an iterable of the values only in the given field. &nbsp;
	 * If you want an iterable of more than one field type, you should use
	 * {@code onlyFields(field1, field2, ...)}. &nbsp; This will return you an
	 * iterable of T instances, with only those fields populated. &nbsp;
	 * If Java adds typed tuple support at some point this method may be extended.
	 * @return
	 * @deprecated Use {@link #asIterableOf(Field<S>)} instead
	 */
	public <S> Iterable<S> select(Field<S> field);

	/**
	 * Returns you an iterable of the values only in the given field. &nbsp;
	 * If you want an iterable of more than one field type, you should use
	 * {@code onlyFields(field1, field2, ...)}. &nbsp; This will return you an
	 * iterable of T instances, with only those fields populated. &nbsp;
	 * If Java adds typed tuple support at some point this method may be extended.
	 * @return
	 */
	public <S> Iterable<S> asIterableOf(Field<S> field);

	/**
	 * Evaluates the given query into memory and returns to you a new query backed by
	 * this in-memory store. &nbsp; (does nothing if this is already an in-memory
	 * query.
	 * @return
	 */
	public Query<T> toMemory();

	/**
	 * Turns a query into an "exists" subquery. &nbsp; Example:
	 * <pre><code>select * from A where exists (select * from B where b.a_id = a.id)</code></pre>
	 * &nbsp; Note that this doesn't evaluate this right away. &nbsp; If you want a simple
	 * check for if the query contains any rows, use <code>isEmpty()</code>
	 * @return
	 */
	public Condition exists();

	/**
	 * Returns the DataSource this query is currently using. &nbsp;
	 * Returns null for in-memory queries.
	 * @return
	 */
	public DataSource getDataSource();

	/**
	 * Returns an array of the fields to be selected for this query.
	 * @return
	 */
	public List<Field<?>> getSelectFields();

	/**
	 * Returns the results of the query as a {@code Iterable} of {@code Object[]}s. &nbsp;
	 * Usually used with {@code cross()} and {@code getSelectFields()}. (the latter to know
	 * which array elements are from what fields) &nbsp;
	 * Use sparingly. &nbsp;
	 * @return
	 */
	public Iterable<Object[]> asIterableOfObjectArrays();

	/**
	 * Returns the results of the query as a {@code Iterable} of {@code Map<Field<?>,Object>}s. &nbsp;
	 * Usually used with {@code cross()} and {@code onlyFields()}
	 * (the latter to specify which fields to populate). &nbsp;
	 * @return
	 */
	public Iterable<Map<Field<?>,Object>> asIterableOfMaps();

	/**
	 * Runs the query (with distinct), populating a set of the values only in the given field.
	 * @param field
	 * @return
	 */
	public <S> Set<S> asSet(Field<S> field);

	/**
	 * Performs a cross join.
	 * @param table
	 * @return
	 */
	public <S extends Table> Query<T> crossJoin(Class<S> table);

	/**
	 * Performs a cross join.
	 * @param table
	 * @return
	 */
	public <S extends Table> Query<T> crossJoin(__Alias<S> table);

	/**
	 * Performs a left join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> leftJoin(Class<S> table, Condition on);

	/**
	 * Performs a left join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> leftJoin(__Alias<S> table, Condition on);

	/**
	 * Performs a right join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> rightJoin(Class<S> table, Condition on);

	/**
	 * Performs a right join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> rightJoin(__Alias<S> table, Condition on);

	/**
	 * Performs an outer join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> outerJoin(Class<S> table, Condition on);

	/**
	 * Performs an outer join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> outerJoin(__Alias<S> table, Condition on);

	/**
	 * Performs an inner join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> innerJoin(Class<S> table, Condition on);

	/**
	 * Performs an inner join using the given condition.
	 * This returns a query of the existing type.  (this is useful for filtering)
	 * If you want a join of a combined type (so you can access the data you're joining to), use {@code Join.left()}
	 * @param table
	 * @param condition
	 * @return
	 */
	public <S extends Table> Query<T> innerJoin(__Alias<S> table, Condition on);

	/**
	 * Returns the base type of this query.
	 * @return
	 */
	public Class<T> getType();

}
