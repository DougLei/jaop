package com.douglei.dynamic.proxy.jdk;

public class ServiceImpl implements IService {

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
