package com.douglei.aop.dynamic.proxy.jdk;

import com.douglei.aop.ProxyBeanContainer;
import com.douglei.aop.dynamic.proxy.TransactionTest;

public class ServiceImpl implements IService {

	@TransactionTest
	@Override
	public void add() {
		System.out.println("ServiceImpl.add()");
		ProxyBeanContainer.getProxy(ServiceImpl.class).delete();
	}

	@TransactionTest
	@Override
	public void delete() {
		System.out.println("ServiceImpl.delete()");
	}
}
