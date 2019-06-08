package com.douglei.dynamic.proxy;

import com.douglei.ProxyBeanContext;
import com.douglei.dynamic.proxy.cglib.Service;

public class Test {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Service service = ProxyBeanContext.getProxy(Service.class);
		
		ProxyBeanContext.addInterceptor(Service.class, new LogProxy(Service.class, Service.class.getDeclaredMethod("add")));
		
		
		service.add();
		
		
		
	}
}
