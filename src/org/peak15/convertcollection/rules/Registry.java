package org.peak15.convertcollection.rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Registers available rules.
 * 
 * This should really be done with ServiceLoader or the Lookup API or annotations or something less crude.
 */
public final class Registry {
	
	private static final Map<String, Rule.Builder> rules = new HashMap<>();
	
	static {
		// Register rules here
		rules.put("mp3ify", MP3ify.builder());
	}
	
	public static boolean hasRule(String ruleName) {
		return rules.containsKey(ruleName);
	}
	
	public static Rule.Builder getRule(String ruleName) {
		return rules.get(ruleName);
	}
}
