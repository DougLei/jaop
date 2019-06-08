package com.douglei;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DougLei
 */
public class ProxyBean {
	private Object object;// 被代理的目标对象
	private Object proxy;// 代理对象
	private List<ProxyInterceptor> interceptors;// 代理拦截器链
	
	public ProxyBean(Object object, Object proxy) {
		this.object = object;
		this.proxy = proxy;
	}
	
	public boolean before(Method method, Object[] args) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethod().equals(method) && !interceptor.before(method, args)) {
					return false;
				}
			}
		}
		return true;
	}

	public Object after(Method method, Object result) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethod().equals(method)) {
					result = interceptor.after(result);
				}
			}
		}
		return result;
	}

	public Object exception(Method method, Object result, Throwable t) {
		if(interceptors != null) {
			for (ProxyInterceptor interceptor : interceptors) {
				if(interceptor.getMethod().equals(method)) {
					result = interceptor.exception(result, t);
				}
			}
		}
		return result;
	}
	
	public void addProxyInterceptor(ProxyInterceptor proxyInterceptor) {
		if(interceptors == null) {
			interceptors = new ArrayList<ProxyInterceptor>();
		}else if(interceptors.contains(proxyInterceptor)) {
			throw new RepeatedProxyInterceptorException("["+object.getClass().getName()+"]对象已经存在["+proxyInterceptor.getClass().getName()+"]拦截器");
		}
		interceptors.add(proxyInterceptor);
	}
	
	public void removeInterceptor(ProxyInterceptor proxyInterceptor) {
		if(interceptors != null) {
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
}
