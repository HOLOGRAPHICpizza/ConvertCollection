package org.peak15.convertcollection;

public class FatalConversionException extends Exception {

	private static final long serialVersionUID = 7836228965156612020L;

	public FatalConversionException(String message) {
		super(message);
	}
	
	public FatalConversionException(String message, Throwable e) {
		super(message, e);
	}
	
	public FatalConversionException(Throwable e) {
		super(e);
	}
}