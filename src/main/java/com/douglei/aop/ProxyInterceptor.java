package com.douglei.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public abstract class ProxyInterceptor {
	protected List<Method> methods;
	
	public ProxyInterceptor(Method method) {
		methods = new ArrayList<Method>(1);
		methods.add(method);
	}
	
	public ProxyInterceptor(List<Method> methods) {
		if(methods == null || methods.size() == 0) {
			throw new NullPointerException(getClass().getName() + "中, 要拦截的方法("+Method.class.getName()+")集合不能为空");
		}
		this.methods = methods;
	}
	
	protected boolean before(Object obj, Method method, Object[] args) {
		return true;
	}

	protected Object after(Object obj, Method method, Object[] args, Object result) {
		return result;
	}
	
	protected void exception(Object obj, Method method, Object[] args, Throwable t) {
		t.printStackTrace();
	}
	
	public List<Method> getMethods() {
		return methods;
	}
}
