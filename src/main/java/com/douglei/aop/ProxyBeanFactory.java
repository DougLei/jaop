package com.douglei.aop;

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
		if(isCreateDynamicProxyByInterface(clz.getInterfaces())) {
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
	
	/**
	 * 是否根据接口创建动态代理
	 * @param interfaces
	 * @return
	 */
	private boolean isCreateDynamicProxyByInterface(Class<?>[] interfaces) {
		if(interfaces.length > 0) {
			byte count = 0;
			for (Class<?> interface_ : interfaces) {
				for (Class<?> ii : IGNORE_INTERFACES) {
					if(ii == interface_) {
						count++;
						break;
					}
				}
			}
			if(count < interfaces.length) {
				return true;
			}
		}
		return false;
	}
	
	// 要忽略的接口, 没有办法根据这些接口创建动态代理
	// TODO 随时可能需要补充
	private static final Class<?>[] IGNORE_INTERFACES = {
			Serializable.class, 
			Cloneable.class
	};
	

	/**
	 * 
	 * @param originObject
	 * @param method
	 * @param args
	 * @return
	 */
	private Object coreInvoke(Object originObject, Method method, Object[] args) {
		boolean beProxy = false;
		Object result = null;
		try {
			beProxy = proxyBean.before_(originObject, method, args);
			if(logger.isDebugEnabled()) {
				logger.debug("执行[{}]类的[{}]方法, {}代理", originObject.getClass().getName(), method.getName(), beProxy?"进行了":"未进行");
			}
			result = method.invoke(originObject, args);
			if(beProxy) {
				result = proxyBean.after_(originObject, method, args, result);
			}
		} catch (Throwable e) {
			if(beProxy) {
				proxyBean.exception_(originObject, method, args, e);
			}else {
				e.printStackTrace();
			}
		}finally {
			if(beProxy) {
				proxyBean.finally_(originObject, method, args);
			}
		}
		if(logger.isDebugEnabled()) {
			logger.debug("执行[{}]类的[{}]方法, 返回结果为[{}]", originObject.getClass().getName(), method.getName(), result);
		}
		return result;
	}
}
