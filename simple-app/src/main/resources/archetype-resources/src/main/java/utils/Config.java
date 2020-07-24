#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private static Config config = null;

	private static Properties properties = null;
	private final String filename = "config.properties";


	private Config() throws IOException {

		properties = new Properties();
		properties.load(new FileReader(new File(".").getCanonicalPath() + File.separator + filename));
		System.out.println("Config loaded...");
	}

	private static void createConfig() {
		if (config == null) {
			try {
				config = new Config();
			} catch (IOException e) {
				System.out.println("Config file missing " + e.getLocalizedMessage());
				System.exit(1);
			}
		}
	}

	public static String getValue(String key) {
		createConfig();
		return properties.getProperty(key);
	}
}