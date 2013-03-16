package org.peak15.convertcollection;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.Queue;
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
	
	private static final int THREADS = Runtime.getRuntime().availableProcessors();
	
	// -----------------
	// Sync These Things
	// -----------------
	private final Queue<T> workPool = new ArrayDeque<>();
	
	private final Set<Thread> threadPool = new HashSet<>();
	// -----------------
	// With This Thing
	// -----------------
	private final Object syncLock = new Object();
	// -----------------
	
	
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
	 * Process all work using this procedure. (Block until all is done.)
	 * After this, the set is empty and ready for more work.
	 * 
	 * @throws InterruptedException if the user gets impatient waiting for this to return and wants to cancel.
	 * 		The active threads will be told to cleanly abandon or finish their current work item,
	 * 		and all queued work items will be discarded. 
	 */
	public void execute(Procedure<T> procedure) throws InterruptedException {
		if(procedure == null) {
			throw new NullPointerException("Procedure may not be null.");
		}
		
		// spawn threads to pull from the work pool
		// get a lock so the workers can't prematurely notify
		synchronized(this.syncLock) {
			for(int i=0; i < THREADS; i++) {
				Thread worker = new Thread(new Worker<T>(this.workPool, procedure));
				worker.start();
				this.threadPool.add(worker);
			}
			
			// Wait on our workers.
			// The lock will be notified when all workers are done.
			try {
				this.syncLock.wait();
			} 
			catch(InterruptedException e) {
				// User got tired of waiting on the whole process and wants to cancel.
				
				// discard remaining work items
				workPool.clear();
				
				// interrupt workers
				for(Thread t : threadPool) {
					t.interrupt();
				}
				
				// re-throw
				throw e;
			}
		}
		
		// Clear the thread pool just in case, obsolete references suck.
		this.threadPool.clear();
	}
	
	/**
	 * Children call this when they have no more work.
	 * Children MUST DIE after this call!
	 */
	private void signalWorkDone() {
		synchronized(this.syncLock) {
			if(!this.threadPool.contains(Thread.currentThread())) {
				throw new RuntimeException("signalWorkDone() called by a non-child thread!");
			}
			
			this.threadPool.remove(Thread.currentThread());
			
			if(this.threadPool.size() < 1) {
				// All work is done, notify the lock.
				this.syncLock.notify();
			}
		}
	}
	
	private final class Worker<U> implements Runnable {
		
		private final Queue<U> workPool;
		private final Procedure<U> procedure;
		
		private Worker(Queue<U> workPool, Procedure<U> procedure) {
			this.workPool = workPool;
			this.procedure = procedure;
		}
		
		@Override
		public void run() {
			while(workPool.size() > 0) {
				// Pull out an item (safely)
				U item;
				synchronized(syncLock) {
					item = workPool.poll();
				}
				if(item != null) {
					// Process item
					procedure.process(item);
				}
			}
			
			// No more work
			signalWorkDone();
		}
		
	}
}
