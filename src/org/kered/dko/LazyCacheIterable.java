package org.kered.dko;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class LazyCacheIterable<T extends Table> implements Iterable<T> {

	private final Iterable<T> src;
	private Iterator<T> srci = null;
	private final List<T> cache;
	int count = 0;
	boolean completelyLoaded = false;

	public LazyCacheIterable(final Iterable<T> src) {
		this.src = src;
		cache = new ArrayList<T>();
	}

	public LazyCacheIterable(final Iterable<T> src, final int size) {
		this.src = src;
		cache = new ArrayList<T>(size);
	}

	@Override
	public Iterator<T> iterator() {
		if (srci==null) initSourceIterator();
		return new CleanableIterator<T>() {

			int position = 0;

			@Override
			public boolean hasNext() {
				if (position==count && completelyLoaded) return false;
				if (!completelyLoaded) peekNext();
				return position < count;
			}

			@Override
			public T next() {
				return cache.get(position++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public synchronized void cleanUp() {
				if (srci instanceof CleanableIterator) ((CleanableIterator)srci).cleanUp();
			}

		};
	}

	private synchronized void peekNext() {
		if (srci.hasNext()) {
			cache.add(srci.next());
			++count;
		} else {
			completelyLoaded = true;
		}
	}

	private synchronized void initSourceIterator() {
		if (srci!=null) return;
		srci = src.iterator();
	}

}
