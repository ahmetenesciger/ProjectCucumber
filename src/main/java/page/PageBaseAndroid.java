package page;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import io.appium.java_client.touch.WaitOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import utilities.SoftAssert;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
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

public class PageBaseAndroid {
	protected AndroidDriver<AndroidElement> androidDriver;
	public WebDriverWait wait;
	JavascriptExecutor js;
	int swipeCount = 5;
	final int defaultImplicitWait = 15;
	private static HashMap _dynamicParameters = new HashMap<String,String>();
	protected Scenario scenario;
	protected int stepNumber;
	protected SoftAssert assertPool;
	private String softTagName = "sanityOne";

	// Constructor
	public PageBaseAndroid(AndroidDriver<?> androidDriver) {
		this.androidDriver = (AndroidDriver<AndroidElement>) androidDriver;
		wait = new WebDriverWait(androidDriver, defaultImplicitWait);
		androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
	}
	
	protected By findElements(String pure_element) {
		By elementBy = null;
		if (pure_element.substring(0, 1).equals("#")) {
			elementBy = MobileBy.id(pure_element.substring(1));
		} else if (pure_element.substring(0, 1).equals("*")) {
			if (pure_element.contains(":id/")) {
				elementBy = MobileBy.AndroidUIAutomator("resourceId(\"" + pure_element.substring(1) + "\")");
			}else
			elementBy = MobileBy
					//sondaki instance'ı kaldırınca dizi olarak bir By dönüyor olması lazım. test et.
					//öyle ise instanceları kaldır. indexleme event methodunda olsun.
					.AndroidUIAutomator("resourceId(\"com.pozitron."+ System.getProperty("appName") + ":id/" + pure_element.substring(1) + "\")");
		} else if (pure_element.substring(0, 1).equals(".")) {
			elementBy = MobileBy.className(pure_element.substring(1));
		} else if (pure_element.substring(0, 1).equals("$")) {
			elementBy = MobileBy
					.AndroidUIAutomator("text(\"" + pure_element.substring(1) + "\")");
		} else if (pure_element.substring(0, 1).equals("@")) {
			elementBy = MobileBy.xpath(pure_element.substring(1));
//					.AndroidUIAutomator("description(\"" + pure_element.substring(1) + "\").instance(" + index + ")");
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
	
//	protected boolean isPresent(By elementBy)
//	{
//		try
//		{
//			wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
//			return true;
//		}
//		catch (Exception e)
//		{
//			return false;
//		}
//	}
	
	protected boolean isPresent(AndroidElement element)
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
//	protected boolean isClickable(By elementBy)
//	{
//		try
//		{
//			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
//			return true;
//		}
//		catch (Exception e)
//		{
//			return false;
//		}
//	}
	
	protected boolean isClickable(AndroidElement element) {
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
	
	protected boolean isClickable(String pure_element, int index)
	{
		try
		{
			By elementBy = findElements(pure_element);
			List<AndroidElement> elements = androidDriver.findElements(elementBy);
			wait.until(ExpectedConditions.elementToBeClickable(elements.get(index)));
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	// Wait By Wrapper Method
	protected void waitLoadingImage() {
		try {
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("animationView")));
			//ait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("progressBar")));
		} catch (Exception ex) {
		} finally {
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	
	
	// Wait By Wrapper Method
	protected void waitLoginLoading() {
		try {
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("animationView")));
			//ait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("progressBar")));
		} catch (Exception ex) {
		} finally {
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	
	
	// Wait By Wrapper Method
	protected void waitStartLoading() {
		try {
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("animationView")));
			//ait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.id("progressBar")));
		} catch (Exception ex) {
		} finally {
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}

	// Wait By Wrapper Method
	protected void waitLoadingAppDonwloadPage() {
		try {
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(MobileBy.className("android.widget.ProgressBar")));
		} catch (Exception ex) {
		} finally {
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
//=================================================== Click
	// Click By Method
	protected void click(String pure_element) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		if (elements.size() > 0 && isClickable(elements.get(0)))
			elements.get(0).click();
		else
			throwFail(">>> click("+pure_element+"); fonksiyonu çalışmadı !!!");
	}
	
	// Click By Method
	protected void click(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);

		if (elements.size() > index && isClickable(elements.get(index)))
			elements.get(index).click();
		else
			throwFail(">>> click("+pure_element+"); fonksiyonu çalışmadı !!!");
	}
	
	// Click By Method
	public void clickUnstable(String pure_element) {
		androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int x = -1, y = -1;
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(androidDriver);
		for (int i = 0; i < 20; i++) {
			try {

				List<AndroidElement> elements = androidDriver.findElements(elementBy);
				if (x == -1)
					x = elements.get(0).getLocation().getX() + 6;
				y = elements.get(0).getLocation().getY();
				break;
			} catch (Exception ex) {

			}
		}
		androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		if (x == -1 && y == -1) {
			throwFail(pure_element + " >> elementi bulunamadı******");
		}
		TouchAction touchAction = new TouchAction(androidDriver);
		touchAction.tap(PointOption.point(x + getWidth(pure_element)/2, y + getHeight(pure_element))).perform();
	}	
	
	public void clickUnstable(String pure_element, int index) {
		androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int x = -1, y = -1;
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(androidDriver);
		for (int i = 0; i < 20; i++) {
			try {

				List<AndroidElement> elements = androidDriver.findElements(elementBy);
				if (x == -1)
					x = elements.get(index).getLocation().getX() + 6;
				y = elements.get(index).getLocation().getY();
				break;
			} catch (Exception ex) {

			}
		}
		androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		if (x == -1 && y == -1) {
			throwFail(pure_element + " elementi bulunamadı******");
		}
		TouchAction touchAction = new TouchAction(androidDriver);
		touchAction.tap(PointOption.point(x, y)).perform();
	}
	
	protected void clickAttribute(String pure_element, String attr, String attrText) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getAttribute(attr).equals(attrText)) {
				if (isClickable(elements.get(i))) {
					elements.get(i).click();
					return;
				}
			}
		}
		throwFail(">>> clickAttribute(" + pure_element + "); fonksiyonu çalışmadı !!!");
	}

	// Long Click By Method
	protected void longClick(String pure_element) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		Point location = elements.get(0).getLocation();
		TouchAction touchAction = new TouchAction(androidDriver);
		touchAction.longPress(PointOption.point(location)).perform();
	}
	
	// Long Click By Method
	protected void longClick(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		Point location = elements.get(index).getLocation();
		TouchAction touchAction = new TouchAction(androidDriver);
		touchAction.longPress(PointOption.point(location)).perform();

	}
	
    protected void clickToCoordinate(int x, int y) {
        TouchAction touchAction = new TouchAction(androidDriver);
       touchAction.press(PointOption.point(x,y))
                  .waitAction(waitOptions(Duration.ofMillis(250)))
                  .release()
                  .perform();
   }
	
	// click to coorditane of element X yukardan aşagı. Y soldak Saga
	protected void clickToCoordinateOfElement(String pure_element, int coordinateX, int coordinateY) {
		By elementBy = findElements(pure_element);
		Actions builder = new Actions(androidDriver);
		builder.moveToElement(androidDriver.findElement(elementBy), coordinateX, coordinateY).click().build().perform();

	}

	protected void clickListItem(String pure_element, String text) {
		int size = getSize(pure_element);
		for (int i = 0; i < size; i++) {
			if (readText(pure_element, i).replaceAll("\\s+","").equals(text.replaceAll("\\s+",""))) {
				click(pure_element, i);
				return;
			}
		}
		throwFail("element bulunamadi: " + pure_element);
	}

	//Swipe Method
	/*protected void clickListItemSwipe(String pure_element, String text, Direction direction) {
		int size = getSize(pure_element);
		int k = 0;
		By elementBy = findElements(pure_element, 0);
		waitClickable(elementBy);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		do {
			for (int i = 0; i < size; i++) {
				if (readText(pure_element, i).equals(text)) {
					k = size;
					click(pure_element, i);
					return;
				}
			}
			swipeElement(elements.get(0), direction);
			k++;
		} while ( k < size);
		fail("element bulunamadi: " + pure_element);
	}*/
	
//===========================================Write Text
	// Write By Text
	protected void writeText(String pure_element, String text) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(0))) {
			elements.get(0).clear();
			elements.get(0).sendKeys(text);
	}
		else
			throwFail(">>> writeText("+pure_element+"); fonksiyonu çalışmadı !!!");

	}

	// Write By Text
	protected void writeText(String pure_element, String text, int index) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(index))) {
			elements.get(index).clear();
			elements.get(index).sendKeys(text);
	}
		else
			throwFail(">>> writeText("+pure_element+"); fonksiyonu çalışmadı !!!");
	}

	protected void writeListItem(String pure_element, String text, String text2) {
		String compareText = null;
		int size = getSize(pure_element);
		for (int i=0; i < size; i++){
			if(readText(pure_element, i).equals(text))
			{
				compareText = text;
				writeText(pure_element, text2, i);
				break;
			}
		}
		assertText(compareText, text);
	}
	
	protected void writeListItem(String pure_element, String text, String pure_element2, String text2) {
		String compareText = null;
		int size = getSize(pure_element);
		for (int i=0; i < size; i++){
			if(readText(pure_element, i).equals(text))
			{
				compareText = text;
				click(pure_element, i);
				writeText(pure_element2, text2, i);
				break;
			}
		}
		assertText(compareText, text);
	}
	
	// Write text without element
	protected void writeTextByKeyboard(String text) {
		androidDriver.getKeyboard().sendKeys(text);
	}

//===========================================================================Read Text
	// Read Text
	protected String readText(String pure_element) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
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
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(index))) {
			System.out.println("readText : " + elements.get(index).getText());
			return elements.get(index).getText();
		}
			throwFail(">>> readText("+pure_element+"); fonksiyonu çalışmadı !!!");
			return "";
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
	
	protected void assertListFound(String pure_element) {
		int size = getSize(pure_element);
		for (int i = 0; i < size; i++) {
			if (exists(pure_element, 5)) {
				return;
			}
		}
	}

	//ekranda olan ve beklenen özelliğe sahip elementi getirir. (tarar)
	protected void assertAttribute(String pure_element, String attr, String attrText) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getAttribute(attr).equals(attrText)) {
				return;
			}
		}
		throwFail("bu özelliğe sahip element bulunamadı.");
	}
	
	//belirli bir index'teki elementin attribute degerini sorgular. (anlik)
	protected void assertAttribute(String pure_element, int index, String attr, String attrText) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		assertText(elements.get(index).getAttribute(attr), attrText);
	}

	protected void assertListText(String pure_element, String text) {
		int size = getSize(pure_element);
		for (int i = 0; i < size; i++) {
			if (readText(pure_element, i).replaceAll("\\s+","").equals(text.replaceAll("\\s+",""))) {
				return;
			}
		}
		throwFail(text + "' adındaki metin bulunamadi. List element: " + pure_element);
	}

	protected void assertListAttribute(String pure_element, String text, String attr, String attrText) {
		int count = getSize(pure_element);
		for (int i = 0; i < count; i++) {
			if(readText(pure_element, i).equals(text))
			{
				if(androidDriver.findElements(findElements(pure_element)).get(i).getAttribute(attr).equals(attrText))
					return;
				else
				{
					throwFail(text + "' adındaki metinin attribute'lari icinde "+ attr +" bulunamadi. List element: " + pure_element);
					return;
				}
			}
		}
		throwFail(text + " adındaki metinin bulunamadi. List element: " + pure_element);
	}
	

	protected String getAttriValue(String pure_element, String attribute, int index) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		return elements.get(index).getAttribute(attribute);
	}
	
	protected String getAttriValue(String pure_element, String attribute) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		return elements.get(0).getAttribute(attribute);
	}

	//=========================================================================== Special Methods


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
	
	// Exists Element
	/** elementin ekran üzerinde mevcut olup olmadığını kontrol eder
	 * @param elementBy  - varlığı kontrol edilecek element
	 * @param second    -- set seconds to wait element in page
	 * @return returns true or false regarding to element existing
	 */
	protected boolean exists(String pure_element, int second) {
		try {
			if(second==0)
				second = 1;
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<AndroidElement> elements = null;
			for (int i = 0; i < second; i++) {
				Thread.sleep(1000);
				try {
					elements = androidDriver.findElements(findElements(pure_element));
					if (elements.size() > 0) {
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
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}
	
	protected boolean exists(String pure_element, int index, int second) {
		try {
			androidDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<AndroidElement> elements = null;
			for (int i = 0; i < second; i++) {
				Thread.sleep(1000);
				try {
					elements = androidDriver.findElements(findElements(pure_element));
					if (elements.get(index).isDisplayed()) {
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
			androidDriver.manage().timeouts().implicitlyWait(defaultImplicitWait, TimeUnit.SECONDS);
		}
	}

	// Get Element Size
	protected int getSize(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<AndroidElement> elements = androidDriver.findElements(elementBy);
			int size = elements.size();
			System.out.println("getSize : " + size);
			return size;
		} catch (Exception ex) { 
			System.out.println("getSize : 0");
			return 0;
		}
	}
	
	protected void hideKeyboard(){
		if(androidDriver.isKeyboardShown()) {   androidDriver.hideKeyboard(); }
	}



	// Back
	protected void back() {
		// driver.pressKeyCode(AndroidKeyCode.BACK);
		androidDriver.navigate().back();
	}

	//===========================================================================Swipe Methods
	public void scroll(Direction dir) {
		waitLoadingImage();
		final int PRESS_TIME = 800; // ms //DEFAULTU 700 İDİ
		int edgeBorder = 10; // better avoid edges
		PointOption pointOptionStart, pointOptionEnd;

		// init screen variables
		Dimension dims = androidDriver.manage().window().getSize();

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
			new TouchAction(androidDriver)
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

	/*
	 * Swipe(startx, starty, endx, endy, time) Drag base on coordinates - X1 - First
	 * point X - Y1 - First point Y - X2 - Second point X - Y2 - Second point Y -
	 * time - Drag time (ms)
	 */
	public void swipe(int firstPointX, int firstPointY, int secondPointX, int secondPointY, int dragTimeMS) {
//		try {
			new TouchAction(androidDriver).press(PointOption.point(firstPointX, firstPointY))
					.waitAction(waitOptions(Duration.ofMillis(dragTimeMS)))
					.moveTo(PointOption.point(secondPointX, secondPointY)).release().perform();
			wait(1);
//		} catch (Exception ex) {System.err.println("swipe methodu hata aldı..");}
	}
	
	public void swipe(String firt_point_element, int secondPointX, int secondPointY, int dragTimeMS) {
		int xy[] = getLocationXY(firt_point_element);
		new TouchAction(androidDriver).press(PointOption.point(xy[0], xy[1]))
				.waitAction(waitOptions(Duration.ofMillis(dragTimeMS)))
				.moveTo(PointOption.point(secondPointX, secondPointY)).release().perform();
	}

	// Swipe list to last
	/**
	 * kordinatları izlenecek element, kaydırma sonrası aynı kordinatta ise kaydırma
	 * tamamlanmıştır. ve ekranda daha kaydırma yapılaz ise geriye false dönecektir.
	 * 
	 * @param pure_element swipe yapılınca kordinatları izlenecek element.
	 * @return liste sonuna gelinir ve kaydırma yapılamaz ise 'false' degeri
	 *         dondurulur.
	 */
	public boolean swipeList(String pure_element, int firstPointX, int firstPointY, int secondPointX,
			int secondPointY, int dragTimeMS) {
		try {
			int actualSize = getSize(pure_element);
			int[] arry = getLocationXY(pure_element, actualSize - 1);
			swipe(firstPointX, firstPointY, secondPointX, secondPointY, dragTimeMS);
			wait(1);
			int[] arryLastPosition = getLocationXY(pure_element, getSize(pure_element) - 1);
			if (arry[0] == arryLastPosition[0] && arry[1] == arryLastPosition[1]) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {
			throwFail("liste elemanları bulunamadı");
			return false;
		}
	}

	protected int[] getLocationXY(String pure_element) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		int[] arry = new int[2];
		arry[0] = elements.get(0).getLocation().getX();
		arry[1] = elements.get(0).getLocation().getY();
		return arry;
	}
	
	protected int[] getLocationXY(String pure_element, int index) {
		By elementBy = findElements(pure_element);
		List<AndroidElement> elements = androidDriver.findElements(elementBy);
		int[] arry = new int[2];
		arry[0] = elements.get(index).getLocation().getX();
		arry[1] = elements.get(index).getLocation().getY();
		return arry;
	}
	
	protected void setLocation(String location) {
		if (location.equals("office")) {
			androidDriver.setLocation(new Location(41.006405, 29.074996, 1));
		}
		wait(3);
	}
	
	// Get Element Width
	protected int getWidth(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<AndroidElement> elements = androidDriver.findElements(elementBy);
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
			List<AndroidElement> elements = androidDriver.findElements(elementBy);
			return elements.get(0).getSize().getHeight();
		} catch (Exception ex) {
			return 0;
		}
	}
	
    protected void takeScreenshot() {
			scenario.attach(scale(((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.BYTES), 375, 667), "image/png",
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