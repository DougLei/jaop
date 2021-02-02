package com.douglei.aop;

/**
 * 重复的Proxy异常
 * @author DougLei
 */
class RepeatedProxyException extends RuntimeException{
	private static final long serialVersionUID = 7804207408960519257L;

	public RepeatedProxyException(String clazz) {
		super("["+clazz+"]已经存在代理对象");
	}
}
