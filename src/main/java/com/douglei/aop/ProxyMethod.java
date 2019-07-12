package com.douglei.aop;

import java.lang.reflect.Method;

import com.douglei.tools.utils.reflect.ValidationUtil;

/**
 * 被代理的方法
 * 包装了 java.lang.reflect.Method
 * @author DougLei
 */
public final class ProxyMethod {
	private Method method;

	public ProxyMethod(Method method) {
		this.method = method;
	}
	
	/**
	 * 比较方法
	 * @param interceptedMethod 
	 * @return
	 */
	boolean equalMethod(Method interceptedMethod) {
		// 如果被拦截的方法来自接口, 要还要判断一下定义时要拦截的方法所在的类, 是否实现了这个接口 
		if(interceptedMethod.getDeclaringClass().isInterface() && ValidationUtil.isImplementInterface(interceptedMethod.getDeclaringClass(), method.getDeclaringClass().getInterfaces())) {
			if(method.getName().equals(interceptedMethod.getName()) 
					&& method.getReturnType().equals(interceptedMethod.getReturnType())
					&& equalParameterTypes(method.getParameterTypes(), interceptedMethod.getParameterTypes())) {
				return true;
			}
		}else {
			return method.equals(interceptedMethod);// 被拦截的方法来自类, 则直接判断
		}
		return false;
	}
	
	/**
	 * 比较参数类型
	 * @param parameterTypes
	 * @param otherParameterTypes
	 * @return
	 */
	private boolean equalParameterTypes(Class<?>[] parameterTypes, Class<?>[] otherParameterTypes) {
		 if (parameterTypes.length == otherParameterTypes.length) {
            for (int i = 0; i < parameterTypes.length; i++) {
                if (parameterTypes[i] != otherParameterTypes[i]) {
                	return false;
                }
            }
            return true;
        }
        return false;
	}
	

	@Override
	public String toString() {
		return method.toString();
	}
}
