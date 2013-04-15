package org.peak15.convertcollection.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.peak15.convertcollection.WorkSet;
import org.peak15.convertcollection.WorkSet.ItemFailedException;

import com.esotericsoftware.minlog.Log;

/**
 * Fill up a pool, execute it, check for right answer.
 * Do it a second time.
 */
public class WorkSetTest {
	
	public static final Set<String> testSrc = new HashSet<>();
	static {
		testSrc.addAll(Arrays.asList("taco", "blarg", "tac"));
	}
	
	public static final Set<String> testDest = new HashSet<>();
	public static int seconds = 1;
	
	@Test
	public void test() {
		try {
			// fill the set
			WorkSet<String> ws = new WorkSet<String>().add(testSrc);
			assertEquals("set filled", testSrc, ws.items());
			
			// run the set
			ws.execute(TestProcedure.INSTANCE);
			assertEquals("set processed", testSrc, testDest);
			assertTrue("set emptied", ws.items().isEmpty());
			
			// reset
			testDest.clear();
			seconds = 0;
			
			// fill the set again
			ws.add(testSrc);
			assertEquals("set filled 2", testSrc, ws.items());
			
			// run the set again
			ws.execute(TestProcedure.INSTANCE);
			assertEquals("set processed 2", testSrc, testDest);
			assertTrue("set emptied 2", ws.items().isEmpty());
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static enum TestProcedure implements WorkSet.Procedure<String> {
		INSTANCE;
		
		@Override
		public void process(String item) throws ItemFailedException {
			Log.info("Processing: " + item);
			WorkSetTest.testDest.add(item);
			try {
				Thread.sleep(WorkSetTest.seconds++ * 1000);
			} catch (InterruptedException e) {
				throw new ItemFailedException(e);
			}
			Log.info("Finished: " + item);
		}
	}
}
