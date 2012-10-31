package org.kered.dko;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Field.FK;
import org.kered.dko.QueryFactory.Callback;

public class QueryAddField extends InMemoryQuery<Table> implements MatryoshkaQuery<Table> {

	private final Query<? extends Table> underlying;
	private final Field<?> field;
	private final Object value;
	private final Callback<Table, ?> func;

	<S> QueryAddField(final Query<? extends Table> underlying, final Field<S> field, final S value) {
		super(Table.class);
		this.underlying = underlying;
		this.field = field;
		this.value = value;
		this.func = null;
	}

	<S> QueryAddField(final Query<? extends Table> underlying, final Field<S> field, final QueryFactory.Callback<Table, S> func) {
		super(Table.class);
		this.underlying = underlying;
		this.field = field;
		this.value = null;
		this.func = func;
	}

	@Override
	public List<Field<?>> getSelectFields() {
		final List<Field<?>> selectFields = underlying.getSelectFields();
		final List<Field<?>> ret = new ArrayList<Field<?>>(selectFields.size()+1);
		ret.addAll(selectFields);
		ret.add(field);
		return Collections.unmodifiableList(ret);
	}

	@Override
	public Iterator<Table> iterator() {
		final Iterator<? extends Table> i = underlying.iterator();
		return new Iterator<Table>() {
			@Override
			public boolean hasNext() {
				return i.hasNext();
			}
			@Override
			public Table next() {
				final Table t = i.next();
				final Object v = func!=null ? func.apply(t) : value;
				return new TableWrapper(t, field, v);
			}
			@Override
			public void remove() {
				i.remove();
			}
		};
	}

	@Override
	public Collection<Query<? extends Table>> getUnderlying() {
		final List<Query<? extends Table>> ret = new ArrayList<Query<? extends Table>>();
		ret.add(underlying);
		return ret;
	}

}
