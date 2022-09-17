package utilities;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.cucumber.java.Scenario;

public class BaseSteps {

    public void takeFailScreenShot(Scenario scenario, String platformName) {
//    	System.out.println(scenario.getStatus());
//    	System.out.println(scenario.getUri());
//    	System.out.println(scenario.getLine());
//    	System.out.println(scenario.getSourceTagNames());
//    	System.out.println(scenario.getName());
    	
    	
		if (scenario.isFailed()) { // || scenario.getStatus().toString().equals("SKIPPED")
			if (platformName.toLowerCase().equals("android")) {
				takeScreenshot(((AndroidDriver<AndroidElement>)ThreadLocalDriver.getAndroidTLDriver()[0]), scenario, true);
			} else if (platformName.toLowerCase().equals("ios")) {
				takeScreenshot(((IOSDriver<IOSElement>)ThreadLocalDriver.getiOSTLDriver()[0]), scenario, true);
			} else if (platformName.toLowerCase().equals("mobile")) {
				takeScreenshot(ThreadLocalDriver.getWebTLDriver(), scenario, true);
			} else if (platformName.toLowerCase().equals("web")) {
				takeScreenshot(ThreadLocalDriver.getWebTLDriver(), scenario, false);
			}
		}
    }
    
    protected void takeScreenshot(Object driver, Scenario scenario, boolean is_mobile) {
		if (is_mobile)
			scenario.attach(scale(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), 375, 667), "image/png",
					"Ekran Görüntüsü");
		else
			scenario.attach(scale(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), 1280, 720), "image/png",
					"Ekran Görüntüsü");
	}
    
	public byte[] scale(byte[] fileData, int width, int height) {
		ByteArrayInputStream in = new ByteArrayInputStream(fileData);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			BufferedImage img = ImageIO.read(in);
			if (height == 0) {
				height = (width * img.getHeight()) / img.getWidth();
			}
			if (width == 0) {
				width = (height * img.getWidth()) / img.getHeight();
			}
			Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);
			ImageIO.write(imageBuff, "jpg", buffer);
		} catch (Exception e) {
		}
		return buffer.toByteArray();
	}
	
	public void releaseDevice(String[] deviceInfo, Scenario scenario) {
		try {
			Path path = Paths.get(System.getProperty("user.dir") + "//src//test//resources//devices//"
					+ System.getProperty("platformName") + "//" + deviceInfo[1] + ".property");
			Charset charset = StandardCharsets.UTF_8;
			String content;
			content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("true", "false");
			Files.write(path, content.getBytes(charset));
			System.out.println("\nFINISHED: \n  > SCENARIO = "+scenario.getName()+ "\n  > DEVICE = " + deviceInfo[1] + " teste baslamaya hazir.\n\n");
		} catch (IOException e) {
			System.err.println("telefon kilitlendi");
			e.printStackTrace();
		}
	}
}

