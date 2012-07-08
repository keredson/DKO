package org.nosco;

import java.sql.SQLException;
import java.util.Iterator;

class SelectAsObjectArrayIterable implements Iterable<Object[]>, Iterator<Object[]> {

	private final Select<?> select;

	public SelectAsObjectArrayIterable(final Select<?> select) {
		this.select = select;
	}

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

	@Override
	public Iterator<Object[]> iterator() {
		select.iterator();
		return this;
	}

}
