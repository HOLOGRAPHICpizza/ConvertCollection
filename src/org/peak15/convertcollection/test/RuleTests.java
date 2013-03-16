package org.peak15.convertcollection.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
	
	private static final File SOURCE_DIR = new File("");
	private static final String DESTINATION_DIR = "";
	private static final String DESTINATION_DIR_2 = "";
	
	private static final Queue<File> SOURCE_FILES = new LinkedList<File>(Arrays.<File>asList(
			null,
			null));
	
	@Test
	public void testDirectory() {
		assertEquals(SOURCE_DIR, a.directory());
	}
	
	@Test
	public void testProcedure() {
		assertNotNull(a.procedure());
	}
	
	@Test
	public void testTraversalRule() {
		try {
			assertEquals(SOURCE_FILES, a.traversalRule().filesOf(SOURCE_DIR));
		} catch (FatalConversionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
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
		List<String> args = Collections.singletonList(DESTINATION_DIR_2);
		
		Rule ret = null;
		try {
			ret = MP3ify.builder().build(SOURCE_DIR, args);
		} catch (FatalConversionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return ret;
	}

}
