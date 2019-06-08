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
	
	protected boolean before(Method method, Object[] args) {
		return true;
	}

	protected Object after(Object result) {
		return result;
	}
	
	protected Object exception(Object result, Throwable t) {
		return result;
	}
	
	public Class<?> getClz() {
		return clz;
	}
	public Method getMethod() {
		return method;
	}
}
