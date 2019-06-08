package com.douglei.dynamic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {
	private static ServiceImpl t = new ServiceImpl();
	
	public static void main(String[] args) {
		InvocationHandler proxy = new IServiceProxy(t);
		IService test = (IService) Proxy.newProxyInstance(IService.class.getClassLoader(), t.getClass().getInterfaces(), proxy);
		test.add();
		
		test.delete();
	}
}
