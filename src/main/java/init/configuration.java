package init;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class configuration {
	//TODO: Add correct set of abstraction.

	InputStream inputStream;
	
	public String getPythonMicroserviceUrl(){
		String propFileName = "config.properties";
		Properties prop = new Properties();
		
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String url = prop.getProperty("python_microservice_url");
		return url;
	}
	
}
