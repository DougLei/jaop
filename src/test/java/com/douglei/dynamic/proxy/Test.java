package com.douglei.dynamic.proxy;

import com.douglei.ProxyBeanContext;
import com.douglei.dynamic.proxy.jdk.ServiceImpl;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		ProxyBeanContext.createProxyBean(ServiceImpl.class, new LogProxy(ServiceImpl.class.getDeclaredMethod("add")));
		ProxyBeanContext.getProxy(ServiceImpl.class).add();
	}
}
