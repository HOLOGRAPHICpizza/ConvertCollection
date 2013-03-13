package org.peak15.convertcollection.rules;

import java.io.File;
import java.util.List;

import org.peak15.convertcollection.FatalConversionException;

/**
 * A singleton class which constructs immutable Rule instances.
 */
public interface RuleBuilder {
	
	Rule build(File directory, List<String> args) throws FatalConversionException;
	
	String usage();
}
