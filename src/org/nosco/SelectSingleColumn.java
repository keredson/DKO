package org.nosco;

import java.util.Iterator;

class SelectSingleColumn<S> implements Iterable<S> {

	private Field<S> field;
	private Query<? extends Table> q;

	SelectSingleColumn(Query<? extends Table> q, Field<S> field) {
		this.q = q;
		this.field = field;
	}

	@Override
	public Iterator<S> iterator() {
		return new Iterator<S>() {
			
			private S next = null;
			private Iterable<? extends Table> it1 = q.all();
			private Iterator<? extends Table> it = it1.iterator();

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
					S tmp = next;
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
