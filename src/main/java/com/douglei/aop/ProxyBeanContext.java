package com.douglei.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 
 * @author DougLei
 */
public class ProxyBeanContext {
	private static Map<String, ProxyBean> PROXY_BEAN_MAP = new HashMap<String, ProxyBean>();
	
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
		createProxyBean(clz, object);
		addInterceptor(clz, interceptors);
	}
	
	public static void createProxyBean(Class<?> clz, List<ProxyInterceptor> interceptors) {
		createProxyBean(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static void createProxyBean(Object object, List<ProxyInterceptor> interceptors) {
		createProxyBean(object.getClass(), object, interceptors);
	}
	
	public static void createProxyBean(Class<?> clz, Object object, List<ProxyInterceptor> interceptors) {
		createProxyBean(clz, object);
		addInterceptor(clz, interceptors);
	}
	
	private static void createProxyBean(Class<?> clz, Object object) {
		String clzName = clz.getName();
		if(PROXY_BEAN_MAP.containsKey(clzName)) {
			throw new RepeatedProxyException(clzName);
		}
		ProxyBeanFactory pbf = new ProxyBeanFactory();
		pbf.createProxy(clz, object);
		PROXY_BEAN_MAP.put(clzName, pbf.getProxyBean());
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
	
	private static void addInterceptor(Class<?> clz, List<ProxyInterceptor> interceptors) {
		if(interceptors != null && interceptors.size() > 0) {
			String clzName = clz.getName();
			ProxyBean pw = PROXY_BEAN_MAP.get(clzName);
			if(pw == null) {
				throw new NotExistsProxyBeanException(clzName);
			}
			pw.setInterceptors(interceptors);
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
	
	// ---------------------------------------------------------------------------------------
	// 
	// ---------------------------------------------------------------------------------------
	/**
	 * 是否存在代理bean
	 * @return
	 */
	public static boolean existsProxyBeans() {
		return PROXY_BEAN_MAP.size() > 0;
	}
}
