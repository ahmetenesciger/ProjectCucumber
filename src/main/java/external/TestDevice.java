package external;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.connection.ConnectionState;
//import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import page.PageBaseIos;

public class TestDevice {
	Provider provider = new Provider();

	
	// *********Constructor*********
	public TestDevice() {

	}
	
	public TestDevice setAndroidLocationServiceStatus(boolean status) {
		String cmd = "";
		if (status)
			cmd = "adb shell settings put secure location_providers_allowed +network & adb shell settings put secure location_providers_allowed +gps";
		else
			cmd = "adb shell settings put secure location_providers_allowed -network & adb shell settings put secure location_providers_allowed -gps";

		try {
			new ProcessBuilder("cmd.exe", "/c", cmd).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public TestDevice resetCustomerLoginStatus(String phone_number) {
		try {
			provider.ExecuteCommand(
				"update customers set access_token = NULL where mobile_phone = '" + phone_number + "';",
				"martiDB");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public TestDevice restartApp(AppiumDriver<?> driver) {
		driver.closeApp();
		driver.launchApp();     
		return this;
	}
	
	public TestDevice resetApp(AppiumDriver<?> driver) {
		driver.resetApp();  
		return this;
	}
	
	public TestDevice setAndroidWIFIServiceStatus(AndroidDriver<AndroidElement> androidDriver, boolean status) {
		ConnectionState conStatus = androidDriver.getConnection();
		
		if (status) {
			if (!conStatus.isWiFiEnabled()) {
				androidDriver.toggleWifi();
			}
		} else {
			if (conStatus.isWiFiEnabled()) {
				androidDriver.toggleWifi();
			}
		}
		return this;
	}
	
	public TestDevice setAndroidDataServiceStatus(AndroidDriver<AndroidElement> androidDriver, boolean status) {
		ConnectionState conStatus = androidDriver.getConnection();
		
		if (status) {
			if (!conStatus.isDataEnabled()) {
				androidDriver.toggleData();
			}
		} else {
			if (conStatus.isDataEnabled()) {
				androidDriver.toggleData();
			}
		}
		return this;
	}
	
	public TestDevice setAndroidAirPlaneStatus(AndroidDriver<AndroidElement> androidDriver, boolean status) {
		ConnectionState conStatus = androidDriver.getConnection();
		
		if (status) {
			if (!conStatus.isAirplaneModeEnabled()) {
				androidDriver.toggleAirplaneMode();
			}
		} else {
			if (conStatus.isAirplaneModeEnabled()) {
				androidDriver.toggleAirplaneMode();
			}
		}
		return this;
	}
	
	protected TestDevice setAndroidBatteryLevel(AndroidDriver<AndroidElement> androidDriver, int level) {
		androidDriver.setPowerCapacity(level);
		return this;
	}
	
	public TestDevice setAndroidCamPermissionStatus(AndroidDriver<AndroidElement> androidDriver, boolean status) {
		String packageName= androidDriver.getCurrentPackage();
		 String grantCameraPermission= "adb shell pm grant " + packageName +" android.permission.CAMERA";
		 String revokeCameraPermission= "adb shell pm revoke " + packageName +" android.permission.CAMERA";
			try {
				if (status) 
					Runtime.getRuntime().exec(grantCameraPermission);
				else
					Runtime.getRuntime().exec(revokeCameraPermission);	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return this;
		}
	
	public TestDevice notify(AndroidDriver<AndroidElement> androidDriver) {
		androidDriver.openNotifications();
		List<AndroidElement> allnotifications=androidDriver.findElements(By.id("android:id/title"));

		    for (AndroidElement webElement : allnotifications) {
		        System.out.println(webElement.getText());
		        if(webElement.getText().contains("something")){
		            System.out.println("Notification found");
		            break;
		        }
		    }
		    return this;
			}
	
/////////////////////////////////////////  IOS  ///////////////////////////////////////////////
	
	
//	public TestDevice setIOSWIFIServiceStatus(IOSDriver<IOSElement> iosDriver, boolean status) {
//		//bu mantik güzelmis herseyi bunla bulup alabilirsin. guzellll
////		MobileElement element = (MobileElement) driver.findElementByAccessibilityId("SomeAccessibilityID");
////		String tagName = element.getAttribute("content-desc");
//		
//		PageBaseIos pageBaseIos = new PageBaseIos(iosDriver);
//		
//		pageBaseIos.swipe(".UIAWindow", 20, iosDriver.manage().window().getSize().height-5, 10, 10, 230);  //Asagidan yukari cekerek paneli acar
//		
//		if (status) {
//			if (pageBaseIos.getValue("#wifi-button", "value").equals("0")) {
//				pageBaseIos.click("#wifi-button");
//			}
//		} else {
//			if (pageBaseIos.getValue("#wifi-button", "value").equals("1")) {
//				pageBaseIos.click("#wifi-button");
//				if( pageBaseIos.exists("#Yakınlardaki Wi-Fi Bağlantısı Yarına Kadar Kesiliyor", 2))
//					pageBaseIos.click("#Tamam");
//			}
//		}
//		clickToHomeButton(iosDriver);
//		
//		return this;
//	}
	
//	public void setIOSDataServiceStatus(IOSDriver<IOSElement> iosDriver, boolean status) {
//		//bu mantik güzelmis herseyi bunla bulup alabilirsin. guzellll
////		MobileElement element = (MobileElement) driver.findElementByAccessibilityId("SomeAccessibilityID");
////		String tagName = element.getAttribute("content-desc");
//		
//		PageBaseIos pageBaseIos = new PageBaseIos(iosDriver);
//		
//		pageBaseIos.swipe(".UIAWindow", 20, iosDriver.manage().window().getSize().height-5, 10, 10, 230);  //Asagidan yukari cekerek paneli acar
//		
//		if (status) {
//			if (pageBaseIos.getValue("#cellular-data-button", "value").equals("0")) {
//				pageBaseIos.click("#cellular-data-button");
//			}
//		} else {
//			if (pageBaseIos.getValue("#cellular-data-button", "value").equals("1")) {
//				pageBaseIos.click("#cellular-data-button");
//			}
//		}
//		clickToHomeButton(iosDriver);
//	}
	
//	public TestDevice setIOSAirPlaneServiceStatus(IOSDriver<IOSElement> iosDriver, boolean status) {
//		//bu mantik güzelmis herseyi bunla bulup alabilirsin. guzellll
////		MobileElement element = (MobileElement) driver.findElementByAccessibilityId("SomeAccessibilityID");
////		String tagName = element.getAttribute("content-desc");
//		
//		PageBaseIos pageBaseIos = new PageBaseIos(iosDriver);
//		pageBaseIos.swipe(".UIAWindow", 20, iosDriver.manage().window().getSize().height-5, 10, 10, 230);  //Asagidan yukari cekerek paneli acar
//		if (status) {
//			if (pageBaseIos.getValue("#airplane-mode-button", "value").equals("0")) {
//				pageBaseIos.click("#airplane-mode-button");
//			}
//		} else {
//			if (pageBaseIos.getValue("#airplane-mode-button", "value").equals("1")) {
//				pageBaseIos.click("#airplane-mode-button");
//			}
//		}
//		clickToHomeButton(iosDriver);
//		return this;
//	}
	
//	public TestDevice setIOSLocationServiceStatus(IOSDriver<IOSElement> iosDriver, boolean status) {	
//		PageBaseIos pageBaseIos = new PageBaseIos(iosDriver);
//		iosDriver.runAppInBackground(Duration.ofSeconds(-1));
//		pageBaseIos.click("#Ayarlar");	
//		iosDriver.executeScript("client:client.applicationClose(\"com.apple.Preferences\");");
//		pageBaseIos.click("#Ayarlar");	
//		pageBaseIos.swipe("#Wi-Fi", 256, 650, 254, 1072, 230);
//		pageBaseIos.writeText("#Ara", "konum");
//		pageBaseIos.click("#Konum");
//		if (status) {
//			if (pageBaseIos.getValue(".UIASwitch", "value").equals("0")) {
//				pageBaseIos.click(".UIASwitch");
//			}
//		} else {
//			if (pageBaseIos.getValue(".UIASwitch", "value").equals("1")) {
//				pageBaseIos.click(".UIASwitch");
//				pageBaseIos.click("#Kapat");
//			}
//		}
//		iosDriver.runAppInBackground(Duration.ofSeconds(1));
//		return this;
//	}
//	
	public void clickToHomeButton(IOSDriver<?> iosDriver) {
		iosDriver.executeScript("client:client.deviceAction(\"Home\")");
		
	}
}