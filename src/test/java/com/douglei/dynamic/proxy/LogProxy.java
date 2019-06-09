package com.douglei.dynamic.proxy;

import java.lang.reflect.Method;
import java.util.List;

import com.douglei.aop.ProxyInterceptor;

/**
 * 
 * @author DougLei
 */
public class LogProxy extends ProxyInterceptor {

	public LogProxy(Method method) {
		super(method);
	}
	public LogProxy(List<Method> methods) {
		super(methods);
	}

	@Override
	protected boolean before(Object obj, Method method, Object[] args) {
		System.out.println("开始执行, 判断有没有注解");
		TransactionTest t = method.getAnnotation(TransactionTest.class);
		if(t == null) {
			System.out.println("没有注解, 不执行");
			return false;
		}
		System.out.println(t);
		return true;
	}

	@Override
	protected Object after(Object obj, Method method, Object[] args, Object result) {
		System.out.println("结束执行");
		return result;
	}
}
