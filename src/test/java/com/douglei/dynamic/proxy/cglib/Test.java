package com.douglei.dynamic.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class Test {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Service.class);
		enhancer.setCallback(new ServiceProxy());
		
		Service service = (Service) enhancer.create();
		service.add();
		
//		Service service = new ProxyWrapperFactory().createProxy(Service.class, new Service());
//		service.add();
	}
}
