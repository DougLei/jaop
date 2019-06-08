package com.douglei.dynamic.proxy.jdk;

import com.douglei.dynamic.proxy.Transaction;

public class ServiceImpl implements IService {

	@Transaction
	@Override
	public void add() {
		System.out.println("ServiceImpl.add()");
		delete();
	}

	@Override
	public void delete() {
		System.out.println("ServiceImpl.delete()");
	}
}
