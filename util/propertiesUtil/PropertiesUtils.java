package com.dangdang.springbate.util.propertiesUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties loadProperty(File pFile) {
		Properties p = null;
		try {
			System.out.println(pFile.getAbsolutePath());
			FileInputStream pInStream = new FileInputStream(pFile);
			p = new Properties();
			p.load(pInStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}

}
