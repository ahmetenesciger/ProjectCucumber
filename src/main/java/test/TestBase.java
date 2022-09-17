package test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import aProject.mainClasss;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utilities.Configuration;
import utilities.DesiredCapabilitiesUtil;
import utilities.ThreadLocalDriver;

public class TestBase {
	private final DesiredCapabilitiesUtil desiredCapabilitiesUtil = new DesiredCapabilitiesUtil();
	protected Configuration configurationGet;
	protected String appiumServer;
	protected String seleniumServer;
	static int threadCount = 0;
	private Lock lock = new ReentrantLock();
	boolean localhostEnabled = true;
	
    @BeforeMethod
//    @Parameters({ "udid", "deviceName", "platformVersion" })
//    public void setup(String udid, String deviceName, String platformVersion) throws IOException {
    public void setup() throws IOException {
//    	try {
    		aProject.mainClasss asdsa = new aProject.mainClasss();
    		asdsa.T1();
//    			configurationGet = Configuration.getInstance();
//    			if(System.getProperty("os.name").toLowerCase().contains("windows") && !System.getProperty("cucumber.filter.tags").toLowerCase().contains("sanity")) {
//    				System.setProperty("webdriver.chrome.driver","C://chromedriver.exe");
//    			}
//    			else {
//    			System.setProperty("webdriver.chrome.driver","//Users//" + System.getProperty("user.name") + "//Documents//chromedriver");
//    			}
//    			
//    			
//				if (System.getProperty("suiteXmlFile") != null) {
//					localhostEnabled = false;
//				}
//    			
//    			int portAppium =4723;
//    			if (localhostEnabled) {
//    				appiumServer = "http://localhost:"+portAppium+"/wd/hub";	//localE	
//    			}
//    			else
//    			{
//					if (System.getProperty("platformName").equals("android"))
//						portAppium = 4722;
//					else
//						portAppium = 4724;
//
//					appiumServer = "http://192.168.12.231:" + portAppium + "/wd/hub"; // remotEE
//					seleniumServer = "http://172.16.50.20:4445/wd/hub"; // remotEE
//					try {
//						if (System.getProperty("cucumber.filter.tags").contains("sanityOne"))
//							seleniumServer = "http://192.168.12.231:4723/wd/hub"; // remotEE
//					} catch (Exception ex) {
//						seleniumServer = "http://192.168.12.231:4723/wd/hub";
//					}
//    			}
//    			
////    			deviceSelection();	
//
//    			Random rand = new Random();
//    			String[] deviceInfo;
//    			String app_package;
//				String app_activity;
//				String ipa_bundle_id;
//				DesiredCapabilities caps;
//    			switch (System.getProperty("platformName")) {
//    			case "mobile":
//    				switch (System.getProperty("deviceName")) {
//    				case "nodevice":
//    					// belki ilerde appName'e göre browser seçilebilir. şimdilik chrome.
//
//    					DesiredCapabilities capability = DesiredCapabilities.htmlUnit();
//    					Map<String, Object> mobileEmulation = new HashMap<>();
//    					mobileEmulation.put("deviceName", "Nexus 5");
//    					ChromeOptions chromeOptions = new ChromeOptions();
//    					chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
//    					capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
//    					lock.lock();
//    					
//						if (localhostEnabled)
//							ThreadLocalDriver.setWebTLDriver(new ChromeDriver(capability));
//						else {
//							System.out.println("selenium server : " + seleniumServer);
//							ThreadLocalDriver.setWebTLDriver(new RemoteWebDriver(new URL(seleniumServer), capability));
//						}
//						lock.unlock();				
//    			        ThreadLocalDriver.getWebTLDriver().manage().window().setPosition(new Point(75, 0));
//    					ThreadLocalDriver.getWebTLDriver().manage().window().setSize(new Dimension(375, 960));
//    					break;
//    				default:
//    					switch (System.getProperty("appName")) {//artık mobile'dir, device adı verilmiştir ve appName = browser olmuştur.
//    					case "chrome":
//    						// On Device(android)
//    						capability = new DesiredCapabilities();
//    						capability.setCapability(MobileCapabilityType.UDID, "33a4175b0a057ece");
//    						capability.setCapability("platformName", "Android");
//    						capability.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
//    						capability.setCapability(CapabilityType.VERSION, "10");
//    						System.out.println(appiumServer);
//    						ThreadLocalDriver.setWebTLDriver(new RemoteWebDriver(new URL(appiumServer), capability));
//    						break;
//    					case "safari":
//    						// On Device(ios)
////    	   			DesiredCapabilities capabilities = new DesiredCapabilities();
////    	   			capabilities.setCapability(MobileCapabilityType.UDID, "00008020-001615201A28002E");
////    	   			capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
////    	   			capabilities.setCapability(CapabilityType.BROWSER_NAME, "Safari");
//////    	   		capabilities.setCapability(CapabilityType.VERSION, "10");
////    				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
////    				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14");
////    				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
////    				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
////
////    						capability = new DesiredCapabilities();
////    						capability.setCapability(MobileCapabilityType.UDID, "00008020-001615201A28002E");
////    						capability.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
////    						capability.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
////    						capability.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
////    						capability.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
////    						ThreadLocalDriver.setWebTLDriver(new AppiumDriver(new URL(appiumServer), capability));
//    						break;
//
//    					default:
//    						break;
//    					}
//    					break;
//    				}
//
//    				break;
//    			case "web":
//    				switch (System.getProperty("appName")) {
//    				case "chrome":
//    					ThreadLocalDriver.setWebTLDriver(new ChromeDriver(chromeOptions()));
//    					ThreadLocalDriver.getWebTLDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//    					ThreadLocalDriver.getWebTLDriver().manage().window().maximize();
////                      driver.manage().window().setPosition(new Point(0, 0));
////                      driver.manage().window().setSize(new Dimension(1920, 1080));
////                      System.out.println("Window width: " + driver.manage().window().getSize().getWidth());
////                      System.out.println("Window height: " + driver.manage().window().getSize().getHeight());
//    					break;
//    				case "firefox":
//    					System.setProperty("webdriver.gecko.driver",
//    							System.getProperty("user.dir") + "//drivers//geckoDriver//geckodriver.exe");
//    					ThreadLocalDriver.setWebTLDriver(new FirefoxDriver(firefoxOptions()));
//    					ThreadLocalDriver.getWebTLDriver().manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
//    					ThreadLocalDriver.getWebTLDriver().manage().window().maximize();
//    					break;
//    				case "ie":
//    					System.setProperty("webdriver.ie.driver",
//    							System.getProperty("user.dir") + "//drivers//ieDriver//IEDriverServer_x32.exe");
//    					ThreadLocalDriver.setWebTLDriver(new InternetExplorerDriver(ieCapabilities()));
//    					ThreadLocalDriver.getWebTLDriver().manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
//    					ThreadLocalDriver.getWebTLDriver().manage().window().maximize();
//    					break;
//    				case "opera":
//
//    					break;
//
//    				default:
//    					break;
//    				}
//
//					break;
//				case "android":
//					Thread.sleep(rand.nextInt(1000));
//					if (threadCount == 0) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(200) + 300);
//					}
//					else if (threadCount == 1) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(300) + 600);
//						
//					}
//					else if (threadCount == 2) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(600) + 900);					
//					}
//					else if (threadCount == 3) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(900) + 1200);					
//					}
//					threadCount = -1;
//					deviceInfo = findSlave();
//					app_package = configurationGet.app_package();
//					app_activity = configurationGet.app_activity();
//					caps = desiredCapabilitiesUtil.getAndroidCapabilities(deviceInfo[0],
//							deviceInfo[1], deviceInfo[2], app_package, app_activity);
//					lock.lock();
//					System.out.println("\n\n >>>>>>>>>> cihaz teste başlıyor.. : " + deviceInfo[1] + " <<<<<<<<<<");
//					ThreadLocalDriver.setAndroidTLDriver(new AndroidDriver<AndroidElement>(new URL(appiumServer), caps),
//							deviceInfo);
//					lock.unlock();
//					break;
//    			case "ios":
//					Thread.sleep(rand.nextInt(1000));
//					if (threadCount == 0) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(200) + 300);
//					}
//					else if (threadCount == 1) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(300) + 600);						
//					}
//					else if (threadCount == 2) {
//						threadCount++;
//						Thread.sleep(rand.nextInt(600) + 900);			
//					}
//					threadCount = -1;
//					deviceInfo = findSlave();
//					ipa_bundle_id = configurationGet.ipa_bundle_id();
//					caps = desiredCapabilitiesUtil.getiOSCapabilities(deviceInfo[0],
//							deviceInfo[1], deviceInfo[2], ipa_bundle_id);
//					lock.lock();
//					System.out.println("\n\n >>>>>>>>>> cihaz teste başlıyor.. : " + deviceInfo[1] + " <<<<<<<<<<");
//					for (int i = 0; i < 5; i++) {
//						try {
//							ThreadLocalDriver.setiOSTLDriver(new IOSDriver<IOSElement>(new URL(appiumServer), caps),
//									deviceInfo);
//							break;
//						} catch (Exception e) {
//							System.out.println("Driver başlatılamadı. 5 saniye sonra tekrar deniyorum...");
//							TimeUnit.SECONDS.sleep(5);
//						}
//					}
//					lock.unlock();
//    				break;
//
//    			default:
//    				break;
//    			}
//
//    		} catch (Exception e) {
//    			// TODO Auto-generated catch block
//    			if (lock.tryLock()) {
//					lock.unlock();
//				}		
//    			e.printStackTrace();
//    		}
    	}
    	
    	//local device reserve
		private String[] findSlave() throws IOException {
			String[] deviceProperties = new String[3];
			String[] deviceNames = System.getProperty("deviceName").split(",");
			for (String devicename : deviceNames) {
				String fileName = System.getProperty("user.dir") + "//src//test//resources//devices//"
						+ System.getProperty("platformName") + "//" + devicename + ".property";
				File file = new File(fileName);
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains("device.udid"))
						deviceProperties[0] = line.substring(line.indexOf("=")+1).trim();
					else if (line.contains("device.name"))
						deviceProperties[1] = line.substring(line.indexOf("=")+1).trim();
					else if (line.contains("platform.version"))
						deviceProperties[2] = line.substring(line.indexOf("=")+1).trim();
					else if (line.contains("active")) {
						break;
						}
				}
				br.close();
//				if (!Boolean.parseBoolean(line.substring(line.indexOf("=")).trim())) {
				if (line.substring(line.indexOf("=")+1).trim().equals("false")) {
				
					Path path = Paths.get(fileName);
					Charset charset = StandardCharsets.UTF_8;

					String content = new String(Files.readAllBytes(path), charset);
					content = content.replaceAll("false", "true");
					Files.write(path, content.getBytes(charset));
					return deviceProperties;
				}
			}
			Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//devices//"
					+ System.getProperty("platformName") + "//" + deviceNames[deviceNames.length-1] + ".property");
			Charset charset = StandardCharsets.UTF_8;

			String content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("false", "true");
			Files.write(path, content.getBytes(charset));
			return deviceProperties;
		}
		
		protected void releaseAllDevices(String platform) {
			File folder = new File(System.getProperty("user.dir") + "//src//test//resources//devices//" + platform);
			File[] listOfFiles = folder.listFiles();
			for (File devicename : listOfFiles) {
				Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//devices//" + platform + "//"
						+ devicename.getName());
				Charset charset = StandardCharsets.UTF_8;
				try {
					String content;
					content = new String(Files.readAllBytes(path), charset);
					content = content.replaceAll("true", "false");
					Files.write(path, content.getBytes(charset));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
//		protected void createReport() {
//			/////
//			try {
//				Charset charset = StandardCharsets.UTF_8;
//				String buildFolderPath= System.getenv("JENKINS_HOME");
//				String jobNamePath = buildFolderPath + "\\jobs\\" + System.getenv("JOB_NAME");
//				String logFile = new String(Files.readAllBytes(Paths.get(jobNamePath + "\\builds\\" + System.getenv("BUILD_NUMBER") + "\\log")), charset);
//				String reportFilePath = System.getenv("JENKINS_HOME") + "\\jobs\\" + System.getenv("JOB_NAME") + "\\buildReport.txt";
//				String report = "report=" + logFile.substring(logFile.indexOf("https://reports.cucumber.io/reports/"),logFile.indexOf("This report will self-destruct")).replace("│","").trim();
//				Path path = Paths.get(reportFilePath);
//				Files.newByteChannel(path).close();
//				Files.write(path, report.getBytes(charset));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			////
//		}

//	private void deviceSelection(String device_name) {
//		// TODO Auto-generated method stub
//		System.out.println("seçilen cihaz kullanımda ==> " + device_name);
//	}

	@AfterMethod
	public synchronized void teardown(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE)
			System.out.println("HATA MESAJI : " + result.getThrowable().getMessage() + " \n\n");

		try {
			if (ThreadLocalDriver.getWebTLDriver() != null) {
				ThreadLocalDriver.getWebTLDriver().quit();
			} 
			else if (System.getProperty("platformName").toLowerCase().equals("android")) {
				((AndroidDriver<AndroidElement>) ThreadLocalDriver.getAndroidTLDriver()[0]).quit();
//				System.out.println("android driver sorunsuz kapatildi");
			} 
			else if (System.getProperty("platformName").toLowerCase().equals("ios")) {
				((IOSDriver<IOSElement>) ThreadLocalDriver.getiOSTLDriver()[0]).terminateApp(Configuration.getInstance().ipa_bundle_id());
				((IOSDriver<IOSElement>) ThreadLocalDriver.getiOSTLDriver()[0]).quit();
//				System.out.println("ios driver sorunsuz kapatildi");
			}
		} catch (Exception ex) {
			System.out.println("!!! DRIVER KAPATILAMADI !!!");
		}
	}
    
	protected ChromeOptions chromeOptions() {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--test-type");
		chromeOptions.addArguments("--disable-popup-blocking");
		chromeOptions.addArguments("--ignore-certificate-errors");
		chromeOptions.addArguments("--disable-translate");
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--disable-notifications");
		chromeOptions.addArguments("disable-automatic-password-saving");
		chromeOptions.addArguments("allow-silent-push");
		chromeOptions.addArguments("disable-infobars");
		// (CapabilityType.ACCEPT_SSL_CERTS, true)
		// https://www.guru99.com/ssl-certificate-error-handling-selenium.html
		// https://stackoverflow.com/questions/2405714/selenium-and-https-ssl/3673404
		return chromeOptions;
	}

	private FirefoxOptions firefoxOptions() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		firefoxOptions.addArguments("--test-type");
		firefoxOptions.addArguments("--disable-popup-blocking");
		firefoxOptions.addArguments("--ignore-certificate-errors");
		firefoxOptions.addArguments("--disable-translate");
		firefoxOptions.addArguments("--start-maximized");
		firefoxOptions.addArguments("--disable-notifications");
		return firefoxOptions;
	}

	private InternetExplorerOptions ieCapabilities() {
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.setCapability("nativeEvents", false);
		options.setCapability("unexpectedAlertBehaviour", "accept");
		// options.setCapability("ignoreProtectedModeSettings", true);
		options.setCapability("disable-popup-blocking", true);
		options.setCapability("enablePersistentHover", true);
		options.setCapability("ignoreZoomSetting", true);
		return options;
	}
	
	/*
	  Map<String, String> mobileEmulation = new HashMap<>();
mobileEmulation.put("deviceName", "Pixel 2");

Map<String, Object> chromeOptions = new HashMap<>();
chromeOptions.put("mobileEmulation", mobileEmulation);

chromeOptions.put("args",
                Arrays.asList("disable-bundled-ppapi-flash",
                "disable-extensions",
                "profile-directory=Default",
                "disable-plugins-discovery",
                "--user-agent=" + userAgent));

ChromeOptions co = new ChromeOptions();
co.addArguments("mobileEmulation="+mobileEmulation);

DesiredCapabilities capabilities = DesiredCapabilities.chrome();
capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);

System.setProperty("webdriver.chrome.driver", RunConfig.CHROME_DRIVER_EXE);

driver = new ChromeDriver(capabilities);

*
*
*
Map<String, String> mobileEmulation = new HashMap<>();
mobileEmulation.put("deviceName", "Pixel 2");

Map<String, Object> chromeOptions = new HashMap<>();
chromeOptions.put("mobileEmulation", mobileEmulation);

chromeOptions.put("args",
                Arrays.asList("disable-bundled-ppapi-flash",
                "disable-extensions",
                "profile-directory=Default",
                "disable-plugins-discovery",
                "--user-agent=" + userAgent));

ChromeOptions co = new ChromeOptions();
co.addArguments("mobileEmulation="+mobileEmulation);

DesiredCapabilities capabilities = DesiredCapabilities.chrome();
capabilities.setCapability(ChromeOptions.CAPABILITY,chromeOptions);

System.setProperty("webdriver.chrome.driver", RunConfig.CHROME_DRIVER_EXE);

driver = new ChromeDriver(capabilities);
*/
}
