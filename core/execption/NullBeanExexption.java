package com.dangdang.springbate.core.execption;

public class NullBeanExexption extends Exception {

	public NullBeanExexption(String name) {
		super(name + "  not found in the context");
	}

}
