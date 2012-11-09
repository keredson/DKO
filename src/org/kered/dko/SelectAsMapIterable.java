package org.kered.dko;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class SelectAsMapIterable<T extends Table> implements Iterable<Map<Field<?>, Object>> {

	private final DBQuery<T> query;

	public SelectAsMapIterable(final DBQuery<T> q) {
		this.query = q;
	}

	@Override
	public Iterator<Map<Field<?>, Object>> iterator() {
		final Select<T> select = new Select<T>(query, false);
		select.init();
		final List<Field<?>> fields = query.getSelectFields();
		return new Iterator<Map<Field<?>, Object>>() {
			@Override
			public boolean hasNext() {
				try {
					return select.peekNextRow() != null;
				} catch (final SQLException e) {
					throw new RuntimeException(e);
				}
			}
			@Override
			public Map<Field<?>, Object> next() {
				try {
					final Object[] oa = select.getNextRow();
					final Map<Field<?>, Object> ret = new LinkedHashMap<Field<?>, Object>();
					for (int i=0; i<fields.size(); ++i) ret.put(fields.get(i), oa[i]);
					return ret;
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
