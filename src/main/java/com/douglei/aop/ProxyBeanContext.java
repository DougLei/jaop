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
	/**
	 * 代理bean的容器
	 */
	private static Map<Class<?>, ProxyBean> PROXY_BEAN_MAP = new HashMap<Class<?>, ProxyBean>(16);
	
	// ---------------------------------------------------------------------------------------
	// 创建代理Bean, 返回创建的代理对象
	// ---------------------------------------------------------------------------------------
	public static ProxyBean createProxy(Class<?> clz, ProxyInterceptor... interceptors) {
		return createProxy(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static ProxyBean createProxy(Object object, ProxyInterceptor... interceptors) {
		return createProxy(object.getClass(), object, interceptors);
	}
	
	public static ProxyBean createProxy(Class<?> clz, Object object, ProxyInterceptor... interceptors) {
		ProxyBean proxyBean = createProxy(clz, object);
		addInterceptor(proxyBean, interceptors);
		return proxyBean;
	}
	
	public static ProxyBean createProxy(Class<?> clz, List<ProxyInterceptor> interceptors) {
		return createProxy(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static ProxyBean createProxy(Object object, List<ProxyInterceptor> interceptors) {
		return createProxy(object.getClass(), object, interceptors);
	}
	
	public static ProxyBean createProxy(Class<?> clz, Object object, List<ProxyInterceptor> interceptors) {
		ProxyBean proxyBean = createProxy(clz, object);
		addInterceptor(proxyBean, interceptors);
		return proxyBean;
	}
	
	// ---------------------------------------------------------------------------------------
	// 创建代理Bean, 返回创建的代理对象, 并将创建的代理bean添加到PROXY_BEAN_MAP中
	// ---------------------------------------------------------------------------------------
	public static ProxyBean createAndAddProxy(Class<?> clz, ProxyInterceptor... interceptors) {
		return createAndAddProxy(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static ProxyBean createAndAddProxy(Object object, ProxyInterceptor... interceptors) {
		return createAndAddProxy(object.getClass(), object, interceptors);
	}
	
	public static ProxyBean createAndAddProxy(Class<?> clz, Object object, ProxyInterceptor... interceptors) {
		ProxyBean proxyBean = createAndAddProxy(clz, object);
		addInterceptor(proxyBean, interceptors);
		return proxyBean;
	}
	
	public static ProxyBean createAndAddProxy(Class<?> clz, List<ProxyInterceptor> interceptors) {
		return createAndAddProxy(clz, ConstructorUtil.newInstance(clz), interceptors);
	}
	
	public static ProxyBean createAndAddProxy(Object object, List<ProxyInterceptor> interceptors) {
		return createAndAddProxy(object.getClass(), object, interceptors);
	}
	
	public static ProxyBean createAndAddProxy(Class<?> clz, Object object, List<ProxyInterceptor> interceptors) {
		ProxyBean proxyBean = createAndAddProxy(clz, object);
		addInterceptor(proxyBean, interceptors);
		return proxyBean;
	}
	
	/**
	 * 创建代理对象
	 * @param clz
	 * @param object
	 * @return 创建的代理bean
	 */
	private static ProxyBean createProxy(Class<?> clz, Object object) {
		ProxyBeanFactory pbf = new ProxyBeanFactory();
		pbf.createProxy(clz, object);
		return pbf.getProxyBean();
	}
	
	/**
	 * 创建代理对象, 并将创建的代理bean添加到PROXY_BEAN_MAP中
	 * @param clz
	 * @param object
	 * @return 创建的代理bean
	 */
	private static ProxyBean createAndAddProxy(Class<?> clz, Object object) {
		if(PROXY_BEAN_MAP.containsKey(clz)) {
			throw new RepeatedProxyException(clz.getName());
		}
		ProxyBean proxyBean = createProxy(clz, object);
		PROXY_BEAN_MAP.put(clz, proxyBean);
		return proxyBean;
	}
	
	// ---------------------------------------------------------------------------------------
	// 获取代理对象
	// ---------------------------------------------------------------------------------------
	/**
	 * 获取ProxyBean包装对象
	 * @param clz
	 * @return
	 */
	public static ProxyBean getProxyBean(Class<?> clz) {
		return PROXY_BEAN_MAP.get(clz);
	}
	
	/**
	 * 获取代理对象
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> clz) {
		ProxyBean pw = PROXY_BEAN_MAP.get(clz);
		if(pw == null) {
			throw new NotExistsProxyBeanException(clz.getName());
		}
		return (T) pw.getProxy();
	}

	// ---------------------------------------------------------------------------------------
	// 添加/删除 interceptor
	// ---------------------------------------------------------------------------------------
	private static void addInterceptor(ProxyBean proxyBean, ProxyInterceptor... interceptors) {
		if(proxyBean == null) {
			throw new NullPointerException("proxyBean不能为空");
		}
		if(interceptors == null || interceptors.length == 0) {
			throw new NullPointerException("至少添加一个代理拦截器["+ProxyInterceptor.class.getName()+"]");
		}
		for (ProxyInterceptor interceptor : interceptors) {
			proxyBean.addInterceptor(interceptor);
		}
	}
	
	private static void addInterceptor(ProxyBean proxyBean, List<ProxyInterceptor> interceptors) {
		if(proxyBean == null) {
			throw new NullPointerException("proxyBean不能为空");
		}
		if(interceptors == null || interceptors.size() == 0) {
			throw new NullPointerException("至少添加一个代理拦截器["+ProxyInterceptor.class.getName()+"]");
		}
		proxyBean.setInterceptors(interceptors);
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
	// destroy
	// ---------------------------------------------------------------------------------------
	/**
	 * 销毁容器
	 */
	public static void destroy() {
		PROXY_BEAN_MAP.clear();
		PROXY_BEAN_MAP = null;
	}
}
