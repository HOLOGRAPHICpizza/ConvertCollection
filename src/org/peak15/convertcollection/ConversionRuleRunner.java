package org.peak15.convertcollection;

import java.io.File;


public final class ConversionRuleRunner {
	// tested via RuleTests
	
	private ConversionRuleRunner() {
		throw new RuntimeException("Attempted to instantiate ConversionRuleRunner.");
	}
	
	/**
	 * @param rule to run
	 * @return true on success, false on partial success.
	 * @throws FatalConversionException on failure.
	 */
	public static boolean run(ConversionRule rule) throws FatalConversionException {	
		WorkSet<File> ws = new WorkSet<>();
		
		// Walk Directory, add source files to work set
		ws.add(rule.traversalRule().filesOf(rule.sourceDirectory()));
		
		// Execute Work Set
		boolean ret;
		try {
			ret = ws.execute(rule.procedure());
		} catch (InterruptedException e) {
			// The user got impatient and wants to cancel.
			// All work has been stopped.
			throw new FatalConversionException("Interrupted! All work has been canceled.", e);
		}
		
		return ret;
	}
}
