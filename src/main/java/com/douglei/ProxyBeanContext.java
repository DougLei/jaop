package com.douglei;

import java.util.HashMap;
import java.util.Map;

import com.douglei.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class ProxyBeanContext {
	private static final Map<String, ProxyBean> PROXY_BEAN_MAP = new HashMap<String, ProxyBean>();
	
	// ---------------------------------------------------------------------------------------
	// interceptor
	// ---------------------------------------------------------------------------------------
	public static <T> T getProxy(Class<T> clz) {
		return getProxy(clz, ConstructorUtil.newInstance(clz));
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Object object) {
		return (T) getProxy(object.getClass(), object);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> clz, Object object) {
		String clzName = clz.getName();
		ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
		if(pw == null) {
			ProxyBeanFactory pbf = new ProxyBeanFactory();
			pbf.createProxy(clz, object);
			pw = pbf.getProxyWrapper();
			PROXY_BEAN_MAP.put(clzName, pw);
		}
		return (T) pw.getProxy();
	}

	// ---------------------------------------------------------------------------------------
	// interceptor
	// ---------------------------------------------------------------------------------------
	public static void addInterceptor(Object object, ProxyInterceptor proxyInterceptor) {
		addInterceptor(object.getClass(), proxyInterceptor);
	}
	
	public static void addInterceptor(Class<?> clz, ProxyInterceptor proxyInterceptor) {
		String clzName = clz.getName();
		ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
		if(pw == null) {
			throw new NotProxyBeanException(clzName);
		}
		pw.addProxyInterceptor(proxyInterceptor);
	}
	
	public static void removeInterceptor(Object object, ProxyInterceptor proxyInterceptor) {
		removeInterceptor(object.getClass(), proxyInterceptor);
	}
	
	public static void removeInterceptor(Class<?> clz, ProxyInterceptor proxyInterceptor) {
		String clzName = clz.getName();
		ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
		if(pw == null) {
			throw new NotProxyBeanException(clzName);
		}
		pw.addProxyInterceptor(proxyInterceptor);
	}
}
