package page;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import utilities.SoftAssert;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.time.Duration;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static org.testng.Assert.fail;

public class PageBaseIos {
	public IOSDriver<IOSElement> iosDriver;
	public WebDriverWait wait;
	JavascriptExecutor js;
	Boolean check = false;
	int swipeCount = 5;
	int defaultImplicitWait = 15;
	protected Scenario scenario;
	private static HashMap _dynamicParameters = new HashMap<String,String>();
	protected int stepNumber;
	protected SoftAssert assertPool;
	private String softTagName = "saniiiiiityOne";

	// Constructor
	public PageBaseIos(IOSDriver<?> iosDriver) {
		this.iosDriver = (IOSDriver<IOSElement>) iosDriver;
		wait = new WebDriverWait(iosDriver, defaultImplicitWait);
		iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
	}

//===========================================================================Waiting..
	public By findElements(String pure_element) { // name
		By elementBy = null;
//		waitLoadingImage();
		if (pure_element.substring(0, 1).equals("@")) {
			elementBy = MobileBy.xpath(pure_element.substring(1));
		} else if (pure_element.substring(0, 1).equals(".")) {
			elementBy = MobileBy.className(pure_element.substring(1));
		} else if (pure_element.substring(0, 1).equals("$")) { // **/XCUIElementTypeButton[`name == "unfavorited"`]
			elementBy = MobileBy.iOSClassChain(pure_element.substring(1));
		} else if (pure_element.substring(0, 1).equals("#")) {
			elementBy = MobileBy.name(pure_element.substring(1));
		}
		return elementBy;
	}

	//set assertParameter
	public void customParameter(String value) {
		this._dynamicParameters.put(scenario.getId(), value);
	}

	//get assertParameter
	public String customParameter() {
		return _dynamicParameters.get(scenario.getId()).toString();
	}

	//================================================== wait..
	protected void wait(int second) {
		try {
			TimeUnit.SECONDS.sleep(second);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void waitMilliSec(int milli_second) {
		try {
			TimeUnit.MILLISECONDS.sleep(milli_second);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected boolean isPresent(IOSElement element)
	{
//		for (int i = 0; i < defaultImplicitWait; i++) {
//			wait(1);
//			try {
//				if (element.isDisplayed()) {
//					return true;
////				} else if (element.isEnabled()){
////					return true;
//				}
//			} catch (Exception ex) {}
//		}
//		return false;
//		try
//		{
//			wait.until(ExpectedConditions.(element));
//			return true;
//		}
//		catch (Exception e)
//		{
//			return false;
//		}
		return true;
	}
	
	protected boolean isClickable(By elementBy)
	{
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	protected boolean isClickable(IOSElement element) {
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	protected boolean isClickable(String pure_element)
	{
		try
		{
			By elementBy = findElements(pure_element);
			List<IOSElement> elements = iosDriver.findElements(elementBy);
			wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	protected boolean isClickable(String pure_element, int index)
	{
		try
		{
			By elementBy = findElements(pure_element);
			List<IOSElement> elements = iosDriver.findElements(elementBy);
			wait.until(ExpectedConditions.elementToBeClickable(elements.get(index)));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	

//	protected void waitClickable(String pure_element) {
//		By elementBy = findElements(pure_element);
//		List<IOSElement> elements = iosDriver.findElements(elementBy);
//		wait.until(ExpectedConditions.elementToBeClickable(elements.get(0)));
//	}
	
//	protected void assertPresence(String pure_element,String name) {
//		check = false;
//		By elementBy = findElements(pure_element,0);
//		//waitPresence(elementBy);
//		List<IOSElement> elements = iosDriver.findElements(elementBy);
//		for (IOSElement element : elements) {
//			if (element.getText().equals(name)) {
//				check = true;
//				//waitClickable(elementBy);
//				break;
//			}
//		}
//		Assert.assertTrue(check);
//	}
//
//	// Wait By Wrapper Method
//	protected void waitLoadingImage() {
//		try {
//			iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("In progress")));
//		} catch (Exception ex) {
//			System.out.println("bunu bi düşün");  //fail(pure_element + "bu element ekranda yok");
//		} finally {
//			iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
//		}
//	}

	// Wait By Wrapper Method
	/*
	protected void waitLoadingImage2() {
		try {
			iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("progressBar2")));
		} catch (Exception ex) {
		} finally {
			iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	*/

//===========================================================================Click
	// Click By Method
	public void click(String pure_element) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isClickable(elements.get(0)))
			elements.get(0).click();
		else
			throwFail(">>> click("+pure_element+"); fonksiyonu çalışmadı !!!");
	}
	
	// Click By Method
	protected void click(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isClickable(elements.get(index)))
			elements.get(index).click();
		else
			throwFail(">>> click("+pure_element+"); fonksiyonu çalışmadı !!!");
	}
	
	// Click By Method
	public void clickUnstable(String pure_element) {
		assertFound(pure_element);
		wait(1);
		iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int x = -1, y = -1;
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(iosDriver);
		for (int i = 0; i < 20; i++) {
			try {
				List<IOSElement> elements = iosDriver.findElements(elementBy);
				if (x == -1)
					x = elements.get(0).getLocation().getX() + 5;
				y = elements.get(0).getLocation().getY();
				break;
			} catch (Exception ex) {

			}
		}
		iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		if (x == -1 && y == -1) {
			throwFail(pure_element + " elementi bulunamadı******");
		}
		TouchAction touchAction = new TouchAction(iosDriver);
		touchAction.tap(PointOption.point(x, y)).perform();
	}
	
	// Click By Method
	public void clickUnstable(String pure_element, int index) {
		assertFound(pure_element);
		wait(1);
		iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int x = -1, y = -1;
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(iosDriver);
		for (int i = 0; i < 20; i++) {
			try {
				List<IOSElement> elements = iosDriver.findElements(elementBy);
				if (x == -1)
					x = elements.get(index).getLocation().getX() + 5;
				y = elements.get(index).getLocation().getY();
				break;
			} catch (Exception ex) {

			}
		}
		iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		if (x == -1 && y == -1) {
			throwFail(pure_element + " elementi bulunamadı******");
		}
		TouchAction touchAction = new TouchAction(iosDriver);
		touchAction.tap(PointOption.point(x, y)).perform();
	}
	
	// click to coorditane of element X soldan saga.Y yukardan aşağaı
	protected void clickToCoordinateOfElement(String pure_element, int coordinateX, int coordinateY) {
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(iosDriver);
		builder.moveToElement(iosDriver.findElement(elementBy), coordinateX, coordinateY).click().build().perform();
	}
	
	// click to coorditane of element X soldan saga.Y yukardan aşağaı
	protected void clickToCoordinateOfElement(String pure_element, int index, int coordinateX, int coordinateY) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);

		Actions builder = new Actions(iosDriver);
		builder.moveToElement(elements.get(index), coordinateX, coordinateY).click().build().perform();
	}
	


	// click to coorditane of element X soldan saga.Y yukardan aşağaı
	protected void clickToCoordinate(int x, int y) {
	     TouchAction touchAction = new TouchAction(iosDriver);
         touchAction.press(PointOption.point(x,y))
                    .waitAction(waitOptions(Duration.ofMillis(250)))
                    .release()
                    .perform();
	}




//===========================================================================Write Text
	// Write By Text
	public void writeText(String pure_element, String text) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(0))) {
			elements.get(0).clear();
			elements.get(0).sendKeys(text);
		}
		else
			throwFail(">>> writeText("+pure_element+"); fonksiyonu çalışmadı !!!");
	}

	// Write By Text
	public void writeText(String pure_element, String text, int index) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(index))) {
			elements.get(index).clear();
			elements.get(index).sendKeys(text);
		}
		else
			throwFail(">>> writeText("+pure_element+"); fonksiyonu çalışmadı !!!");
	}

	// Write text without element
	public void writeTextByKeyboard(String text) {
		iosDriver.getKeyboard().sendKeys(text);
	}
	
//===========================================================================Read Text
	// Read Text
	protected String readText(String pure_element) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(0))) {
			System.out.println("readText : " + elements.get(0).getText());
			return elements.get(0).getText();
		}
			throwFail(">>> readText("+pure_element+"); fonksiyonu çalışmadı !!!");
			return "";
	
	}

	// Read Text
	protected String readText(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(index))) {
			System.out.println("readText : " + elements.get(index).getText());
			return elements.get(index).getText();
		}
			throwFail(">>> readText("+pure_element+"); fonksiyonu çalışmadı !!!");
			return "";
	}
	
	// Read Text List
	protected String[][] readTextList(String pure_element) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		String[][] result = new String[elements.size()][1];
		for (int i = 0; i < result.length; i++) {
			result[i][0] = elements.get(i).getText();
		}
		return result;
	}
	

	protected void hideKeyboard(){
		if(iosDriver.isKeyboardShown()) {
			iosDriver.hideKeyboard(); 
			}
	}


	//===========================================================================Assert
		// Assert
		protected boolean assertEquals(String pure_element, String expected_text) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					String text = readText(pure_element);
					assertPool.assertEquals(text, expected_text,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (text.equals(expected_text))
					return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertEquals(readText(pure_element), expected_text);
			return true;
		}
		
		// Assert
		protected boolean assertEquals(String pure_element, int index, String expected_text) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					String text = readText(pure_element, index);
					assertPool.assertEquals(text, expected_text,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (text.equals(expected_text))
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertEquals(readText(pure_element, index), expected_text);
			return true;
		}

		// Assert integer
		protected boolean assertEquals(int number1, int number2) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					assertPool.assertEquals(number1, number2,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (number1 == number2)
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertEquals(number1, number2);
			return true;
		}
		
		// Assert Not
		protected boolean assertNotEquals(String pure_element, String expected_text) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
				String text = readText(pure_element);
					assertPool.assertNotEquals(text, expected_text,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (!text.equals(expected_text))
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertNotEquals(readText(pure_element), expected_text);
			return true;
		}
		
		// Assert Text
		protected boolean assertText(String text_1, String text_2) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					assertPool.assertEquals(text_1, text_2,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (text_1.equals(text_2))
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertEquals(text_1, text_2);
			return true;
		}
		
		// Assert Contains
		protected boolean assertContains(String pure_element, String text_2) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					String text = readText(pure_element);
					assertPool.assertTrue(text.contains(text_2),
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (text.contains(text_2))
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertTrue(readText(pure_element).contains(text_2));
			return true;
		}
			
		// Assert Not text
		protected boolean assertNotText(String text_1, String text_2) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					assertPool.assertNotEquals(text_1, text_2,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (!text_1.equals(text_2))
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertNotEquals(text_1, text_2);
			return true;
		}
		
		// Assert Found -- elementi bulmak için ekranda defaultImplicitWait sn boyunca arar.
		protected boolean assertFound(String pure_element) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					boolean bool = exists(pure_element, defaultImplicitWait);
					assertPool.assertTrue(bool,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (bool)
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertTrue(exists(pure_element, defaultImplicitWait));
			return true;
			}
		
		// Assert Found - belirli sureli arama
		protected boolean assertFound(String pure_element, int index) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					boolean bool = exists(pure_element, index, defaultImplicitWait);
					assertPool.assertTrue(bool,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (bool)
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertTrue(exists(pure_element, index, defaultImplicitWait));
			return true;
		}
		
		// Assert Found - belirli sureli arama
		protected boolean assertFound(String pure_element, int index, int second) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					boolean bool = exists(pure_element, index, second);
					assertPool.assertTrue(bool,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (bool)
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertTrue(exists(pure_element, index, second));
			return true;
		}

		protected boolean assertNotFound(String pure_element, int index, int second) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					boolean bool = exists(pure_element, index, second);
					assertPool.assertTrue(!bool,
							"\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: ");
					if (!bool)
						return true;
					else
						takeScreenshot();
					return false;
				}
			}
			Assert.assertTrue(!exists(pure_element, 1));
			return true;
		}
		
		protected boolean skip(String text) {
			for (String tag : scenario.getSourceTagNames()) {
				if (tag.contains(softTagName)) {
					assertPool.fail("\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: " + text);
					takeScreenshot();
					return true;
				}
			}
			throw new SkipException(text);
		}
		
	protected void assertAttribute(String pure_element, String attr, String attrText) {
		By elementBy = findElements(pure_element);
		WebElement element = iosDriver.findElement(elementBy);
		assertText(element.getAttribute(attr), attrText);
	}


	protected void assertListText(String pure_element, String text) {
		int size = getSize(pure_element);
		for (int i = 0; i < size; i++) {
			if (readText(pure_element, i).equals(text)) {
				return;
			}
		}
		fail(text + "' adındaki metin bulunamadi. List element: " + pure_element);
	}
	
	// Get Attribute
	public String geAttribute(String pure_element, String attribute_name) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		return elements.get(0).getAttribute(attribute_name);
	}

	// Get Value
	public String getValue(String pure_element) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		return elements.get(0).getAttribute("value");
	}
	

//===========================================================================Special for Elements
	// Exists Element
	/**
	 /* @param elementBy
	 * @param second    -- set seconds to wait element in page
	 * @return returns true or false regarding to element existing
	 */
	public boolean exists(String pure_element, int second) {
		try {
			if(second==0)
				second = 1;
			iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<IOSElement> elements = null;
			for (int i = 0; i < second; i++) {
				Thread.sleep(1000);
				try {
					elements = iosDriver.findElements(findElements(pure_element));
					if (elements.get(0).isDisplayed()) {  //android tarafında bunu .size() olarak aldım. sorun yaşanırsa burda da denenbilir.
						return true;
					} else if (elements.get(0).isEnabled()){
						return true;
					}
				} catch (Exception ex) {
					continue;
				}
			}
			System.out.println("element bulunamadi : " + pure_element);
			return false;

		} catch (Exception e) {
			System.out.println("element bulunamadi : " + pure_element);
			return false;
		} finally {
			iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	
	// Exists Element index
	/**
	 /* @param elementBy
	 * @param second    -- set seconds to wait element in page
	 * @return returns true or false regarding to element existing
	 */
	public boolean exists(String pure_element, int index, int second) {
		try {
			iosDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<IOSElement> elements = null;
			for (int i = 0; i < second; i++) {
				Thread.sleep(1000);
				try {
					elements = iosDriver.findElements(findElements(pure_element));
					if (elements.get(index).isDisplayed()) {
						return true;
					} else if (elements.get(index).isEnabled()){
						return true;
					}
				} catch (Exception ex) {
					continue;
				}
			}
			System.out.println("element bulunamadi : " + pure_element);
			return false;

		} catch (Exception e) {
			System.out.println("element bulunamadi : " + pure_element);
			return false;
		} finally {
			iosDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	
	// Get Element Size
	protected int getSize(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<IOSElement> elements = iosDriver.findElements(elementBy);
			int size = elements.size();
			System.out.println("elemenet size = " + size);
			return size;
		} catch (Exception ex) {
			System.out.println("getSize : 0");
			return 0;
		}
	}

//===========================================================================Special Methods

	protected String getStepName() {
		PickleStepTestStep currentStep = null;
		try {
			Field f = scenario.getClass().getDeclaredField("delegate");
			f.setAccessible(true);
			TestCaseState tcs = (TestCaseState) f.get(scenario);
			Field f2 = tcs.getClass().getDeclaredField("testCase");
			f2.setAccessible(true);
			TestCase r = (TestCase) f2.get(tcs);
			List<PickleStepTestStep> stepDefs = r.getTestSteps().stream()
					.filter(x -> x instanceof PickleStepTestStep).map(x -> (PickleStepTestStep) x)
					.collect(Collectors.toList());
			currentStep = stepDefs.get(stepNumber - 1);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return currentStep.getStepText();
	}
	
	protected void throwFail(String text) {
		for (String tag : scenario.getSourceTagNames()) {
			if (tag.contains(softTagName)) {
				assertPool.fail("\n Step No: " + stepNumber + "\n Step Name: " + getStepName() + "\nerror: " + text);
				takeScreenshot();
				return;
			}
		}
		fail(text);
	}

	// Back
	protected void back() {
		// driver.pressKeyCode(AndroidKeyCode.BACK);
		iosDriver.navigate().back();
	}

	//===========================================================================Swipe Methods

	/*
     * Swipe(startx, starty, endx, endy, time) Drag base on coordinates - X1 - First
     * point X - Y1 - First point Y - X2 - Second point X - Y2 - Second point Y -
     * time - Drag time (ms)
     */
    public void swipe(int firstPointX, int firstPointY, int secondPointX, int secondPointY,
            int dragTimeMS) {
        new TouchAction(iosDriver).press(PointOption.point(firstPointX, firstPointY))
                .waitAction(waitOptions(Duration.ofMillis(dragTimeMS)))
                .moveTo(PointOption.point(secondPointX, secondPointY)).release().perform();
    }
    
	public void swipe(String firt_point_element, int secondPointX, int secondPointY, int dragTimeMS) {
		int xy[] = getLocationXY(firt_point_element);
		new TouchAction(iosDriver).press(PointOption.point(xy[0], xy[1]))
				.waitAction(waitOptions(Duration.ofMillis(dragTimeMS)))
				.moveTo(PointOption.point(secondPointX, secondPointY)).release().perform();
	}
	
	public void scroll(Direction dir) {
		final int PRESS_TIME = 1000; // ms
		int edgeBorder = 10; // better avoid edges
		PointOption pointOptionStart, pointOptionEnd;

		// init screen variables
		Dimension dims = iosDriver.manage().window().getSize();

		// init start point = center of screen
		pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);

		switch (dir) {
			case DOWN: // center of footer
				pointOptionEnd = PointOption.point(dims.width / 2, dims.height - edgeBorder);
				break;
			case UP: // center of header
				pointOptionEnd = PointOption.point(dims.width / 2, edgeBorder);
				break;
			case LEFT: // center of left side
				pointOptionEnd = PointOption.point(edgeBorder, dims.height / 2);
				break;
			case RIGHT: // center of right side
				pointOptionEnd = PointOption.point(dims.width - edgeBorder, dims.height / 2);
				break;
			default:
				throw new IllegalArgumentException("swipeScreen(): dir: '" + dir + "' NOT supported");
		}

		// execute swipe using TouchAction
		try {
			new TouchAction(iosDriver)
					.press(pointOptionStart)
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
					.moveTo(pointOptionEnd)
					.release().perform();
		} catch (Exception e) {
			System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
			return;
		}
	}

	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}

	// Get Element Width
	protected int getWidth(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<IOSElement> elements = iosDriver.findElements(elementBy);
			System.out.println("getWidth: "+ elements.get(0).getSize().getWidth());
			return elements.get(0).getSize().getWidth();
		} catch (Exception ex) {
			return 0;
		}
	}
//	
	// getHeight
	protected int getHeight(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<IOSElement> elements = iosDriver.findElements(elementBy);
			System.out.println("getHeight: "+ elements.get(0).getSize().getWidth());
			return elements.get(0).getSize().getHeight();
		} catch (Exception ex) {
			return 0;
		}
	}


	protected int[] getLocationXY(String pure_element) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		int[] arry = new int[2];
		arry[0] = elements.get(0).getLocation().getX();
		arry[1] = elements.get(0).getLocation().getY();
		return arry;
	}
	
	protected int[] getLocationXY(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<IOSElement> elements = iosDriver.findElements(elementBy);
		int[] arry = new int[2];
		arry[0] = elements.get(index).getLocation().getX();
		arry[1] = elements.get(index).getLocation().getY();
		return arry;
	}
	
    protected void takeScreenshot() {
			scenario.attach(scale(((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.BYTES), 375, 667), "image/png",
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
	
}
