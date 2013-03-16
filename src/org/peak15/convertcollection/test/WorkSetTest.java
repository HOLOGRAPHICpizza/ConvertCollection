package org.peak15.convertcollection.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.peak15.convertcollection.workset.WorkSet;

/**
 * Fill up a pool, execute it, check for right answer.
 * Do it a second time.
 */
public class WorkSetTest {

	@Test
	public void test() {
		fail("Not yet implemented");
		
		try {
			new WorkSet<String>().add("taco").add("blarg").add("tac").execute(null);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
