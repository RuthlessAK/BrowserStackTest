package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
		public static String getKey(String key) {
			Properties properties = new Properties();
			try {
				File f = new File("src/test/resources/config.properties");
				FileInputStream fis = new FileInputStream(f);
				properties.load(fis);
			} catch (IOException e) {
				throw new RuntimeException("Failed to load config.properties", e);
			}
			return properties.getProperty(key);
		}
	}
