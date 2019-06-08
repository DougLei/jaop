package com.douglei.dynamic.proxy;

import java.lang.reflect.Method;
import com.douglei.ProxyInterceptor;

/**
 * 
 * @author DougLei
 */
public class LogProxy extends ProxyInterceptor {

	public LogProxy(Method method) {
		super(method);
	}

	@Override
	protected boolean before(Object obj, Method method, Object[] args) {
		System.out.println("开始执行, 判断有没有注解");
		Transaction t = method.getAnnotation(Transaction.class);
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
