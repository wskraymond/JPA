package com.mine.core;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

import com.mine.core.inf.MyFas;

public class HttpFactoryBean implements FactoryBean<MyFas>{
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public MyFas getObject() throws Exception {
		return new MyFas(){
			@Override
			public List<String> getResultList(String staff) {
				return Arrays.asList(url);
			}};
	}

	@Override
	public Class<?> getObjectType() {
		return MyFas.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}



}
