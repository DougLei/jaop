package com.douglei.aop;

import java.io.Closeable;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * @author DougLei
 */
class ProxyBeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(ProxyBeanFactory.class);
	
	private ProxyBean proxyBean;

	public ProxyBean getProxyBean() {
		return proxyBean;
	}
	
	/**
	 * 创建代理对象
	 * @param clz
	 * @param originObject
	 * @return
	 */
	public <T> T createProxy(Class<T> clz, Object originObject) {
		T proxy = newProxyInstance(clz, originObject);
		proxyBean = new ProxyBean(originObject, proxy);
		return (T) proxy;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T newProxyInstance(Class<T> clz, Object originObject) {
		if(isCreateDynamicProxyByInterface(clz.getInterfaces())) {
			return (T) Proxy.newProxyInstance(clz.getClassLoader(), clz.getInterfaces(), new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return coreInvoke(originObject, method, args);
				}
			});
		}else {
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clz);
			enhancer.setCallback(new MethodInterceptor() {
				@Override
				public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
					return coreInvoke(originObject, method, args);
				}
			});
			return (T) enhancer.create();
		}
	}
	
	/**
	 * 是否根据接口创建动态代理
	 * @param interfaces
	 * @return
	 */
	private boolean isCreateDynamicProxyByInterface(Class<?>[] interfaces) {
		if(interfaces.length > 0) {
			byte count = 0;
			for (Class<?> interface_ : interfaces) {
				if(isIgnore(interface_)) {
					count++;
				}
			}
			if(count < interfaces.length) {
				return true;
			}
		}
		return false;
	}
	
	// 要忽略的接口, 因为不能根据这些接口去生成动态代理, 所以要过滤掉, 防止生成动态代理时出现异常
	private static final Class<?>[] ignoreInterfaces = {
			Serializable.class, Cloneable.class, Closeable.class
	};
	private boolean isIgnore(Class<?> interface_) {
		for (Class<?> ii : ignoreInterfaces) {
			if(ii == interface_) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 
	 * @param originObject
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable 
	 */
	private Object coreInvoke(Object originObject, Method method, Object[] args) throws Throwable {
		if(!proxyBean.before_(originObject, method, args))
			return method.invoke(originObject, args);
		
		Object result = null;
		try {
			if(logger.isDebugEnabled()) 
				logger.debug("执行[{}]类的[{}]代理方法", originObject.getClass().getName(), method.getName());
			
			result = method.invoke(originObject, args);
			result = proxyBean.after_(originObject, method, args, result);
		} catch (Throwable e) {
			proxyBean.exception_(originObject, method, args, e);
		}finally {
			proxyBean.finally_(originObject, method, args);
		}
		if(logger.isDebugEnabled()) 
			logger.debug("执行[{}]类的[{}]方法, 返回结果为[{}]", originObject.getClass().getName(), method.getName(), result);
		return result;
	}
}
