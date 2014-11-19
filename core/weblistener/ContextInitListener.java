package com.dangdang.springbate.core.weblistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dangdang.springbate.SpringContext;

public class ContextInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("context������");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("��servletContext�����springContext����");
		System.setProperty("java.awt.headless", "true");
		arg0.getServletContext().setAttribute(
				"springContext",
				new SpringContext(this.getClass().getClassLoader()
						.getResource("")));
	}

}
