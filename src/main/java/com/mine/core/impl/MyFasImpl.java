package com.mine.core.impl;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.aop.framework.AopContext;

import com.mine.core.inf.MyFas;

public class MyFasImpl implements MyFas {
	private String name;
	
	public String getName() {
		Thread.currentThread().dumpStack();
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public List<String> getResultList(String staff) {
//		System.out.println("MyFasImpl - " + getName() + " " + AopContext.currentProxy());
		Thread.currentThread().dumpStack();
		throw new OptimisticLockException();
	}

}
