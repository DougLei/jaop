package com.douglei.aop.dynamic.proxy.cglib;

import com.douglei.aop.dynamic.proxy.TransactionTest;

public class Service {
	
	@TransactionTest
	public void add() {
		System.out.println("Service.add()");
	}
}
