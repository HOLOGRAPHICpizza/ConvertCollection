package org.peak15.convertcollection.rules;

import java.io.File;

import org.apache.commons.io.DirectoryWalker;
import org.peak15.convertcollection.Procedure;

public interface Rule {
	
	/**
	 * @return procedure to run on each file
	 */
	Procedure<File> getProcedure();
	
	/**
	 * @return describes how to traverse the file tree
	 */
	DirectoryWalker<File> getDirectoryWalker();
}
