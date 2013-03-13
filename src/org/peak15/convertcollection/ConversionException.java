package org.peak15.convertcollection;

public class ConversionException extends Exception {

	private static final long serialVersionUID = 2494926818047335373L;

	public ConversionException(String message) {
		super(message);
	}
	
	public ConversionException(String message, Throwable e) {
		super(message, e);
	}
	
	public ConversionException(Throwable e) {
		super(e);
	}
}