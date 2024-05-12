package com.mine.project;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;


class Calculator
{
	double divide(double a , double b){
		return a/b;
	}
}

@RunWith(Parameterized.class)
public class TestDataPool {
	private Calculator calculator;
	private double a, b,expectedResult;

	public TestDataPool(double a, double b, double expectedResult) {
		this.a = a;
		this.b = b;
		this.expectedResult = expectedResult;
	}
	
	@Before
	public void before(){
		calculator = new Calculator();
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> data(){
		return Arrays.asList(new Object[][]{
				{1d, 2d , 1d/2d},
				{4d, 5d , 4d/5d}
		});
	}
	
	@Test
	public void test(){
		assertEquals(expectedResult, calculator.divide(a, b), 0.00001);
	}
	
}
