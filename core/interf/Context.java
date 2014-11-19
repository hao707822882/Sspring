package com.dangdang.springbate.core.interf;

import com.dangdang.springbate.core.execption.NullBeanExexption;

public interface Context {

	Object getBean(String name) throws NullBeanExexption;

}
