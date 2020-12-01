package com.douglei.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.reflect.ConstructorUtil;

/**
 * 代理bean的容器
 * @author DougLei
 */
public class ProxyBeanContainer {
	private static final Logger logger = LoggerFactory.getLogger(ProxyBeanContainer.class);
	private static Map<Class<?>, ProxyBean> PROXY_BEAN_CONTAINER = new HashMap<Class<?>, ProxyBean>();
	
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
	// 创建代理Bean, 返回创建的代理对象, 并将创建的代理bean添加到PROXY_BEAN_CONTAINER中
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
	 * 创建代理对象, 并将创建的代理bean添加到PROXY_BEAN_CONTAINER中
	 * @param clz
	 * @param object
	 * @return 创建的代理bean
	 */
	private static ProxyBean createAndAddProxy(Class<?> clz, Object object) {
		if(PROXY_BEAN_CONTAINER.containsKey(clz)) {
			throw new RepeatedProxyException(clz.getName());
		}
		ProxyBean proxyBean = createProxy(clz, object);
		PROXY_BEAN_CONTAINER.put(clz, proxyBean);
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
		return PROXY_BEAN_CONTAINER.get(clz);
	}
	
	/**
	 * 获取代理对象
	 * @param clz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getProxy(Class<T> clz) {
		ProxyBean pw = PROXY_BEAN_CONTAINER.get(clz);
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
//			ProxyBean pw = PROXY_BEAN_CONTAINER.get(clzName);
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
		if(PROXY_BEAN_CONTAINER != null) {
			if(PROXY_BEAN_CONTAINER.size() > 0) {
				logger.info("[{}]被销毁, 数量为[{}]", ProxyBeanContainer.class.getName(), PROXY_BEAN_CONTAINER.size());
				if(logger.isDebugEnabled() && PROXY_BEAN_CONTAINER.size() > 0) {
					Set<Entry<Class<?>, ProxyBean>> entries = PROXY_BEAN_CONTAINER.entrySet();
					entries.forEach(entry -> {
						logger.debug("销毁Class=[{}]的ProxyBean=[{}]", entry.getKey().getName(), entry.getValue());
					});
				}
				PROXY_BEAN_CONTAINER.clear();
			}
			PROXY_BEAN_CONTAINER = null;
		}
	}
}
