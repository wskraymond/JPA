package com.mine.core;

import java.lang.reflect.Method;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

public class MyPointcut implements Pointcut {

	@Override
	public ClassFilter getClassFilter() {
		return new ClassFilter(){

			@Override
			public boolean matches(Class<?> clz) {
				return clz.getName().contains("MyBizSvc");
			}
			
		};
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return new MethodMatcher(){

			@Override
			public boolean isRuntime() {
				return false;
			}

			@Override
			public boolean matches(Method method, Class<?> clz) {
				return clz.getName().contains("MyBizSvc") && method.getName().equals("foo");
			}

			@Override
			public boolean matches(Method method, Class<?> clz, Object[] args) {
				return matches(method,clz) && (args==null || args.length==0);
			}
			
		};
	}

}
