package utilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public class DesiredCapabilitiesUtil {
	public DesiredCapabilities getAndroidCapabilities(String udid, String deviceName, String platformVersion,
			String appPackage, String appActivity) {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("udid", udid);
		desiredCapabilities.setCapability("deviceName", deviceName);
		desiredCapabilities.setCapability("platformVersion", platformVersion);
		desiredCapabilities.setCapability("platformName", "Android");
		desiredCapabilities.setCapability("automationName", "UiAutomator2");
		desiredCapabilities.setCapability("appPackage", appPackage);
		desiredCapabilities.setCapability("appActivity", appActivity);
//        desiredCapabilities.setCapability("skipUnlock", "true");
		desiredCapabilities.setCapability("noReset", "true");
		desiredCapabilities.setCapability("clearSystemFiles", false);
		desiredCapabilities.setCapability("autoGrantPermissions",true);  //test
//		desiredCapabilities.setCapability("dontStopAppOnReset",true);
		return desiredCapabilities;

//		if (scenario.getName().contains("App Kurulum")) {
//		capability.setCapability(MobileCapabilityType.NO_RESET, false);
//		capability.setCapability("fastReset", true);
//		capability.setCapability("clearSystemFiles", true);
//	}
//	else {
//		capability.setCapability(MobileCapabilityType.NO_RESET, true);
//		capability.setCapability("fastReset", false);
//		capability.setCapability("clearSystemFiles", false);
//	}
	}

	public DesiredCapabilities getiOSCapabilities(String udid, String deviceName, String platformVersion,
			String bundle) {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("udid", udid);
		desiredCapabilities.setCapability("deviceName", deviceName);
		desiredCapabilities.setCapability("platformVersion", platformVersion);
		desiredCapabilities.setCapability("bundleId", bundle);
		desiredCapabilities.setCapability("platformName", "iOS");
		desiredCapabilities.setCapability("automationName", "XCUITest");
//		desiredCapabilities.setCapability("fastReset", true);
//		desiredCapabilities.setCapability("noReset", "false");
		desiredCapabilities.setCapability("newCommandTimeout", 120);
		desiredCapabilities.setCapability("clearSystemFiles", true);
		desiredCapabilities.setCapability("autoAcceptAlerts", true);
		desiredCapabilities.setCapability("locationServicesAuthorized", true);
		desiredCapabilities.setCapability("xcodeSigningId", "7E33PZGZ87");
		desiredCapabilities.setCapability("xcodeOrgId", "iPhone Developer");
//		desiredCapabilities.setCapability("usePrebuiltWDA", "true");
//		desiredCapabilities.setCapability("derivedDataPath", "/Users/"+ System.getProperty("user.name") +"/Library/Developer/Xcode/DerivedData/WebDriverAgent-ciegwgvxzxdrqthilmrmczmqvrgu");
		    
		
//		desiredCapabilities.setCapability("instrumentApp", false);
//		desiredCapabilities.setCapability("useNewWDA", true);
//		desiredCapabilities.setCapability("simpleIsVisibleCheck", false);
//		desiredCapabilities.setCapability("–session-override",true);
		return desiredCapabilities;
	}

}

//