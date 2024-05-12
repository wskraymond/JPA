package com.mine.core;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class AccessAuditLogAdvice implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("AccessAuditLogAdvice");
		try{
			return invocation.proceed();
		} catch(Exception e) {
			throw e;
		} finally {
			System.out.println(invocation.getClass().getName() 
				+ " - " + invocation.getMethod().getName() 
				+ " - " + invocation.getArguments() 
				+ " - " + invocation.getThis()
				+ " - " + invocation.getStaticPart());
		}
	}

}
