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
		final DBRowIterator<T> i = new DBRowIterator<T>(query, false);
		i.init();
		final List<Field<?>> fields = query.getSelectFields();
		return new Iterator<Map<Field<?>, Object>>() {
			@Override
			public boolean hasNext() {
				return i.peek() != null;
			}
			@Override
			public Map<Field<?>, Object> next() {
				final Object[] oa = i.next();
				final Map<Field<?>, Object> ret = new LinkedHashMap<Field<?>, Object>();
				for (int i=0; i<fields.size(); ++i) ret.put(fields.get(i), oa[i]);
				return ret;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

}
