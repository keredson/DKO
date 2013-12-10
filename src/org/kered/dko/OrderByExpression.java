package org.kered.dko;

public interface OrderByExpression<T> {
	public OrderByExpression<T> asc();
	public OrderByExpression<T> desc();
}
