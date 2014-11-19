package com.dangdang.springbate.util.stackUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Stack;

public class StackUtil {

	public static void foreachStack(Stack stack) {
		Iterator it = stack.iterator();
		while (it.hasNext()) {
			String value = (String) it.next();
			System.out.println(value);
		}
	}

	/***
	 * 
	 * @is 当is设置为true的时候 不是使用值拼接，而是使用占位符？
	 * 
	 * ***/
	public static String foreachStack4String(Stack stack, Boolean is,
			Boolean ishave) {
		Iterator it = stack.iterator();
		StringBuffer buffer = new StringBuffer();
		if (ishave)
			buffer.append("(");

		while (it.hasNext()) {
			if (is) {
				it.next();
				buffer.append("?" + ",");
			} else {
				String value = (String) it.next();
				buffer.append(value + ",");
			}
		}

		String t = buffer.toString();
		String tt = t.substring(0, t.length() - 1);
		if (ishave)
			tt += ")";
		return tt;
	}

	public static String forEachStack4Where(Stack valuename) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("where ");
		Iterator it = valuename.iterator();
		while (it.hasNext()) {
			buffer.append((String) it.next() + "=? and ");
		}
		String tmp = buffer.toString();
		return tmp.substring(0, buffer.length() - 4) + ";";

	}

	public static PreparedStatement insertValue(PreparedStatement p, Stack value) {
		Iterator it = value.iterator();
		int count = 1;
		while (it.hasNext()) {
			String v = (String) it.next();
			System.out.println("值是是-----"+v);
			try {
				p.setObject(count, v);
				count++;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	
}
