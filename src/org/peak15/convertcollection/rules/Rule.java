package org.peak15.convertcollection.rules;

import java.io.File;

import org.peak15.convertcollection.workset.Procedure;

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
}
