package com.douglei.aop;

/**
 * 不存在ProxyBean元素异常
 * @author DougLei
 */
class NotExistsProxyBeanException extends RuntimeException{
	private static final long serialVersionUID = 5938310508335960872L;

	public NotExistsProxyBeanException(String clzName) {
		super("不存在className=["+clzName+"]的ProxyBean");
	}
}
