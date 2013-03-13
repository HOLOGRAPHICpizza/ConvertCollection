package org.peak15.convertcollection.rules;

import java.io.File;

import org.peak15.convertcollection.ConversionException;
import org.peak15.convertcollection.FatalConversionException;

public interface Rule {
	
	void process(File file) throws ConversionException, FatalConversionException;
}
