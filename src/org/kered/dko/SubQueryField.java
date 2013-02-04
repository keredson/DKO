package org.kered.dko;

import java.util.List;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Tuple.Tuple2;

class SubQueryField<T extends Table,S> extends Field<S> {

	transient private DBRowIterator<T> s;
	private DBQuery<T> q;
	private Field<S> field;

	public SubQueryField(Field<S> field, DBQuery<T> subquery) {
		super(field.JAVA_NAME, field.TYPE);
		this.field = field;
		this.q = subquery;
		this.s = new DBRowIterator<T>(subquery);
	}

	@Override
	protected String getSQL(SqlContext context) {
		throw new RuntimeException();
	}

	@Override
	protected void getSQL(StringBuffer sb, SqlContext context) {
		throw new RuntimeException();
	}

	@Override
	protected void getSQL(StringBuffer sb, DB_TYPE dbType) {
		throw new RuntimeException();
	}

	@Override
	protected String getSQL(DB_TYPE dbType) {
		throw new RuntimeException();
	}

	@Override
	protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
		sb.append('(');
		final SqlContext innerContext = new SqlContext(s.getUnderlyingQuery(), context);
		innerContext.maxFields = 1;
		final Tuple2<String, List<Object>> ret = s.getSQL(innerContext);
		sb.append(ret.a);
		bindings.addAll(ret.b);
		sb.append(')');
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((q == null) ? 0 : q.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubQueryField other = (SubQueryField) obj;
		if (q == null) {
			if (other.q != null)
				return false;
		} else if (!q.equals(other.q))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InnerQuery("+ field +")";
	}

}
