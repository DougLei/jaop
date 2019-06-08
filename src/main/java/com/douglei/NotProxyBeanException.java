package com.douglei;

/**
 * 不存在ProxyBean元素异常
 * @author DougLei
 */
class NotProxyBeanException extends RuntimeException{
	private static final long serialVersionUID = 1040681875086550857L;

	public NotProxyBeanException(String clzName) {
		super("不存在className=["+clzName+"]的ProxyBean");
	}
}
