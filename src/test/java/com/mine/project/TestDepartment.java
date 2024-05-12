package com.mine.project;

import org.junit.Test;
//import static org.junit.Assert.assertEquals;	
import junit.framework.TestCase;

public class TestDepartment extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		System.out.println("setUp Department");
	}

	@Test
	public void test(){
		System.out.println("Test Department");
		assertEquals(1, 1);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		System.out.println("tearDown Department");
	}

}
