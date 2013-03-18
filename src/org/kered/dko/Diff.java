package org.kered.dko;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * This class offers diff logic. &nbsp; Let's assume you have two databases with identical
 * schemas containing a {@code Question} table.  If you wanted to compare them (to perhaps sync
 * them), you could do the following:
 * <pre>  {@code Datasource dsA = [...]; // first database
 *  Datasource dsB = [...]; // second database
 *
 *  Iterable<Question> allAs = Question.ALL.use(dsA).orderBy(Question.ID);
 *  Iterable<Question> allBs = Question.ALL.use(dsB).orderBy(Question.ID);
 *
 *  Iterable<RowChange<Question>> changes = Diff.streamingDiff(allAs, allBs);
 *  for (RowChange<Question> change : changes) {
 *    // do something
 *  }}</pre>
 *
 * Note: It's very important these are sorted in ascending order by their natural
 * ordering!  &nbsp; You will get nonsensical diffs otherwise.
 *
 * @author Derek Anderson
 */
public class Diff {

	/**
	 * @deprecated renamed - use {@link #diff(Iterable<T>,Iterable<T>)} instead
	 */
	public static <T extends Table> Iterable<RowChange<T>> streamingDiff(
			final Iterable<T> from, final Iterable<T> to) {
		return diff(from, to);
	}

	/**
	 * @deprecated renamed - use {@link #diff(Iterable<T>,Iterable<T>,boolean)} instead
	 */
	public static <T extends Table> Iterable<RowChange<T>> streamingDiff(
			final Iterable<T> from, final Iterable<T> to, final boolean emitUnchanged) {
		return diff(from, to, emitUnchanged);
	}

	/**
	 * @deprecated renamed - se {@link #diffActualized(Iterable<T>,Iterable<T>)} instead
	 */
	public static <T extends Table> List<RowChange<T>> streamingDiffActualized(
			final Iterable<T> from, final Iterable<T> to) {
		return diffActualized(from, to);
	}

	/**
	 * This will create a streaming diff of a single input stream. &nbsp; Obviously it
	 * won't provide you the 'deletes' a normal diff would, but it will show you what
	 * has changed in your collection of objects.
	 * @param them
	 * @return
	 */
	public static <T extends Table> Iterable<RowChange<T>> diff(final Iterable<T> them) {
		return new Iterable<RowChange<T>>() {
			@Override
			public Iterator<RowChange<T>> iterator() {
				return new TableChangeIterator<T>(them);
			}
		};
	}

	/**
	 * This will create a streaming diff of two input streams.  &nbsp; The diff is
	 * calculated on the fly as you iterate over the returned object, avoiding having to
	 * load either source input stream (or the resultant stream) into memory all at once.
	 * <p>
	 * Note: It's critically important that the two input lists are sorted by their
	 * natural ordering. &nbsp; This algorithm will not work otherwise.
	 * @param from a sorted {@code Iterable}
	 * @param to a sorted {@code Iterable}
	 * @return
	 */
	public static <T extends Table> Iterable<RowChange<T>> diff(
			final Iterable<T> from, final Iterable<T> to) {
		return new Iterable<RowChange<T>>() {
			@Override
			public Iterator<RowChange<T>> iterator() {
				return new ChangeIterator<T>(from.iterator(), to.iterator(), false);
			}
		};
	}

	/**
	 * This will create a streaming diff of two input streams.  &nbsp; The diff is
	 * calculated on the fly as you iterate over the returned object, avoiding having to
	 * load either source input stream (or the resultant stream) into memory all at once.
	 * <p>
	 * Note: It's critically important that the two input lists are sorted by their
	 * natural ordering. &nbsp; This algorithm will not work otherwise.
	 * @param from a sorted {@code Iterable}
	 * @param to a sorted {@code Iterable}
	 * @param emitUnchanged controls whether or not unchanged objects are returned (with an {@code UNCHANGED} change type)
	 * @return
	 */
	public static <T extends Table> Iterable<RowChange<T>> diff(
			final Iterable<T> from, final Iterable<T> to, final boolean emitUnchanged) {
		return new Iterable<RowChange<T>>() {
			@Override
			public Iterator<RowChange<T>> iterator() {
				return new ChangeIterator<T>(from.iterator(), to.iterator(), emitUnchanged);
			}
		};
	}

	/**
	 * This is identical to {@code streamingDiff(them)}, but the resultant {@code Iterable}
	 * actualized into a {@code List} for you. &nbsp; Of note:
	 * <ul>
	 * <li>The incoming stream is still processed as a stream, meaning it's never loaded
	 * into memory in its entirety.
	 * <li>This will block until the entire diff is created.
	 * <li>In a worst-case scenario, the resultant {@code List} will be the size of the input.
	 * </ul>
	 * @param them a sorted Iterable
	 * @return
	 */
	public static <T extends Table> List<RowChange<T>> diffActualized(
			final Iterable<T> them) {
		final List<RowChange<T>> ret = new ArrayList<RowChange<T>>();
		for (final RowChange<T> v : diff(them)) ret.add(v);
		return ret;
	}

	/**
	 * This is identical to {@code streamingDiff(from, to)}, but the resultant {@code Iterable}
	 * actualized into a {@code List} for you. &nbsp; Of note:
	 * <ul>
	 * <li>The incoming streams are still processed as a stream, meaning they're never loaded
	 * into memory in their entirety.
	 * <li>This will block until the entire diff is created.
	 * <li>In a worst-case scenario, the resultant {@code List} will be the size of both inputs
	 * combined.
	 * </ul>
	 * @param from a sorted Iterable
	 * @param to a sorted Iterable
	 * @return
	 */
	public static <T extends Table> List<RowChange<T>> diffActualized(
			final Iterable<T> from, final Iterable<T> to) {
		final List<RowChange<T>> ret = new ArrayList<RowChange<T>>();
		for (final RowChange<T> v : diff(from, to)) ret.add(v);
		return ret;
	}


	private static enum CHANGE_TYPE {
		ADD, UPDATE, DELETE, UNCHANGED
	}

	private static class ChangeIterator<T extends Table> implements
			Iterator<RowChange<T>> {
		private final Iterator<T> A;
		private final Iterator<T> B;
		private T a = null;
		private T b = null;
		RowChange<T> next = null;
		Map<Class<?>, Set<Field<?>>> fieldsForClass = new HashMap<Class<?>, Set<Field<?>>>();
		private final boolean emitUnchanged;

		private ChangeIterator(final Iterator<T> a, final Iterator<T> b, final boolean emitUnchanged) {
			this.A = a;
			this.B = b;
			this.emitUnchanged = emitUnchanged;
		}

		@Override
		public boolean hasNext() {
			if (next != null)
				return true;
			while (true) {
				if (a == null && A.hasNext())
					a = A.next();
				if (b == null && B.hasNext())
					b = B.next();

				if (a == null && b == null)
					return false;
				if (a == null && b != null) {
					next = new RowChange<T>(CHANGE_TYPE.ADD, b, null);
					b = null;
					return true;
				}
				if (a != null && b == null) {
					next = new RowChange<T>(CHANGE_TYPE.DELETE, a, null);
					a = null;
					return true;
				}
				@SuppressWarnings("unchecked")
				final
				int c = ((Comparable<T>)a).compareTo(b);
				if (c < 0) {
					next = new RowChange<T>(CHANGE_TYPE.DELETE, a, null);
					a = null;
					return true;
				} else if (c > 0) {
					next = new RowChange<T>(CHANGE_TYPE.ADD, b, null);
					b = null;
					return true;
				} else {
					Set<Field<?>> fields = null;
					if (a.getClass().equals(b.getClass())) {
						fields = fieldsForClass.get(a.getClass());
						if (fields == null) {
							fields = new LinkedHashSet<Field<?>>();
							fields.addAll(Util.getFields(a.getClass()));
							fieldsForClass.put(a.getClass(), fields);
						}

					} else {
						fields = new LinkedHashSet<Field<?>>();
						fields.addAll(Util.getFields(a.getClass()));
						fields.addAll(Util.getFields(b.getClass()));
					}
					final Collection<FieldChange<T, ?>> diffs = new ArrayList<FieldChange<T, ?>>();
					for (final Field<?> field : fields) {
						final Object av = a.get(field);
						final Object bv = b.get(field);
						if (av == null ? bv != null : !av.equals(bv)) {
							diffs.add(new FieldChange<T, Object>(
									(Field<Object>) field, av, bv));
						}
					}
					if (diffs.size() > 0) {
						next = new RowChange<T>(CHANGE_TYPE.UPDATE, b, diffs);
						a = null;
						b = null;
						return true;
					} else {
						if (emitUnchanged) {
							next = new RowChange<T>(CHANGE_TYPE.UNCHANGED, a, null);
							a = null;
							b = null;
							return true;
						}
					}
					a = null;
					b = null;
				}
			}
		}

		@Override
		public RowChange<T> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			final RowChange<T> tmp = next;
			next = null;
			return tmp;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * Represents a changed field between two versions of a row.
	 *
	 * @author Derek Anderson
	 *
	 * @param <T> the type of the row that changed
	 * @param <S> the type of the field that changed
	 */
	public static class FieldChange<T extends Table, S> {
		/**
		 * The field that changed.
		 */
		public final Field<S> field;
		/**
		 * The value it changed from.
		 */
		public final S version1;
		/**
		 * The value it changed to.
		 */
		public final S version2;

		private FieldChange(final Field<S> field, final S v1, final S v2) {
			this.field = field;
			this.version1 = v1;
			this.version2 = v2;
		}
		@Override
		public String toString() {
			return "[FieldChange " + field + ", from=" + version1
					+ ", to=" + version2 + "]";
		}
	}

	/**
	 * Represents a change in a row. &nbsp; (either an ADD, UPDATE, or DELETE)
	 *
	 * @author Derek Anderson
	 *
	 * @param <T> the type of the row that changed
	 */
	public static class RowChange<T extends Table> {

		private final CHANGE_TYPE type;
		private final T o;
		private final Collection<FieldChange<T, ?>> updates;

		private RowChange(final CHANGE_TYPE type, final T o,
				final Collection<FieldChange<T, ?>> updates) {
			this.type = type;
			this.o = o;
			this.updates = updates;
		}

		/**
		 * The object representing the row that changed
		 * @return the object representing the row that changed
		 */
		public T getObject() {
			return o;
		}

		/**
		 * A collection of fields and values that changed (if it's an UPDATE)
		 * @return a collection of fields and values that changed (if it's an UPDATE)
		 */
		public Collection<FieldChange<T, ?>> getChanges() {
			return updates;
		}

		/**
		 * If row change was an UPDATE
		 * @return true if row change was an UPDATE
		 */
		public boolean isUpdate() {
			return type == CHANGE_TYPE.UPDATE;
		}

		/**
		 * If row change was an ADD
		 * @return true if row change was an ADD
		 */
		public boolean isAdd() {
			return type == CHANGE_TYPE.ADD;
		}

		/**
		 * If row change was a DELETE
		 * @return true if row change was a DELETE
		 */
		public boolean isDelete() {
			return type == CHANGE_TYPE.DELETE;
		}

		/**
		 * If row change was a UNCHANGED
		 * @return true if row change was a UNCHANGED
		 */
		public boolean isUnchanged() {
			return type == CHANGE_TYPE.UNCHANGED;
		}

		@Override
		public String toString() {
			return "[RowChange " + type + ", " + o + ", " + updates + "]";
		}

	}

	private static class TableChangeIterator<T extends Table> implements
	Iterator<RowChange<T>> {

		private final Iterator<T> them;
		private RowChange<T> next = null;

		public TableChangeIterator(final Iterable<T> them) {
			this.them = them.iterator();
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public boolean hasNext() {
			while (next==null && them.hasNext()) {
				final T t = them.next();
				if (t==null) continue;
				final List<FieldChange<T,?>> changes = new ArrayList<FieldChange<T,?>>();
				CHANGE_TYPE changeType = CHANGE_TYPE.UNCHANGED;
				for (final Field field : Util.getFields(t.getClass())) {
					if (t.__NOSCO_UPDATED_VALUES!=null && t.__NOSCO_UPDATED_VALUES.get(field.INDEX)) {
						changes.add(new FieldChange(field, null, t.get(field)));
					}
				}
				if (t.__NOSCO_ORIGINAL_DATA_SOURCE == null) {
					changeType = CHANGE_TYPE.ADD;
				} else if (!changes.isEmpty()) {
					changeType = CHANGE_TYPE.UPDATE;
				}
				if (changeType != CHANGE_TYPE.UNCHANGED) {
					next = new RowChange<T>(changeType, t, changes);
				}
			}
			return next != null;
		}

		@Override
		public RowChange<T> next() {
			final RowChange<T> tmp = next;
			next = null;
			return tmp;
		}

		@Override
		public void remove() {
			them.remove();
		}

	}


}
