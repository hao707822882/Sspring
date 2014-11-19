package com.dangdang.springbate.util.stringUtil;

public class StringUtil {

	public static String first2UpCase(String name) {
		String head = name.substring(0, 1).toUpperCase();
		String back = name.substring(1);
		return head + back;
	}

	
	public static void main(String[] args) {
		System.out.println(first2UpCase("zhu"));
	}
}
