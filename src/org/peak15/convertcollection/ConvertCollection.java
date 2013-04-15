package org.peak15.convertcollection;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.peak15.convertcollection.rules.Registry;

import com.esotericsoftware.minlog.Log;

public final class ConvertCollection {
	// no test necessary
	
	private static final int LOG_LEVEL = Log.LEVEL_TRACE;
	
	private static final int EXIT_SUCCESS = 0;
	private static final int EXIT_FAILURE = 1;
	private static final int EXIT_PARTIAL_SUCCESS = 2;
	
	private static final String LOGNAME = "ConvertCollection";
	
	private ConvertCollection() {
		throw new RuntimeException("Attempted to instantiate ConvertCollection.");
	}
	
	/**
	 * @param args Usage: java ConvertCollection rule-name directory [rule-args...]
	 */
	public static void main(String[] args) {
		Log.set(LOG_LEVEL);
		
		if(args.length < 2) {
			throw new IllegalArgumentException("Usage: java ConvertCollection rule-name directory [rule-args...]");
		}
		else if(!Registry.hasRule(args[0])) {
			throw new IllegalArgumentException("Specified rule not found.");
		}
		
		File dir = new File(args[1]);
		
		ConversionRule.Builder builder = Registry.getRule(args[0]);
		
		List<String> newArgs = new LinkedList<>();
		for(int i = 2; i < args.length; i++) {
			newArgs.add(args[i]);
		}
		
		int exitStatus = EXIT_FAILURE;
		
		try {
			ConversionRule rule = builder.build(dir, newArgs);
			
			if(ConversionRuleRunner.run(rule)) {
				Log.info(LOGNAME, "Success: All items processed!");
				exitStatus = EXIT_SUCCESS;
			}
			else {
				Log.warn(LOGNAME, "Partial Success: One or more items failed to process.");
				exitStatus = EXIT_PARTIAL_SUCCESS;
			}
		}
		catch(FatalConversionException e) {
			Log.error(LOGNAME, "Fatal conversion exception!", e);
		}
		
		System.exit(exitStatus);
	}
}
