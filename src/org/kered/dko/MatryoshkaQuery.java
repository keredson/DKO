package org.kered.dko;

interface MatryoshkaQuery<T extends Table> extends Query<T> {

	Query<T> getUnderlying();

}
