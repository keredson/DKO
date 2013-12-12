package org.kered.dko;

/**
 * This is a marker interface for internal implementation details.  
 * Any other implementations passed to DKO will result in an error.
 * @author derek
 * @param <T>
 */
public interface OrderByExpression<T> {
	public OrderByExpression<T> asc();
	public OrderByExpression<T> desc();
}
