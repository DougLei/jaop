package com.douglei.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代理拦截器, 需要自定义实现, 增强目标方法/类
 * @author DougLei
 */
public abstract class ProxyInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(ProxyInterceptor.class);
	
	protected Class<?> clz;
	protected List<ProxyMethod> methods;
	
	public ProxyInterceptor(Class<?> clz, ProxyMethod method) {
		this.clz = clz;
		if(method != null) {
			methods = new ArrayList<ProxyMethod>(1);
			methods.add(method);
		}
	}
	
	public ProxyInterceptor(Class<?> clz, List<ProxyMethod> methods) {
		this.clz = clz;
		this.methods = methods;
	}
	
	/**
	 * 前置处理, 如果返回false, 则不会调用被代理的方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	protected boolean before_(Object obj, Method method, Object[] args) {
		return true;
	}
	
	/**
	 * 后置处理, 处理并返回调用被代理的返回值
	 * @param obj
	 * @param method
	 * @param args
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	protected Object after_(Object obj, Method method, Object[] args, Object result) throws Throwable{
		return result;
	}
	
	/**
	 * 调用被代理方法出现异常时的处理
	 * @param obj
	 * @param method
	 * @param args
	 * @param t
	 */
	protected void exception_(Object obj, Method method, Object[] args, Throwable t) {
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
	
	/**
	 * 是否代理所有方法
	 * @return
	 */
	final boolean proxyAllMethod() {
		return methods == null;
	}
	
	/**
	 * 是否拦截指定方法
	 * @param interceptedMethod
	 * @return
	 */
	final boolean isInterceptMethod(Method interceptedMethod) {
		for (ProxyMethod proxyMethod : methods) {
			if(logger.isDebugEnabled()) {
				logger.debug("拦截的方法[{}]来自[{}]", interceptedMethod, interceptedMethod.getDeclaringClass());
			}
			if(proxyMethod.equalMethod(interceptedMethod)) {
				return true;
			}
		}
		return false;
	}
}
