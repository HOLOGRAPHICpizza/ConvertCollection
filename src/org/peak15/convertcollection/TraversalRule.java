package org.peak15.convertcollection;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.filefilter.IOFileFilter;

public abstract class TraversalRule extends DirectoryWalker<File> {
	// tested via RuleTests
	
	protected TraversalRule(IOFileFilter fileFilter, IOFileFilter directoryFilter, int depth) {
		super(fileFilter, directoryFilter, depth);
	}
	
	/**
	 * @param directory to traverse
	 * @return files from the traversal
	 * @throws FatalConversionException if traversal does not succeed
	 */
	public Queue<File> filesOf(File directory) throws FatalConversionException {
		if(directory == null) {
			throw new NullPointerException("Directory may not be null.");
		}
		else if(!(directory.isDirectory() && directory.canRead())) {
			throw new FatalConversionException(
					new IllegalArgumentException("directory must be a readable directory."));
		}
		
		Queue<File> files = new LinkedList<>();
		
		try {
			walk(directory, files);
		} catch (IOException e) {
			throw new FatalConversionException("Failed to traverse directory.", e);
		}
		
		return files;
	}
}
