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

	// (1)��һ�����Ƚ����еĶ���ʵ����
	@Override
	public void differAnnotation(Class clazz) {
		Annotation[] annotations = clazz.getAnnotations();

		if (annotations.length <= 0)
			return;
		System.out.println("���class��������" + clazz.getName());
		for (Annotation annotation : annotations) {
			if (annotation.annotationType() == Compent.class) {// ˵�������compent
				try {// ��1�����ж�����ǲ���һ�����
					String name = ((Compent) annotation).name();
					Object object = clazz.newInstance();
					dosomthing(object);
					compents.put(name, object);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (annotation.annotationType() == Url.class) {// ˵�������compent
				// ��1�����ж�����ǲ���һ�����
				String name = ((Url) annotation).url();
				System.out.println("װ����bean   class--------- " + name);
				classes.put(name, clazz);
			}

		}
	}

	/****
	 * (1)ȡ��map�е�bean���Ѿ�ʵ����Ϊobject (2)���ν�������ע��
	 * ****/
	@Override
	public void differFieldsAnnotation() {
		Set keyset = compents.keySet();
		Iterator iterator = keyset.iterator();
		while (iterator.hasNext()) {
			String name = (String) iterator.next();
			log.info("��ȡ�����������    " + name);
			Object o = compents.get(name);
			Class clazz = o.getClass();
			log.info("��ȡ���Ķ����class��" + clazz.getName());
			Field[] fields = clazz.getFields();
			if (fields.length == 0)
				continue;

			log.info(clazz.getName() + "��������ע��");
			for (Field f : fields) {
				Annotation a = f.getAnnotation(NeedResource.class);
				if (a != null) {
					String needResourceName = ((NeedResource) a).name();
					log.info("��Ҫ����������" + needResourceName);
					Object o1 = compents.get(needResourceName);
					if (o1 == null)
						log.info("û�л�ȡ����Ҫ������");

					String methodName = "set"
							+ StringUtil.first2UpCase(f.getName());
					log.log(Priority.INFO, "ͨ��invoke" + methodName + "ע��");
					try {

						PropertyDescriptor pd = new PropertyDescriptor(
								f.getName(), clazz);
						Method m = pd.getWriteMethod();// ������Ե�set����
						System.out.println("ע��ķ���������" + m.getName());
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
