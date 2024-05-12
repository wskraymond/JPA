package com.mine.core.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.mine.core.inf.MyBizSvc;
import com.mine.core.inf.MyDao;
import com.mine.core.inf.MyFas;
import com.mine.core.vo.GlobalCache;
import com.mine.core.vo.LocalCache;

public class MyBizSvcImpl implements MyBizSvc {
	
	private String beanName;
	private MyDao myDao;
	@Autowired
	private MyFas myFas;
	
	private LocalCache localCh;
	private GlobalCache globalCache; 
	
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	public MyDao getMyDao() {
		return myDao;
	}
	public void setMyDao(MyDao myDao) {
		this.myDao = myDao;
	}
	public LocalCache getLocalCh() {
		return localCh;
	}
	public void setLocalCh(LocalCache localCh) {
		this.localCh = localCh;
	}
	public GlobalCache getGlobalCache() {
		return globalCache;
	}
	public void setGlobalCache(GlobalCache globalCache) {
		this.globalCache = globalCache;
	}
	
	@Override
	public void foo() {
		System.out.println(beanName + " " + getMyDao().findJobName(0) /*+ myFas.getResultList(null)*/);
	}

}
