package org.kered.dko;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Constants.JOIN_TYPE;
import org.kered.dko.Field.FK;
import org.kered.dko.Table.__Alias;

class SoftJoin<T extends Table> extends AbstractQuery<T> {

    private static final Logger log = Logger.getLogger("org.kered.dko.SoftJoin");

    private Query<? extends Table> q1;
    private Query<? extends Table> q2;
    private final List<Condition> conditions;
    private final JOIN_TYPE joinType;

    private transient List<Field<?>> selectFields;

    public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Join.J> type, final Class<? extends Table> t1, final Class<? extends Table> t2, final Condition on) {
	super(type);
	this.joinType = joinType;
	q1 = QueryFactory.IT.getQuery(t1);
	q2 = QueryFactory.IT.getQuery(t2);
	conditions = new ArrayList<Condition>();
	joinAwareWhere(on);
    }

    public SoftJoin(final SoftJoin<T> q) {
	super(q.ofType);
	joinType = q.joinType;
	q1 = q.q1;
	q2 = q.q2;
	conditions = new ArrayList<Condition>(q.conditions);
    }

    public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Join.J> type, final Class<? extends Table> t1,
	final __Alias<? extends Table> t2, final Condition on)
    {
	super(type);
	this.joinType = joinType;
	q1 = QueryFactory.IT.getQuery(t1);
	q2 = null;//QueryFactory.IT.getQuery(t2);
	conditions = new ArrayList<Condition>();
	// TODO Auto-generated constructor stub
    }

    public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Join.J> type, final __Alias<? extends Table> t1,
	final Class<? extends Table> t2, final Condition on)
    {
	super(type);
	this.joinType = joinType;
	q1 = null;//QueryFactory.IT.getQuery(t1);
	q2 = null;//QueryFactory.IT.getQuery(t2);
	conditions = new ArrayList<Condition>();
	// TODO Auto-generated constructor stub
    }

    public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Join.J> type, final __Alias<? extends Table> t1,
	final __Alias<? extends Table> t2, final Condition on)
    {
	super(type);
	this.joinType = joinType;
	q1 = null;//QueryFactory.IT.getQuery(t1);
	q2 = null;//QueryFactory.IT.getQuery(t2);
	conditions = new ArrayList<Condition>();
	// TODO Auto-generated constructor stub
    }

    @Override
    public Query<T> where(final Condition... conditions) {
	final SoftJoin<T> q = new SoftJoin<T>(this);
	q.joinAwareWhere(conditions);
	return q;
    }

    private void joinAwareWhere(final Condition... conditions) {
	for (final Condition condition : conditions) {
	    if (conditionIsAllReferencingQuery(condition, q1)) {
		q1 = q1.where(condition);
	    } else if (conditionIsAllReferencingQuery(condition, q2)) {
		q2 = q2.where(condition);
	    } else {
		this.conditions.add(condition);
	    }
	}
    }

    private boolean conditionIsAllReferencingQuery(final Condition condition,
	final Query<? extends Table> q) {
	if (!(q instanceof DBQuery)) return false;
	try {
	    final DBQuery<? extends Table> q2 = (DBQuery<? extends Table>) q.where(condition);
	    final SqlContext context = new SqlContext(q2);
	    q2.getWhereClauseAndBindings(context);
	    return true;
	} catch (final Util.FieldNotPartOfSelectableTableSet e) {
		return false;
	}
    }

    @Override
    public Query<T> orderBy(final Field<?>... fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> limit(final long n) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> distinct() {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> max() {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> min() {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> with(final FK... fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> deferFields(final Field<?>... fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> deferFields(final Collection<Field<?>> fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> onlyFields(final Collection<Field<?>> fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> onlyFields(final Field<?>... fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> orderBy(final DIRECTION direction, final Field<?>... fields) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public Query<T> set(final Field<?> key, final Object value) {
	throw new UnsupportedOperationException("Write operations are not supported in software joins.");
    }

    @Override
    public Query<T> set(final Map<Field<?>, Object> values) {
	throw new UnsupportedOperationException("Write operations are not supported in software joins.");
    }

    @Override
    public Query<T> use(final DataSource ds) {
	log.warning("Setting a data source doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
	return this;
    }

    @Override
    public Query<T> use(final Connection conn) {
	log.warning("Setting a connection doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
	return this;
    }

    @Override
    public Query<T> use(final DB_TYPE type) {
	log.warning("Setting a database type doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
	return this;
    }

    @Override
    public Query<T> in(final Collection<T> set) {
	throw new RuntimeException("not implemented yet"); //TODO
    }

    @Override
    public DataSource getDataSource() {
	return q1.getDataSource();
    }

    @Override
    public List<Field<?>> getSelectFields() {
	if (selectFields==null) {
		selectFields = new ArrayList<Field<?>>();
		selectFields.addAll(q1.getSelectFields());
		selectFields.addAll(q2.getSelectFields());
	}
	return selectFields;
    }

    @Override
    public Iterator<T> iterator() {
	return new Iterator<T>() {

	    Iterator<? extends Table> q1i = q1.iterator();
	    Iterator<? extends Table> q2i = q2.iterator();

	    @Override
	    public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	    }

	    @Override
	    public T next() {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public void remove() {
		// TODO Auto-generated method stub

	    }
	};
    }

}
