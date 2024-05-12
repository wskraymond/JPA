package com.mine.project;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCase {
	@BeforeClass
	public static void beforeClass(){
		System.out.println("beforeClass");
	}
	
	@Before
	public void before(){
		System.out.println("before");
	}
	
	@Test
	public void test1(){
		System.out.println("test1");
		assertTrue("4 < 5 is true", 4 < 5);
	}

	@Ignore
	@Test(timeout=1000)
	public void test2(){
		System.out.println("test2");
		while(true);
//		assertNull("it is not null", new Object());
	}
	
	@Ignore
	@Test
	public void test3(){
		System.out.println("test3");
		assertNotNull("it is null", null);
	}
	
	@Test(expected=ArithmeticException.class)
	public void test4(){
		System.out.println("test1");
		throw new ArithmeticException();
	}
	
	@After
	public void after(){
		System.out.println("after");
	}
	
	@AfterClass
	public static void afterClass(){
		System.out.println("afterClass");
	}
}
