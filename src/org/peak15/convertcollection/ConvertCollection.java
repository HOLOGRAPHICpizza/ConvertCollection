package org.peak15.convertcollection;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.peak15.convertcollection.rules.Registry;
import org.peak15.convertcollection.rules.Rule;
import org.peak15.convertcollection.rules.RuleBuilder;

public class ConvertCollection {	
	
	/**
	 * @param args Usage: java ConvertCollection rule-name [rule-args...]
	 */
	public static void main(String[] args) {
		if(args.length < 2) {
			throw new IllegalArgumentException("Usage: java ConvertCollection rule-name directory [rule-args...]");
		}
		else if(!Registry.hasRule(args[0])) {
			throw new IllegalArgumentException("Specified rule not found.");
		}
		
		File dir = new File(args[1]);
		
		if(!(dir.isDirectory() && dir.canRead())) {
			throw new IllegalArgumentException("directory must be a readable directory.");
		}
		
		RuleBuilder builder = Registry.getRule(args[0]);
		
		List<String> newArgs = new LinkedList<>();
		for(int i = 2; i < args.length; i++) {
			newArgs.add(args[i]);
		}
		
		try {
			Rule rule = builder.build(dir, newArgs);
			
			// Walk dir
			//TODO: Walk dir, with thread pool.
		}
		catch(FatalConversionException e) {
			System.err.println("Fatal conversion error!");
			e.printStackTrace();
			System.exit(1);
		}
	}
}
