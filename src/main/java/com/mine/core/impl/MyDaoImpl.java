package com.mine.core.impl;

import com.mine.core.inf.MyDao;

public class MyDaoImpl implements MyDao {

	@Override
	public String findJobName(long uid) {
		return "Programmer";
	}

}
