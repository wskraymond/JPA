package com.mine.core.vo;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class LocalCache {
	private final Set<String> set = new ConcurrentSkipListSet<>();

	public Set<String> getSet() {
		return set;
	}
	
	public void init() {
		System.out.println("LocalCache is initialized");
	}
	
	public void destroy() {
		System.out.println("LocalCache is destroyed");
	}
	 
}
