package com.douglei;

/**
 * 重复的ProxyInterceptor异常
 * @author DougLei
 */
public class RepeatedProxyInterceptorException extends RuntimeException{
	private static final long serialVersionUID = 8609239988768757647L;

	public RepeatedProxyInterceptorException(String message) {
		super(message);
	}
}
