package org.peak15.convertcollection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A set of work that needs to be performed.
 * Fill it up then run it.
 * The work will be performed on multiple threads.
 * 
 * @param T the type of the problems to solve (type of work set elements)
 */
public final class WorkSet<T> {
	
	private Set<T> workPool = Collections.<T>synchronizedSet(new HashSet<T>());
	
	public WorkSet<T> add(T item) {
		if(item == null) {
			throw new NullPointerException("Work items may not be null.");
		}
		this.workPool.add(item);
		return this;
	}
		
	public WorkSet<T> add(Collection<? extends T> collection) {
		if(collection == null) {
			throw new NullPointerException("Work items may not be null.");
		}
		
		for(T item : collection) {
			this.add(item);
		}
		
		return this;
	}
	
	/**
	 * Process all work using this procedure.
	 * After this, the set is empty and ready for more work.
	 */
	public void doWork(Procedure<T> procedure) {
		throw new UnsupportedOperationException("Not yet implemented!");
	}
}
