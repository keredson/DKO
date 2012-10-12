package org.nosco;

interface MatryoshkaQuery<T extends Table> extends Query<T> {

	Query<T> getUnderlying();

}
