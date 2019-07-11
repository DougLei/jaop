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
	
	boolean before_(Object obj, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				// 第一是要判断该方法是否需要被增强
				// 第二是要判断before_预处理是否返回true, 即预处理是否正常
				// 满足这两个条件, 即可对该方法进行代理增强
				if(!((interceptor.getMethods() == null || interceptor.getMethods().contains(method)) && interceptor.before_(obj, method, args))) {
					return false;
				}
			}
		}
		return true;
	}

	Object after_(Object obj, Method method, Object[] args, Object result) throws Throwable {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null || interceptor.getMethods().contains(method)) {
					result = interceptor.after_(obj, method, args, result);
				}
			}
		}
		return result;
	}

	void exception_(Object obj, Method method, Object[] args, Throwable t) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null || interceptor.getMethods().contains(method)) {
					interceptor.exception_(obj, method, args, t);
				}else {
					t.printStackTrace();// 没有增强的方法, 如果出现异常, 这里就直接打印出来
				}
			}
		}
	}
	
	void finally_(Object obj, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null || interceptor.getMethods().contains(method)) {
					interceptor.finally_(obj, method, args);
				}
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
	
//	public void removeInterceptor(ProxyInterceptor proxyInterceptor) {
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
}
