package utilities;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ThreadLocalDriver {
	static String[] DeviceInfo;
	private static final ThreadLocal<WebDriver> webTlDriver = new ThreadLocal<>();
	private static final ThreadLocal<AndroidDriver<AndroidElement>> androidTlDriver = new ThreadLocal<>();
	private static final ThreadLocal<IOSDriver<IOSElement>> iosTlDriver = new ThreadLocal<>();

	public static synchronized void setWebTLDriver(WebDriver driver) {
		webTlDriver.set(driver);
	}

	public static synchronized WebDriver getWebTLDriver() {
		return webTlDriver.get();
	}

	public static synchronized void setAndroidTLDriver(AndroidDriver<AndroidElement> driver, String[] deviceInfo) {
		androidTlDriver.set(driver);
		DeviceInfo = deviceInfo;
	}

	public static synchronized Object[] getAndroidTLDriver() {
		Object[] obj = new Object[2];
		obj[0] = androidTlDriver.get();
		obj[1] = DeviceInfo;
		return obj;
	}

	public static synchronized void setiOSTLDriver(IOSDriver<IOSElement> driver, String[] deviceInfo) {
		iosTlDriver.set(driver);
		DeviceInfo = deviceInfo;
	}

	public static synchronized Object[] getiOSTLDriver() {
		Object[] obj = new Object[2];
		obj[0] = iosTlDriver.get();
		obj[1] = DeviceInfo;
		return obj;
	}
}
