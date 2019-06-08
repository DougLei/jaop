package com.douglei.dynamic.proxy;

import com.douglei.ProxyBeanContext;
import com.douglei.dynamic.proxy.jdk.IService;
import com.douglei.dynamic.proxy.jdk.ServiceImpl;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		IService service = ProxyBeanContext.getProxy(ServiceImpl.class);
		
		ProxyBeanContext.addInterceptor(ServiceImpl.class, new LogProxy(ServiceImpl.class, ServiceImpl.class.getDeclaredMethod("add")));
		
		
		service.add();
		
		
		
	}
}
