package org.kered.dko;

import javax.sql.DataSource;

/**
 * This is a relatively uninteresting factory class that you will likely
 * never use. &nbsp; Please look at the generated <code>TableName.ALL</code> objects
 * for starting your queries. &nbsp; (DataSources can be specified with
 * <code>TableName.ALL.use(ds)</code>)
 * @author dander
 *
 */
public class QueryFactory {

	protected QueryFactory() {}

	/**
	 * singleton instance
	 */
	public static final QueryFactory IT = new QueryFactory();

	/**
	 * Creates a query centered around the given class.
	 * @param cls
	 * @return
	 */
	public <T extends Table> Query<T> getQuery(final Class<T> cls) {
		return new DBQuery<T>(cls);
	}

	/**
	 * Creates a query centered around the given class using the given datasource.
	 * @param cls
	 * @param ds
	 * @return
	 */
	public <T extends Table> Query<T> getQuery(final Class<T> cls, final DataSource ds) {
		return new DBQuery<T>(cls, ds);
	}

	/**
	 * Creates a new query based the given query, but adds a constant field to the resulting list of tables.
	 * @param cls
	 * @return
	 */
	public <S, T extends Table> Query<Table> addField(final Query<T> q, final Field<S> field, final S value) {
		return new QueryAddField(q, field, value);
	}

	/**
	 * Creates a new query based the given query, but adds a function-defined field to the resulting list of tables.
	 * @param cls
	 * @return
	 */
	public <S, T extends Table> Query<Table> addField(final Query<T> q, final Field<S> field, final Function<Table,S> func) {
		return new QueryAddField(q, field, func);
	}

	public static interface Function<A,B> {
		public B apply(A a);
	}

}
