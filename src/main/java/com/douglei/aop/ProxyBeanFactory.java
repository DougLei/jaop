package com.douglei.aop;

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
				if(IgnoreInterfacesProperties.isIgnore(interface_)) {
					count++;
				}
			}
			if(count < interfaces.length) {
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
	 */
	private Object coreInvoke(Object originObject, Method method, Object[] args) {
		boolean intercepted = false;
		Object result = null;
		try {
			intercepted = proxyBean.before_(originObject, method, args);
			if(logger.isDebugEnabled()) {
				logger.debug("执行[{}]类的[{}]方法, {}代理", originObject.getClass().getName(), method.getName(), intercepted?"进行了":"未进行");
			}
			result = method.invoke(originObject, args);
			if(intercepted) {
				result = proxyBean.after_(originObject, method, args, result);
			}
		} catch (Throwable e) {
			if(intercepted) {
				proxyBean.exception_(originObject, method, args, e);
			}else {
				e.printStackTrace();// 没有被代理, 则遇到异常, 直接抛出
			}
		}finally {
			if(intercepted) {
				proxyBean.finally_(originObject, method, args);
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("执行[{}]类的[{}]方法, 返回结果为[{}]", originObject.getClass().getName(), method.getName(), result);
		}
		return result;
	}
}
