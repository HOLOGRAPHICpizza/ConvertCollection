package org.peak15.convertcollection;

import java.io.File;
import java.util.List;

import org.peak15.convertcollection.WorkSet.Procedure;

/**
 * This is a value type, implementing classes should implement common object methods.
 */
public interface ConversionRule {
	// tested via RuleTests
	
	/**
	 * @return the directory this rule operates on.
	 */
	File sourceDirectory();
	
	/**
	 * @return procedure to run on each file
	 */
	Procedure<File> procedure();
	
	/**
	 * @return describes how to traverse the file tree
	 */
	TraversalRule traversalRule();
	
	/**
	 * A singleton class which constructs immutable ConversionRule instances.
	 */
	public static interface Builder {
		
		ConversionRule build(File sourceDirectory, List<String> args) throws FatalConversionException;
		
		String usage();
	}
}
