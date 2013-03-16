package org.peak15.convertcollection;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.peak15.convertcollection.rules.Registry;
import org.peak15.convertcollection.rules.Rule;
import org.peak15.convertcollection.rules.RuleBuilder;

public class ConvertCollection {	
	
	private static final int EXIT_SUCCESS = 0;
	private static final int EXIT_FAILURE = 1;
	private static final int EXIT_PARTIAL_SUCCESS = 2;
	
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
		
		int exitStatus = EXIT_SUCCESS;
		
		try {
			Rule rule = builder.build(dir, newArgs);
			
			WorkSet<File> ws = new WorkSet<>();
			
			// Walk dir, put files in work set
			//TODO: walk with apache dirwalker
			
			// Execute Work Set
			try {
				ws.execute(rule.getProcedure());
			} catch (InterruptedException e) {
				// The user got impatient and wants to cancel.
				// All work has been stopped.
				throw new FatalConversionException("Interrupted! All work has been canceled.", e);
			}
		}
		catch(FatalConversionException e) {
			System.err.println("Fatal conversion exception!");
			e.printStackTrace();
			exitStatus = EXIT_FAILURE;
		}
		
		System.exit(exitStatus);
	}
}
