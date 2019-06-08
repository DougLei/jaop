package com.douglei.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
class ProxyBean {
	private Object object;// 被代理的目标对象
	private Object proxy;// 代理对象
	private List<ProxyInterceptor> interceptors;// 代理拦截器链
	
	public ProxyBean(Object object, Object proxy) {
		this.object = object;
		this.proxy = proxy;
	}
	
	public boolean before(Object obj, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if((interceptor.getMethods() == null ||  interceptor.getMethods().contains(method)) && !interceptor.before(obj, method, args)) {
					return false;
				}
			}
		}
		return true;
	}

	public Object after(Object obj, Method method, Object[] args, Object result) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null ||  interceptor.getMethods().contains(method)) {
					result = interceptor.after(obj, method, args, result);
				}
			}
		}
		return result;
	}

	public void exception(Object obj, Method method, Object[] args, Throwable t) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null || interceptor.getMethods().contains(method)) {
					interceptor.exception(obj, method, args, t);
				}
			}
		}
	}
	
	public void finally_(Object obj, Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethods() == null ||  interceptor.getMethods().contains(method)) {
					interceptor.finally_(obj, method, args);
				}
			}
		}
	}
	
	public void addInterceptor(ProxyInterceptor proxyInterceptor) {
		if(interceptors == null) {
			interceptors = new ArrayList<ProxyInterceptor>();
		}else if(interceptors.contains(proxyInterceptor)) {
			throw new RepeatedProxyInterceptorException("["+object.getClass().getName()+"]对象已经存在["+proxyInterceptor.getClass().getName()+"]拦截器");
		}
		interceptors.add(proxyInterceptor);
	}
	
	public void removeInterceptor(ProxyInterceptor proxyInterceptor) {
		if(interceptors != null && interceptors.size() > 0) {
			interceptors.remove(proxyInterceptor);
		}
	}
	
	public Object getProxy() {
		return proxy;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public List<ProxyInterceptor> getInterceptors() {
		return interceptors;
	}
	public void setInterceptors(List<ProxyInterceptor> interceptors) {
		this.interceptors = interceptors;
	}
}
