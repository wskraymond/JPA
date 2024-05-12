package com.mine.core;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.core.Ordered;

public class MyPointcutAdvisor implements PointcutAdvisor, Ordered {
	private Advice advice;
	private Pointcut pointcut;
	private int order;

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	@Override
	public int getOrder() {
		return order;
	}

}
