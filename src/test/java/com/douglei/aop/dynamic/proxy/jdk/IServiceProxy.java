package com.douglei.aop.dynamic.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IServiceProxy implements InvocationHandler{
	
	private Object targetObject;
	public IServiceProxy(Object targetObject) {
		this.targetObject = targetObject;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals("add")) {
			System.out.println("begin");
			
			
			
			Object result = method.invoke(targetObject, args);
			System.out.println("end");
			return result;
		}
		
		return method.invoke(targetObject, args);
	
	}
}
