package org.nosco;

public class Constants {
	
	public static enum DIRECTION {
		ASCENDING,
		DESCENDING
	}
	
	public enum LOGIC {
		AND,
		OR,
		NOT
	}
	
	public enum OLD_RELATIONSHIP {
		EQUALS,
		EXACT,
		IEXACT,
		LESS_THAN,
		GREATER_THAN,
		LESS_THAN_OR_EQUALS,
		GREATER_THAN_OR_EQUALS,
		GTE,
		LTE,
		NOT_EQUALS,
		LIKE,
		ILIKE,
		CONTAINS,
		ICONTAINS,
		IN,
		GT,
		LT,
		STARTSWITH,
		ISTARTSWITH,
		ENDSWITH,
		IENDSWITH,
		BETWEEN,
		IS_NULL,
	}

	public enum FIELD_TYPE {
		INTEGER,
		NUMERIC,
		STRING,
		BINARY,
		BOOLEAN,
		LONG,
	}

}
