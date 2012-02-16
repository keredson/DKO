package org.nosco;

public class QueryFactory {
	
	protected QueryFactory() {}
	
	public static final QueryFactory IT = new QueryFactory();
	
	public <T extends Table> Query<T> getQuery(Class<T> cls) {
		return new QueryImpl<T>(cls);
	}

}
