package com.douglei;

import java.lang.reflect.Method;

/**
 * 
 * @author DougLei
 */
public abstract class ProxyInterceptor {
	protected Class<?> clz;
	protected Method method;
	
	public ProxyInterceptor(Class<?> clz, Method method) {
		this.clz = clz;
		this.method = method;
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
	
	public Class<?> getClz() {
		return clz;
	}
	public Method getMethod() {
		return method;
	}
}
