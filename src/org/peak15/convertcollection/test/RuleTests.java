package org.peak15.convertcollection.test;

import static org.junit.Assert.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.peak15.convertcollection.ConversionRule;
import org.peak15.convertcollection.FatalConversionException;
import org.peak15.convertcollection.TraversalRule;
import org.peak15.convertcollection.WorkSet.ItemFailedException;
import org.peak15.convertcollection.WorkSet.Procedure;
import org.peak15.convertcollection.rules.MP3ify;
import org.peak15.convertcollection.rules.MP3ify.MusicTraversalRule;

import com.esotericsoftware.minlog.Log;

/**
 * use MP3ify for exemplar ConversionRule and TraversalRule
 * 
 * ConversionRule
 * use value type template
 * test all 3 methods
 * 
 * TraversalRule
 * check filesOf
 */
public class RuleTests extends ValueTypeTest<ConversionRule> {
	
	private static final File SOURCE_DIR = new File("test/MP3ify/src");
	private static final String DESTINATION_DIR = "test/MP3ify/dest";
	
	/**
	 * The manually entered array of the expected traversal output list.
	 * 
	 * MP3ify test tree rev 1
	 */
	private static final Set<File> SOURCE_FILES = new HashSet<File>(Arrays.<File>asList(
			new File("test/MP3ify/src/satellite.flac"),
			new File("test/MP3ify/src/mia-roder.mp3"),
			new File("test/MP3ify/src/subdir1/satellite.flac"),
			new File("test/MP3ify/src/subdir2/mia-roder.mp3")));
	
	@Test
	public void testDirectory() {
		assertEquals(SOURCE_DIR, a.sourceDirectory());
	}
	
	@Test
	public void testProcedure() {
		assertNotNull(a.procedure());
	}
	
	@Test
	public void testTraversalRule() {
		try {
			Set<File> traversalOut = new HashSet<>(a.traversalRule().filesOf(SOURCE_DIR));
			assertEquals(SOURCE_FILES, traversalOut);
		} catch (FatalConversionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Override
	protected ConversionRule A() {
		List<String> args = Collections.singletonList(DESTINATION_DIR);
		
		ConversionRule ret = null;
		try {
			ret = MP3ify.builder().build(SOURCE_DIR, args);
		} catch (FatalConversionException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return ret;
	}

	@Override
	protected ConversionRule D() {
		//TODO: Made 2nd ConversionRule type
		
		return new TestRule(SOURCE_DIR);
	}
	
	private static final class TestRule implements ConversionRule {
		
		private final File src;
		
		public TestRule(File src) {
			this.src = src;
		}
		
		@Override
		public File sourceDirectory() {
			return src;
		}

		@Override
		public Procedure<File> procedure() {
			return TestProcedure.INSTANCE;
		}

		@Override
		public TraversalRule traversalRule() {
			return MusicTraversalRule.INSTANCE;
		}
	}
	
	private static enum TestProcedure implements Procedure<File> {
		INSTANCE;
		
		@Override
		public void process(File item) throws ItemFailedException {
			Log.info("TEST PROCESS PROCESSED " + item);
		}
	}
}
