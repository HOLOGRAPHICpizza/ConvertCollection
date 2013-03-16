package org.peak15.convertcollection.workset;

public interface Procedure<T> {
	void process(T item) throws ItemFailedException;
}
