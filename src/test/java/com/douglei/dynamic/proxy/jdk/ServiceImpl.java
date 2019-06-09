package com.douglei.dynamic.proxy.jdk;

import com.douglei.aop.ProxyBeanContext;
import com.douglei.dynamic.proxy.Transaction;

public class ServiceImpl implements IService {

	@Transaction
	@Override
	public void add() {
		System.out.println("ServiceImpl.add()");
		ProxyBeanContext.getProxy(ServiceImpl.class).delete();
	}

	@Transaction
	@Override
	public void delete() {
		System.out.println("ServiceImpl.delete()");
	}
}
