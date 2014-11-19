package com.dangdang.springbate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****
 * 
 * ���annotation ���������@compent ��ᱻ�Զ�ʵ���� ��־annoytation
 * 
 * ****/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Compent {
	public String name() default "";
}
