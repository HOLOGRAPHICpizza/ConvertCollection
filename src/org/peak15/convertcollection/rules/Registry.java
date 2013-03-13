package org.peak15.convertcollection.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Registers available rules.
 * 
 * This should really be done with ServiceLoader or the Lookup API or annotations or something less crude.
 */
public final class Registry {
	
	private static final Map<String, RuleBuilder> rules = new HashMap<>();
	
	static {
		// Register rules here
		rules.put("mp3ify", new MP3ify.Builder());
	}
	
	public static boolean hasRule(String ruleName) {
		return rules.containsKey(ruleName);
	}
	
	public static RuleBuilder getRule(String ruleName) {
		return rules.get(ruleName);
	}
}
