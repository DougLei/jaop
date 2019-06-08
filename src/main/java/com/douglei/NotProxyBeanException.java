package com.douglei;

/**
 * 不存在ProxyBean元素异常
 * @author DougLei
 */
public class NotProxyBeanException extends RuntimeException{
	private static final long serialVersionUID = 1869372122916295062L;

	public NotProxyBeanException(String clzName) {
		super("不存在className=["+clzName+"]的ProxyBean");
	}
}
