package com.douglei.aop.dynamic.proxy;

import java.util.ArrayList;
import java.util.List;

import com.douglei.aop.ProxyBeanContext;
import com.douglei.aop.ProxyMethod;
import com.douglei.aop.dynamic.proxy.jdk.ServiceImpl;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		
		List<ProxyMethod> list = new ArrayList<ProxyMethod>();
		list.add(new ProxyMethod(ServiceImpl.class.getDeclaredMethod("add")));
		list.add(new ProxyMethod(ServiceImpl.class.getDeclaredMethod("delete")));
		
		
		
		ProxyBeanContext.createProxy(ServiceImpl.class, new LogProxy(list));
		ProxyBeanContext.getProxy(ServiceImpl.class).add();
	}
}
