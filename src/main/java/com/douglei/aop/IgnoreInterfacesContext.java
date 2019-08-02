package com.douglei.aop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.douglei.tools.utils.CloseUtil;
import com.douglei.tools.utils.datatype.converter.DataTypeConvertException;
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
		
		InputStream in = IgnoreInterfacesContext.class.getClassLoader().getResourceAsStream("jaop.ignore.interfaces.properties");
		if(in != null) {
			InputStreamReader isr = null;
			BufferedReader br = null;
			try {
				isr = new InputStreamReader(in);
				br = new BufferedReader(isr);
				while(br.ready()) {
					ignoreInterfaces_.add(ClassLoadUtil.loadClass(br.readLine()));
				}
			} catch (IOException e) {
				throw new DataTypeConvertException("在读取jaop.ignore.interfaces.properties配置文件时出现异常", e);
			} finally {
				CloseUtil.closeIO(br, isr, in);
			}
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
