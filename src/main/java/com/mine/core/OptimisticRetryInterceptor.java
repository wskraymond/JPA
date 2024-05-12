package com.mine.core;

import javax.persistence.OptimisticLockException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.InitializingBean;

public class OptimisticRetryInterceptor implements MethodInterceptor, InitializingBean {

	private final int MAX_ATTEMPT = 10;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		int numAttempt = 0;
		OptimisticLockException lockExcp = null;
		do{
			try{
				System.out.println("OptimisticRetryInterceptor - start");
				System.out.println(invocation.getMethod().getName());
				//http://forum.spring.io/forum/spring-projects/aop/31180-multiple-method-invocations-from-methodinterceptor-with-beannameautoproxycreator
				/*
				 * https://kknews.cc/zh-hk/other/ezn33q.html
				 * You cannot call proceed() twice in the invoke() method of an interceptor: invoking proceed() will exhaust the interceptor chain held in the MethodInvocation.
				 *	It is possible, though, to do this by using ReflectiveMethodInvocation class.
				 */
				return ((ReflectiveMethodInvocation)invocation).invocableClone().proceed();
			} catch (OptimisticLockException e) {
				lockExcp = e;
			}
			
			System.out.println("OptimisticRetryInterceptor - retry " + numAttempt);
			numAttempt++;
		} while (numAttempt <= MAX_ATTEMPT);
		
		throw lockExcp;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("OptimisticRetryInterceptor init");
		
	}

}
