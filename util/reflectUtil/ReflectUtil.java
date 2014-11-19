package com.dangdang.springbate.util.reflectUtil;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("all")
public class ReflectUtil {

	public static Method getMethodByName(Class clazz, String name)
			throws NoSuchMethodException, SecurityException {
		return clazz.getMethod(name, null);
	}

	public static void invokeSet(Field f, Class clazz, Object o1, Object value) {

		Method m = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
			m = pd.getWriteMethod();// 获得属性的set方法
			System.out.println("注入属性"+m.getName());
			m.invoke(o1, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}

	}

	public static Object invokeGet(Field f, Class clazz, Object o1) {

		Method m = null;
		Object value = null;
		try {
			PropertyDescriptor pd = new PropertyDescriptor(f.getName(), clazz);
			m = pd.getReadMethod();// 获得属性的set方法
			value = m.invoke(o1);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IntrospectionException e) {
			e.printStackTrace();
		}
		return value;
	}
}
