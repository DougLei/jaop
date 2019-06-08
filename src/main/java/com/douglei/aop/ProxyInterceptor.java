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
		this.methods = methods;
	}
	
	/**
	 * 前置处理, 如果返回false, 则不会调用被代理的方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	protected boolean before(Object obj, Method method, Object[] args) {
		return true;
	}
	
	/**
	 * 后置处理, 处理并返回调用被代理的返回值
	 * @param obj
	 * @param method
	 * @param args
	 * @param result
	 * @return
	 */
	protected Object after(Object obj, Method method, Object[] args, Object result) {
		return result;
	}
	
	/**
	 * 调用被代理方法出现异常时的处理
	 * @param obj
	 * @param method
	 * @param args
	 * @param t
	 */
	protected void exception(Object obj, Method method, Object[] args, Throwable t) {
		t.printStackTrace();
	}
	
	/**
	 * 调用被代理方式的finally块
	 * @param obj
	 * @param method
	 * @param args
	 */
	protected void finally_(Object obj, Method method, Object[] args) {
	}
	
	public List<Method> getMethods() {
		return methods;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((methods == null) ? 0 : methods.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ProxyInterceptor other = (ProxyInterceptor) obj;
		if (methods == null) {
			if (other.methods == null) {
				return true;
			}else {
				return false;
			}
		}
		return methods.equals(other.methods);
	}
}
