package org.kered.dko;

import java.util.Iterator;

interface ClosableIterator<E> extends Iterator<E> {
	public void close();
}
