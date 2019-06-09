package com.douglei.dynamic.proxy.jdk;

import com.douglei.aop.ProxyBeanContext;
import com.douglei.dynamic.proxy.TransactionTest;

public class ServiceImpl implements IService {

	@TransactionTest
	@Override
	public void add() {
		System.out.println("ServiceImpl.add()");
		ProxyBeanContext.getProxy(ServiceImpl.class).delete();
	}

	@TransactionTest
	@Override
	public void delete() {
		System.out.println("ServiceImpl.delete()");
	}
}
