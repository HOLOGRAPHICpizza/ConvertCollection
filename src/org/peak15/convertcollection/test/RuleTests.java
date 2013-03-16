package org.peak15.convertcollection.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.rules.MP3ify;
import org.peak15.convertcollection.rules.Rule;

/**
 * use MP3ify for exemplar Rule and TraversalRule
 * 
 * Rule
 * use value type template
 * test all 3 methods
 * 
 * TraversalRule
 * check filesOf
 */
public class RuleTests extends ValueTypeTest<Rule> {
	
	private static final String DESTINATION_DIR = "";
	private static final File SOURCE_DIR = new File("");
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Override
	protected Rule A() {
		List<String> args = Collections.singletonList(DESTINATION_DIR);
		
		Rule ret = null;
		try {
			ret = MP3ify.builder().build(SOURCE_DIR, args);
		} catch (FatalConversionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return ret;
	}

	@Override
	protected Rule D() {
		// TODO Auto-generated method stub
		return null;
	}

}
