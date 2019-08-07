package com.douglei.aop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.douglei.tools.instances.reader.ResourcesReader;
import com.douglei.tools.utils.reflect.ClassLoadUtil;

/**
 * 要忽略的接口集合容器
 * @author DougLei
 */
public class IgnoreInterfacesContext {
	private static final Class<?>[] ignoreInterfaces;
	static {
		List<Class<?>> ignoreInterfaces_ = new ArrayList<Class<?>>();
		ignoreInterfaces_.add(Serializable.class);
		ignoreInterfaces_.add(Cloneable.class);
		
		ResourcesReader reader = new ResourcesReader("ignore.interfaces.properties");
		while(reader.ready()) {
			ignoreInterfaces_.add(ClassLoadUtil.loadClass(reader.readLine()));
		}
		ignoreInterfaces = ignoreInterfaces_.toArray(new Class<?>[ignoreInterfaces_.size()]);
	}
	
	/**
	 * 指定的接口是否需要被忽略
	 * @param interface_
	 * @return
	 */
	public static boolean isIgnore(Class<?> interface_) {
		for (Class<?> ii : ignoreInterfaces) {
			if(ii == interface_) {
				return true;
			}
		}
		return false;
	}
}
