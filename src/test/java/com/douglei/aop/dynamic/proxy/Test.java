package com.douglei.aop.dynamic.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.douglei.aop.ProxyBeanContext;
import com.douglei.aop.dynamic.proxy.jdk.ServiceImpl;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
		List<Method> list = new ArrayList<Method>();
		list.add(ServiceImpl.class.getDeclaredMethod("add"));
		list.add(ServiceImpl.class.getDeclaredMethod("delete"));
		
		
		
		ProxyBeanContext.createProxyBean(ServiceImpl.class, new LogProxy(list));
		ProxyBeanContext.getProxy(ServiceImpl.class).add();
	}
}
