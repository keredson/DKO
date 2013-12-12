package org.kered.dko;

import java.util.List;

/**
 * These interfaces are marker interfaces for internal implementation details.  
 * Any other implementations passed to DKO will likely result in an error.
 * @author Derek Anderson
 * @param <T>
 */
public interface Expression<T> {
	
	public interface Select<T> extends Expression<T> {
		Select<T> __getUnderlying();
	}

	public interface OrderBy<T> {
		public OrderBy<T> asc();
		public OrderBy<T> desc();
	}

	void __getSQL(StringBuffer sb, List<Object> bindings, SqlContext context);
	Class<T> getType();

}
