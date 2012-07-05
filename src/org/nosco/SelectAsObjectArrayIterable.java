package org.nosco;

import java.sql.SQLException;
import java.util.Iterator;

class SelectAsObjectArrayIterable implements Iterable<Object[]>, Iterator<Object[]> {

	private Select<?> select;

	public SelectAsObjectArrayIterable(Select<?> select) {
		this.select = select;
	}

	@Override
	public boolean hasNext() {
		try {
			return select.peekNextRow() != null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object[] next() {
		try {
			return select.getNextRow();
		} catch (SQLException e) {
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
