package com.dangdang.springbate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*****
 * 
 * 组件annotation 如果设置了@compent 则会被自动实例化 标志annoytation
 * 
 * ****/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Compent {
	public String name() default "";
}
