package com.douglei;

import java.lang.reflect.Method;

/**
 * 
 * @author DougLei
 */
public abstract class ProxyInterceptor {
	protected Method method;
	
	public ProxyInterceptor(Method method) {
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
	
	public Method getMethod() {
		return method;
	}
}
