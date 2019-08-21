package com.douglei.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public final class ProxyBean {
	private Object originObject;// 被代理的源目标对象
	private Object proxy;// 代理对象
	private List<ProxyInterceptor> interceptors;// 代理拦截器链
	
	ProxyBean(Object originObject, Object proxy) {
		this.originObject = originObject;
		this.proxy = proxy;
	}
	
	boolean before_(Object originObject, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				// 第一是要判断该方法是否需要被增强
				// 第二是要判断before_预处理是否返回true, 即预处理是否正常
				// 满足这两个条件, 即可对该方法进行代理增强
				if(!((interceptor.proxyAllMethod() || interceptor.isInterceptMethod(method)) && interceptor.before_(originObject, method, args))) {
					return false;
				}
			}
		}
		return true;
	}

	Object after_(Object originObject, Method method, Object[] args, Object result) throws Throwable {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				result = interceptor.after_(originObject, method, args, result);
			}
		}
		return result;
	}

	void exception_(Object originObject, Method method, Object[] args, Throwable t) throws Throwable {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				interceptor.exception_(originObject, method, args, t);
			}
		}
	}
	
	void finally_(Object originObject, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				interceptor.finally_(originObject, method, args);
			}
		}
	}
	
	void addInterceptor(ProxyInterceptor proxyInterceptor) {
		if(interceptors == null) {
			interceptors = new ArrayList<ProxyInterceptor>();
		}else if(interceptors.contains(proxyInterceptor)) {
			throw new RepeatedProxyInterceptorException("["+originObject.getClass().getName()+"]对象已经存在["+proxyInterceptor.getClass().getName()+"]拦截器");
		}
		interceptors.add(proxyInterceptor);
	}
	
//	void removeInterceptor(ProxyInterceptor proxyInterceptor) {
//		if(interceptors != null && interceptors.size() > 0) {
//			interceptors.remove(proxyInterceptor);
//		}
//	}
	
	public Object getOriginObject() {
		return originObject;
	}
	public Object getProxy() {
		return proxy;
	}
	List<ProxyInterceptor> getInterceptors() {
		return interceptors;
	}
	void setInterceptors(List<ProxyInterceptor> interceptors) {
		this.interceptors = interceptors;
	}

	@Override
	public String toString() {
		return "ProxyBean [originObject=" + originObject + ", proxy=" + proxy + ", interceptors=" + interceptors + "]";
	}
}
