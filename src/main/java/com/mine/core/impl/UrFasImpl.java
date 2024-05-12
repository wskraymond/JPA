package com.mine.core.impl;

import java.util.Arrays;
import java.util.List;

import com.mine.core.inf.MyFas;

public class UrFasImpl implements MyFas {

	@Override
	public List<String> getResultList(String staff) {
		System.out.println("UrFasImpl - BII");
		return Arrays.asList("hi");
	}

}
