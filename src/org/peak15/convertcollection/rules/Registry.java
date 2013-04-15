package org.peak15.convertcollection.rules;

import java.util.HashMap;
import java.util.Map;

import org.peak15.convertcollection.ConversionRule;

/**
 * Registers available conversionRules.
 * 
 * This should really be done with ServiceLoader or the Lookup API or annotations or something less crude.
 */
public final class Registry {
	// no test necessary
	
	private static final Map<String, ConversionRule.Builder> conversionRules = new HashMap<>();
	
	private Registry() {
		throw new RuntimeException("Attempted to instantiate Registry.");
	}
	
	static {
		// Register conversionRules here
		conversionRules.put("mp3ify", MP3ify.builder());
	}
	
	public static boolean hasRule(String ruleName) {
		return conversionRules.containsKey(ruleName);
	}
	
	public static ConversionRule.Builder getRule(String ruleName) {
		return conversionRules.get(ruleName);
	}
}
