package org.peak15.convertcollection.rules;

import java.io.File;
import java.util.List;

import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.workset.Procedure;

/**
 * This is a value type, implementing classes should implement common object methods.
 */
public interface Rule {
	
	/**
	 * @return the directory this rule operates on.
	 */
	File directory();
	
	/**
	 * @return procedure to run on each file
	 */
	Procedure<File> procedure();
	
	/**
	 * @return describes how to traverse the file tree
	 */
	TraversalRule traversalRule();
	
	/**
	 * A singleton class which constructs immutable Rule instances.
	 */
	public static interface Builder {
		
		Rule build(File directory, List<String> args) throws FatalConversionException;
		
		String usage();
	}
}
