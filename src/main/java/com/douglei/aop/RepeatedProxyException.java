package com.douglei.aop;

/**
 * 重复的Proxy异常
 * @author DougLei
 */
class RepeatedProxyException extends RuntimeException{
	private static final long serialVersionUID = -865625707459631863L;

	public RepeatedProxyException(String clzName) {
		super("["+clzName+"]已经存在代理对象");
	}
}
