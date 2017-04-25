package xianming.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties authProp;
	
	public static Properties getAuthProp(){
		if(authProp==null){
			try {
				authProp = new Properties();
				authProp.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("auth.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return authProp;
	}
}
