package com.dangdang.springbate.util.listUtil;

import java.util.List;

public class ListUtil {

	public static void foreachList(List list) {
		for (Object object : list) {
			System.out.println((String) object);
		}
	}

}
