package org.kered.dko;

import java.util.Iterator;

interface CleanableIterator<E> extends Iterator<E> {
	void cleanUp();
}
