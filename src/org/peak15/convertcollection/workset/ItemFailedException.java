package org.peak15.convertcollection.workset;

public class ItemFailedException extends Exception {

	private static final long serialVersionUID = 2494926818047335373L;

	public ItemFailedException(String message) {
		super(message);
	}
	
	public ItemFailedException(String message, Throwable e) {
		super(message, e);
	}
	
	public ItemFailedException(Throwable e) {
		super(e);
	}
}