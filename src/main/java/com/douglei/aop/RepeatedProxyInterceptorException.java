package com.douglei.aop;

/**
 * 重复的ProxyInterceptor异常
 * @author DougLei
 */
class RepeatedProxyInterceptorException extends RuntimeException{
	private static final long serialVersionUID = -1249096064116356373L;

	public RepeatedProxyInterceptorException(String message) {
		super(message);
	}
}
