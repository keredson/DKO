package org.kered.dko;

import java.util.Collection;

interface MatryoshkaQuery<T extends Table> extends Query<T> {

	Collection<Query<? extends Table>> getUnderlying();

}
