package com.dangdang.springbate;

import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dangdang.springbate.core.abs.abstractAnotationScan;
import com.dangdang.springbate.core.execption.NullBeanExexption;
import com.dangdang.springbate.core.impl.CompentScanPool;
import com.dangdang.springbate.core.interf.Context;

/***
 * 
 * Ĭ���� ͨ��ע��ע�⣬û��ƪ���I�ļ�
 * ����Ĭ����ֱ��ʹ��ע��ɨ�裬��build·���ݹ�����
 * 
 ***/
public class SpringContext implements Context {

	Logger log = Logger.getLogger(getClass());
	private abstractAnotationScan compentScanPool;
	private Map pool;
	private Map classPool;

	public SpringContext(URL path) {
		compentScanPool = new CompentScanPool();
		compentScanPool.doScan(path);
		pool = (Map) compentScanPool.getPool();
		classPool = (Map) compentScanPool.getClassPool();
	}

	@Override
	public Object getBean(String name) throws NullBeanExexption {
		Object obj = pool.get(name);
		if (obj == null)
			throw new NullBeanExexption(name);
		else
			return obj;
	}

	public Map getPool() {
		return pool;
	}

	public Map getClassPool() {
		return classPool;
	}
}
