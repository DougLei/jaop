package com.douglei.dynamic.proxy.cglib;

import com.douglei.dynamic.proxy.TransactionTest;

public class Service {
	
	@TransactionTest
	public void add() {
		System.out.println("Service.add()");
	}
}
