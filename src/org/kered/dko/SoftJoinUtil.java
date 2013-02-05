package org.kered.dko;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kered.dko.Condition.Binary;
import org.kered.dko.Condition.Binary2;
import org.kered.dko.Field.PK;

class SoftJoinUtil {

	static Map<Field<?>,Field<?>> getFieldsOpposingPK(Class<? extends Table> type, Condition condition) {
		PK<? extends Table> pk = Util.getPK(type);
		if (pk==null) return null;
		Set<Field<?>> pkFields = new HashSet<Field<?>>(pk.GET_FIELDS());
		Map<Field<?>,Field<?>> otherFields = new LinkedHashMap<Field<?>,Field<?>>();
		List<Condition> conditions;
		if (condition instanceof Condition.And) {
			conditions = ((Condition.And)condition).conditions;
		} else {
			conditions = new ArrayList<Condition>();
			conditions.add(condition);
		}
		for (Condition c : conditions) {
			if (c instanceof Binary) {
				Binary bc = (Binary)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					if (pkFields.contains(bc.field) && bc.field2!=null) otherFields.put(bc.field, bc.field2);
					if (pkFields.contains(bc.field2) && bc.field!=null) otherFields.put(bc.field2, bc.field);
					pkFields.remove(bc.field);
					pkFields.remove(bc.field2);
				}
			}
			if (c instanceof Binary2) {
				Binary2 bc = (Binary2)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					if (pkFields.contains(bc.o1) && bc.o2 instanceof Field) otherFields.put((Field<?>) bc.o1, (Field<?>) bc.o2);
					if (pkFields.contains(bc.o2) && bc.o1 instanceof Field) otherFields.put((Field<?>) bc.o2, (Field<?>) bc.o1);
					pkFields.remove(bc.o1);
					pkFields.remove(bc.o2);
				}
			}
		}
		return otherFields;
	}

	static boolean doesConditionCoverPK(Class<? extends Table> type, Condition condition) {
		PK<? extends Table> pk = Util.getPK(type);
		if (pk==null) return false;
		Set<Field<?>> fields = new HashSet<Field<?>>(pk.GET_FIELDS());
		List<Condition> conditions;
		if (condition instanceof Condition.And) {
			conditions = ((Condition.And)condition).conditions;
		} else {
			conditions = new ArrayList<Condition>();
			conditions.add(condition);
		}
		for (Condition c : conditions) {
			if (c instanceof Binary) {
				Binary bc = (Binary)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					fields.remove(bc.field);
					fields.remove(bc.field2);
				}
			}
			if (c instanceof Binary2) {
				Binary2 bc = (Binary2)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					fields.remove(bc.o1);
					fields.remove(bc.o2);
				}
			}
		}
		return fields.isEmpty();
	}

	static int getObjectSizeOfQuery(final Query<? extends Table> q) {
		try {
			final java.lang.reflect.Field f = q.getClass().getDeclaredField("SIZE");
			return f.getInt(q);
		} catch (final NoSuchFieldException e) {
			// not a Join._Query
			return 1;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static class AddNullAtEnd<E extends Table> implements Iterable<E> {
		private Query<E> q;
		public AddNullAtEnd(Query<E> q) {
			this.q = q;
		}
		@Override
		public Iterator<E> iterator() {
			final Iterator<E> i = q.iterator();
			return new Iterator<E>() {
				boolean sentLastNull = false;
				@Override
				public boolean hasNext() {
					return i.hasNext() || !sentLastNull;
				}
				@Override
				public E next() {
					if (i.hasNext()) return i.next();
					sentLastNull = true;
					return null;
				}
				@Override
				public void remove() {
					i.remove();
				}
			};
		}

	}

	static boolean conditionIsAllReferencingQuery(final Condition condition,
			final Query<? extends Table> q) {
		if (!(q instanceof DBQuery))
			return false;
		try {
			final DBQuery<? extends Table> q2 = (DBQuery<? extends Table>) q
					.where(condition);
			final SqlContext context = new SqlContext(q2);
			q2.getWhereClauseAndBindings(context);
			return true;
		} catch (final Util.FieldNotPartOfSelectableTableSet e) {
			return false;
		}
	}


}
