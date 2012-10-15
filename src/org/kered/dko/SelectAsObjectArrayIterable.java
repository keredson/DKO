package org.kered.dko;

import java.sql.SQLException;
import java.util.Iterator;

class SelectAsObjectArrayIterable<T extends Table> implements Iterable<Object[]> {

	private final DBQuery<T> query;

	public SelectAsObjectArrayIterable(final DBQuery<T> dbQuery) {
		this.query = dbQuery;
	}

	@Override
	public Iterator<Object[]> iterator() {
		final Select<T> select = new Select<T>(query, false);
		select.init();
		return new Iterator<Object[]>() {
			@Override
			public boolean hasNext() {
				try {
					return select.peekNextRow() != null;
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
			@Override
			public Object[] next() {
				try {
					return select.getNextRow();
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
