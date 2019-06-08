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
	// 创建代理Bean
	// ---------------------------------------------------------------------------------------
	public static void createProxyBean(Class<?> clz, ProxyInterceptor... interceptors) {
		createProxyBean(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static void createProxyBean(Object object, ProxyInterceptor... interceptors) {
		createProxyBean(object.getClass(), object, interceptors);
	}
	
	public static void createProxyBean(Class<?> clz, Object object, ProxyInterceptor... interceptors) {
		String clzName = clz.getName();
		if(PROXY_BEAN_MAP.containsKey(clzName)) {
			throw new RepeatedProxyException(clzName);
		}
		ProxyBeanFactory pbf = new ProxyBeanFactory();
		pbf.createProxy(clz, object);
		PROXY_BEAN_MAP.put(clzName, pbf.getProxyBean());
		
		addInterceptor(clz, interceptors);
	}
	
	// ---------------------------------------------------------------------------------------
	// 获取代理对象
	// ---------------------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Object object) {
		return (T) getProxy(object.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> clz) {
		String clzName = clz.getName();
		ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
		if(pw == null) {
			throw new NotExistsProxyBeanException(clzName);
		}
		return (T) pw.getProxy();
	}

	// ---------------------------------------------------------------------------------------
	// 添加/删除 interceptor
	// ---------------------------------------------------------------------------------------
	private static void addInterceptor(Class<?> clz, ProxyInterceptor... interceptors) {
		if(interceptors != null && interceptors.length > 0) {
			String clzName = clz.getName();
			ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
			if(pw == null) {
				throw new NotExistsProxyBeanException(clzName);
			}
			for (ProxyInterceptor interceptor : interceptors) {
				pw.addInterceptor(interceptor);
			}
		}
	}
	
//	public static void removeInterceptor(Object object, ProxyInterceptor... interceptors) {
//		removeInterceptor(object.getClass(), interceptors);
//	}
//	
//	public static void removeInterceptor(Class<?> clz, ProxyInterceptor... interceptors) {
//		if(interceptors != null && interceptors.length > 0) {
//			String clzName = clz.getName();
//			ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
//			if(pw == null) {
//				throw new NotExistsProxyBeanException(clzName);
//			}
//			for (ProxyInterceptor interceptor : interceptors) {
//				pw.removeInterceptor(interceptor);
//			}
//		}
//	}
}