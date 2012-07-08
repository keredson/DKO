package org.nosco;

import java.util.Iterator;

class SelectSingleColumn<S> implements Iterable<S> {

	private final Field<S> field;
	private final Query<? extends Table> q;

	SelectSingleColumn(final Query<? extends Table> q, final Field<S> field) {
		this.q = q;
		this.field = field;
	}

	@Override
	public Iterator<S> iterator() {
		return new Iterator<S>() {
			
			private S next = null;
			private final Iterable<? extends Table> it1 = q.all();
			private final Iterator<? extends Table> it = it1.iterator();

			@Override
			public boolean hasNext() {
				if (next != null)
					return true;
				if (it.hasNext()) {
					next = it.next().get(field);
					return true;
				}
				return false;
			}

			@Override
			public S next() {
				if (hasNext()) {
					final S tmp = next;
					next = null;
					return tmp;
				}
				throw new RuntimeException("no more available");
			}

			@Override
			public void remove() {
				throw new RuntimeException("remove not implemented");
			}
			
		};
	}

}
