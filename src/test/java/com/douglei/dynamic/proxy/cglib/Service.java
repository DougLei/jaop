package com.douglei.dynamic.proxy.cglib;

import com.douglei.dynamic.proxy.Transaction;

public class Service {
	
	@Transaction
	public void add() {
		System.out.println("Service.add()");
	}
}
