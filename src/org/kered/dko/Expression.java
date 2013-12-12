package org.kered.dko;

import java.util.List;

public interface Expression<T> {
	
	public interface Select<T> extends Expression<T> {
		Select<T> __getUnderlying();
	}

	void __getSQL(StringBuffer sb, List<Object> bindings, SqlContext context);
	Class<T> getType();

}
