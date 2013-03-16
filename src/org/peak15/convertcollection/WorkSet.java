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
 * Not thread safe.
 * 
 * @param T the type of the problems to solve (type of work set elements)
 */
public final class WorkSet<T> {
	
	private Set<T> workPool = Collections.<T>synchronizedSet(new HashSet<T>());
	
	private final Object syncLock = new Object();
	
	private static final int THREADS = Runtime.getRuntime().availableProcessors();
	private int currentThreads = 0;
	
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
		//throw new UnsupportedOperationException("Not yet implemented!");
		
		if(procedure == null) {
			throw new NullPointerException("Procedure may not be null.");
		}
		
		// spawn threads to pull from the work pool
		// get a lock so the workers can't prematurely notify
		synchronized(this.syncLock) {
			for(currentThreads=0; currentThreads < THREADS; currentThreads++) {
				new Thread(new Worker<T>(this.workPool)).start();
			}
			
			// Wait on our workers.
			// The lock will be notified when all workers are done.
			try {
				this.syncLock.wait();
			} 
			catch(InterruptedException e) {}
		}
	}
	
	/**
	 * Children call this when they have no more work.
	 * Children MUST DIE after this call!
	 */
	private void signalWorkDone() {
		synchronized(this.syncLock) {
			currentThreads--;
			
			if(currentThreads < 0) {
				// All work is done, notify the lock.
				currentThreads = 0;
				this.syncLock.notify();
			}
		}
	}
	
	private static final class Worker<T> implements Runnable {
		
		private final Set<T> workPool;
		
		private Worker(Set<T> workPool) {
			this.workPool = workPool;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			throw new UnsupportedOperationException("Not yet implemented!");
		}
		
	}
}
