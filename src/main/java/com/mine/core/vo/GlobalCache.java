package com.mine.core.vo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class GlobalCache implements InitializingBean, DisposableBean{
	private final Map<Thread,String> cacheMap = new ConcurrentHashMap<Thread,String>();

	public Map<Thread, String> getCacheMap() {
		return cacheMap;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("GlobalCache is initialized");
		
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("GlobalCache is destroyed");
	}
	
}
