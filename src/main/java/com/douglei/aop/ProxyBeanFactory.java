package com.douglei.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author DougLei
 */
class ProxyBeanFactory {
	private ProxyBean proxyBean;

	public ProxyBean getProxyBean() {
		return proxyBean;
	}
	
	/**
	 * 创建代理对象
	 * @param clz
	 * @param object
	 * @return
	 */
	public <T> T createProxy(Class<T> clz, Object object) {
		T proxy = newProxyInstance(clz, object);
		proxyBean = new ProxyBean(object, proxy);
		return (T) proxy;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T newProxyInstance(Class<T> clz, Object object) {
		if(clz.getSuperclass().isInterface()) {
			return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return coreInvoke(object, method, args);
				}
			});
		}else {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clz);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					return coreInvoke(object, method, args);
				}
			});
			return (T) enhancer.create();
		}
	}
	
	private Object coreInvoke(Object obj, Method method, Object[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object result = null;
		if(proxyBean.before(obj, method, args)) {
			try {
				result = method.invoke(obj, args);
				result = proxyBean.after(obj, method, args, result);
			} catch (Exception e) {
				proxyBean.exception(obj, method, args, e);
			}
		}
		return result;
	}
}
