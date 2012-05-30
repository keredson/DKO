package org.nosco;

import java.util.Iterator;

class SelectAsObjectArrayIterable implements Iterable<Object[]>, Iterator<Object[]> {

	private Select<?> select;

	public SelectAsObjectArrayIterable(Select<?> select) {
		this.select = select;
	}

	@Override
	public boolean hasNext() {
		return select.hasNext();
	}

	@Override
	public Object[] next() {
		select.next();
		Object[] ret = new Object[select.lastFieldValues.length];
		System.arraycopy(select.lastFieldValues, 0, ret, 0, select.lastFieldValues.length);
		return ret;
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
