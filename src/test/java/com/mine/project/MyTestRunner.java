package com.mine.project;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MyTestRunner {
	public static void main(String[] args){
		System.out.println("--------Test Runner Start----------");
		Result r = JUnitCore.runClasses(PoTestSuite.class);
		for(Failure f : r.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println("is it Successful : " + r.wasSuccessful());
		System.out.println("---------Test Runner End-----------");
	}
}
