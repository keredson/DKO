package org.kered.dko;

import java.util.Iterator;

interface PeekableIterator<T> extends Iterator<T> {
	
	public T peek();

}
