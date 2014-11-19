package com.dangdang.springbate.util.fileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public static File searchFile(String name) {
		File file = null;
		try {
			ListFileBean b = new ListFileBean();
			file = b.searchOnlyFile(new File(FileUtils.class.getClassLoader()
					.getResource("").toURI()).getAbsolutePath(), name);
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		return file;
	}

	/****
	 * 
	 * 每次使用都要创建一个新的，如果不是新的，那么list中硅油许多脏数据
	 * 
	 * ****/
	static class ListFileBean {

		List list = new ArrayList();
		File file;

		public File searchOnlyFile(String path, String searchvalue)
				throws IOException, URISyntaxException {
			System.out.println(path);
			File root = new File(path);
			File[] child = root.listFiles();

			for (File file : child) {
				if (file.isDirectory()) {
					searchOnlyFile(file.getAbsolutePath(), searchvalue);
				} else {
					if (file.getName().equals("dbConfig.properties")) {
						this.file = file;
						System.out.println("----------"+ file.getAbsolutePath());
						System.out.println("AAAAAAAAAA" + file.getName());
					}
				}
			}
			return file;
		}

		public List listFlie(String path, String searchValue)
				throws IOException, URISyntaxException {
			File root = new File(path);
			File[] child = root.listFiles();
			for (File file : child) {
				if (file.isDirectory()) {
					listFlie(file.getAbsolutePath(), searchValue);
				} else {
					if (file.getName().contains(searchValue)) {
						list.add(file);
					}
				}
			}
			return list;
		}

		public List getList() {
			return list;
		}

	}

}
