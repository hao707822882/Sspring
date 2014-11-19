package com.dangdang.springbate.core.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import com.dangdang.springbate.annotation.Compent;
import com.dangdang.springbate.annotation.NeedResource;
import com.dangdang.springbate.annotation.Url;
import com.dangdang.springbate.core.abs.abstractAnotationScan;
import com.dangdang.springbate.core.interf.Pool;
import com.dangdang.springbate.util.stringUtil.StringUtil;

@SuppressWarnings("all")
public class CompentScanPool extends abstractAnotationScan implements Pool {
	private Logger log = Logger.getLogger(CompentScanPool.class);
	public static Map compents = new HashMap();
	public static Map classes = new HashMap();

	// (1)第一步，先将所有的对象实例化
	@Override
	public void differAnnotation(Class clazz) {
		Annotation[] annotations = clazz.getAnnotations();

		if (annotations.length <= 0)
			return;
		System.out.println("这个class的名字是" + clazz.getName());
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == Compent.class) {// 说明这个是compent
				try {// （1）先判断这个是不是一个组件
					String name = ((Compent) annotation).name();
					Object object = clazz.newInstance();
					dosomthing(object);
					compents.put(name, object);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (annotation.annotationType() == Url.class) {// 说明这个是compent
				// （1）先判断这个是不是一个组件
				String name = ((Url) annotation).url();
				System.out.println("装载了bean   class--------- " + name);
				classes.put(name, clazz);
			}

		}
	}

	/****
	 * (1)取出map中的bean，已经实例化为object (2)依次进行属性注入
	 * ****/
	@Override
	public void differFieldsAnnotation() {
		Set keyset = compents.keySet();
		Iterator iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			log.info("获取的组件名字是    " + name);
			Object o = compents.get(name);
			Class clazz = o.getClass();
			log.info("获取到的对象的class是" + clazz.getName());
			Field[] fields = clazz.getFields();
			if (fields.length == 0)
				continue;

			log.info(clazz.getName() + "进行属性注入");
			for (Field f : fields) {
				Annotation a = f.getAnnotation(NeedResource.class);
				if (a != null) {
					String needResourceName = ((NeedResource) a).name();
					log.info("需要的属性名字" + needResourceName);
					Object o1 = compents.get(needResourceName);
					if (o1 == null)
						log.info("没有获取到想要的属性");

					String methodName = "set"
							+ StringUtil.first2UpCase(f.getName());
					log.log(Priority.INFO, "通过invoke" + methodName + "注入");
					try {

						PropertyDescriptor pd = new PropertyDescriptor(
								f.getName(), clazz);
						Method m = pd.getWriteMethod();// 获得属性的set方法
						System.out.println("注入的方法名字是" + m.getName());
						m.invoke(o, o1);

					} catch (SecurityException | IllegalAccessException
							| IllegalArgumentException | IntrospectionException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	@Override
	public void dosomthing(Object object) {

	}

	@Override
	public Object getPool() {
		return compents;
	}

	@Override
	public Object getClassPool() {
		return classes;
	}

}
