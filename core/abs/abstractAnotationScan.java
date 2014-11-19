package com.dangdang.springbate.core.abs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.springbate.core.interf.Pool;
import com.dangdang.springbate.core.interf.Scan;

/****
 * 
 * list�������class��·��
 * 
 * �Զ��巽���� �̳в���д differAnnotation�����õ��ж�Ӧ��annotation��class instenceBean����ʵ����bean
 * 
 * ʹ�÷�����new������ ����doscan���� ����Ĳ���Ϊ·�� һ��Ϊ��bin��
 * 
 * ****/
@SuppressWarnings("all")
public abstract class abstractAnotationScan implements Scan, Pool {
	List list;

	@Override
	public void doScan(URL path) {
		System.out.println(path);
		list = new ArrayList<>();
		System.out.println(path.toExternalForm());
		try {
			listFlie(new File(path.toURI()).getAbsolutePath());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		if (list.size() > 0)
			searchCompentBean();

	}

	// ���ע�� �Ƿ�����Ҫ�����bean
	public void searchCompentBean() {

		for (int i = 0; i < list.size(); i++) {
			try {
				String path = ((File) list.get(i)).getPath();
				String clazz = path.substring(path.indexOf("com"))
						.replace(File.separatorChar, '.').replace(".class", "");
				Class c = Class.forName(clazz);
				// (1)�����Ƚ���class��ʵ������������map��
				differAnnotation(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		differFieldsAnnotation();
	}

	// ���������ļ����µ��ļ����޳������ԡ�java��β���ļ�
	private List listFlie(String path) throws IOException, URISyntaxException {
		File root = new File(path);
		File[] child = root.listFiles();
		for (File file : child) {
			if (file.isDirectory()) {
				listFlie(file.getAbsolutePath());
			} else {
				if (file.getName().contains(".class")) {
					list.add(file);
				}
			}
		}
		return list;
	}

	// �������ϵ�ע��
	public abstract void differAnnotation(Class clazz);

	// ���������ϵĺ����
	public abstract void differFieldsAnnotation();

	public abstract void dosomthing(Object object);// ��differAnnotation������
													// ������ʵ�����Ե�ע�룬����չ

}
