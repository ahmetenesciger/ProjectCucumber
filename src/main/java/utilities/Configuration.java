package utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class Configuration {

	private static Configuration instance;
	private Properties configProps = new Properties();

	private String device_udid;
	private String device_name;
	private String platform_version;
	private String apk_folder;
	private String apk_file;
	private String app_package;
	private String app_activity;
    private String ipa_bundle_id;
    private String ipa_folder;
    private String ipa_file;

    public static Configuration getInstance() {
        if (instance == null) {
            createInstance();
        }
        return instance;
    }

    private static synchronized void createInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
    }
    
	private Configuration() {
		InputStream is = null;
		Reader reader = null;
		try {			
			if (System.getProperty("suiteXmlFile") == null) {
			is = ClassLoader.getSystemResourceAsStream("debug.properties");		
			// debug properties
			reader = new InputStreamReader(is, "UTF-8");
			configProps.load(reader);
			System.setProperty("platformName", configProps.getProperty("platformName"));
			System.setProperty("appName", configProps.getProperty("appName"));
			System.setProperty("deviceName", configProps.getProperty("deviceName"));
			System.setProperty("environment", configProps.getProperty("environment"));  
			// Services
			System.setProperty("requestCount", configProps.getProperty("requestCount"));
			System.setProperty("interval", configProps.getProperty("interval")); 
			}
    		System.out.println("Operating System: "+ System.getProperty("os.name"));
			System.out.println("Platform Name: " + System.getProperty("platformName"));			
			System.out.println("Application Name: " + System.getProperty("appName"));
			System.out.println("Device Name: " + System.getProperty("deviceName"));
			System.out.println("Tag Name: " + System.getProperty("cucumber.filter.tags"));
			System.out.println("Envrionment(optional*): " + System.getProperty("environment"));
//			System.out.println("---------------------------");
//			System.out.println("baseURL: " + System.getProperty("baseURL"));
//			System.out.println("requestCount: " + System.getProperty("requestCount"));
//			System.out.println("interval(ms): " + System.getProperty("interval"));
//			System.out.println(" ==>");
			
				///////////
			if (System.getProperty("deviceName") != null) {
				if (!System.getProperty("deviceName").equals("nodevice")) {
					if (!System.getProperty("deviceName").contains(",")) {
					// device properties
					is = ClassLoader.getSystemResourceAsStream("devices/" + System.getProperty("platformName") + "/"
							+ System.getProperty("deviceName") + ".property");
					reader = new InputStreamReader(is, "UTF-8");
					configProps.load(reader);
					this.device_udid = configProps.getProperty("device.udid");
					this.device_name = configProps.getProperty("device.name");
					this.platform_version = configProps.getProperty("platform.version");
					}
					else {
						this.device_name = System.getProperty("deviceName");
						 }

					// app properties
					is = ClassLoader.getSystemResourceAsStream("apps/" + System.getProperty("platformName") + "/"
							+ System.getProperty("appName") + ".property");
					reader = new InputStreamReader(is, "UTF-8");
					configProps.load(reader);
					this.app_package = configProps.getProperty("app.package");
					this.app_activity = configProps.getProperty("app.activity");

					// ios
					this.ipa_bundle_id = configProps.getProperty("ipa.bundle_id");
					this.ipa_folder = configProps.getProperty("ipa.folder");
					this.ipa_file = configProps.getProperty("ipa.file");

				}
			}
		}
		 catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String device_udid() {
		return device_udid;
	}

	public String device_name() {
		return device_name;
	}

	public String platform_version() {
		return platform_version;
	}

	public String apk_folder() {
		return apk_folder;
	}

	public String apk_file() {
		return apk_file;
	}

	public String app_package() {
		return app_package;
	}

	public String app_activity() {
		return app_activity;
	}
	
	public String ipa_bundle_id() {
		return ipa_bundle_id;
	}
	public String ipa_folder() {
		return ipa_folder;
	}
	public String ipa_file() {
		return ipa_file;
	}
}