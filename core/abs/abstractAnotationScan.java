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
 * list存放所有class的路径
 * 
 * 自定义方法： 继承并重写 differAnnotation（）得到有对应的annotation的class instenceBean（）实例化bean
 * 
 * 使用方法：new出对象 调用doscan方法 参入的参数为路径 一般为‘bin’
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

	// 检查注解 是否是需要处理的bean
	public void searchCompentBean() {

		for (int i = 0; i < list.size(); i++) {
			try {
				String path = ((File) list.get(i)).getPath();
				String clazz = path.substring(path.indexOf("com"))
						.replace(File.separatorChar, '.').replace(".class", "");
				Class c = Class.forName(clazz);
				// (1)这里先进行class的实例化，并放入map中
				differAnnotation(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		differFieldsAnnotation();
	}

	// 遍历所有文件夹下的文件，剔除不是以。java结尾的文件
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

	// 处理类上的注解
	public abstract void differAnnotation(Class clazz);

	// 处理属性上的和租借
	public abstract void differFieldsAnnotation();

	public abstract void dosomthing(Object object);// 在differAnnotation中运行
													// ，可以实现属性的注入，待扩展

}
