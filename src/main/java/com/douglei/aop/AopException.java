package com.douglei.aop;

import com.douglei.tools.RootRuntimeException;

/**
 * 
 * @author DougLei
 */
public class AopException extends RootRuntimeException {
	private static final long serialVersionUID = -5778683851351228848L;

	public AopException() {
		super();
	}

	public AopException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AopException(String message, Throwable cause) {
		super(message, cause);
	}

	public AopException(String message) {
		super(message);
	}

	public AopException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getName() {
		return "jaop";
	}
}
