package page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.TestCase;
import utilities.SoftAssert;

import static org.testng.Assert.fail;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class PageBaseWeb {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;
	
	int defaultImplicitWait = 30;  // HENUZ KULLANMADIN
	protected Scenario scenario;
	private static HashMap _dynamicParameters = new HashMap<String,String>();
	protected int stepNumber;
	protected SoftAssert assertPool;
	private String softTagName = "sanityOne";
	
	protected PageBaseWeb(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		this.js = (JavascriptExecutor) driver;
	}
	
	protected void beforeTrue(String pure_element) {		
		WebElement switchLabel = driver.findElement(findElements(pure_element)); 
		String colorRGB = ((JavascriptExecutor)driver) 
		        .executeScript("return window.getComputedStyle(arguments[0], ':before').getPropertyValue('background-color');",switchLabel).toString();
	System.out.println(colorRGB);
	}
	
	
	protected By findElements(String pure_element) {
	waitAllRequest();
	By elementBy = null;
	if (pure_element.substring(0, 1).equals("@")) {
		elementBy = By.xpath(pure_element.substring(1));
	}
	  else
		elementBy = By.cssSelector(pure_element);
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
	
	protected void waitAllRequest() {
		waitUntilJSReady();
		ajaxComplete();
		waitUntilJQueryReady();
		waitUntilAngularReady();
		waitUntilAngular5Ready();
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
	
	protected boolean isClickable(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	protected boolean isPresent(WebElement element)
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

	// Wait By Wrapper Method
	protected void waitClickable(String pure_element) {
		wait.until(ExpectedConditions.elementToBeClickable(findElements(pure_element)));
	}
	
	// Wait By Wrapper Method
	protected void waitClickable(String pure_element, int index) {
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(findElements(pure_element)).get(index)));
	}
	
	// Wait By Wrapper Method
	protected void waitClickable(By elementBy) {
		wait.until(ExpectedConditions.elementToBeClickable(elementBy));
	}
	
	protected void waitClickable(WebElement element) {
		waitLoadingImage();
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
//	protected void waitVisibility(By elementBy) {
//		wait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
//	}

	// Wait By Wrapper Method
	protected void waitLoadingImage() {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("path[stroke-linecap='round']")));
		} catch (Exception ex) {
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}
	
	private void ajaxComplete() {
		js.executeScript("var callback = arguments[arguments.length - 1];" + "var xhr = new XMLHttpRequest();"
				+ "xhr.open('GET', '/Ajax_call', true);" + "xhr.onreadystatechange = function() {"
				+ "  if (xhr.readyState == 4) {" + "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
	}

	private void waitForJQueryLoad() {
		try {
			ExpectedCondition<Boolean> jQueryLoad = driver -> ((Long) ((JavascriptExecutor) this.driver)
					.executeScript("return jQuery.active") == 0);

			boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

			if (!jqueryReady) {
				wait.until(jQueryLoad);
			}
		} catch (WebDriverException ignored) {
		}
	}

	private void waitForAngularLoad() {
		String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";
		angularLoads(angularReadyScript);
	}

	private void waitUntilJSReady() {
		try {
			ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) this.driver)
					.executeScript("return document.readyState").toString().equals("complete");

			boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

			if (!jsReady) {
				wait.until(jsLoad);
			}
		} catch (WebDriverException ignored) {
		}
	}

	private void waitUntilJQueryReady() {
		Boolean jQueryDefined = (Boolean) js.executeScript("return typeof jQuery != 'undefined'");
		if (jQueryDefined) {
			waitMilliSec(20);

			waitForJQueryLoad();

			waitMilliSec(20);
		}
	}

	private void waitUntilAngularReady() {
		try {
			Boolean angularUnDefined = (Boolean) js.executeScript("return window.angular === undefined");
			if (!angularUnDefined) {
				Boolean angularInjectorUnDefined = (Boolean) js
						.executeScript("return angular.element(document).injector() === undefined");
				if (!angularInjectorUnDefined) {
					waitMilliSec(20);

					waitForAngularLoad();

					waitMilliSec(20);
				}
			}
		} catch (WebDriverException ignored) {
		}
	}

	private void waitUntilAngular5Ready() {
		try {
			Object angular5Check = js.executeScript("return getAllAngularRootElements()[0].attributes['ng-version']");
			if (angular5Check != null) {
				Boolean angularPageLoaded = (Boolean) js
						.executeScript("return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1");
				if (!angularPageLoaded) {
					waitMilliSec(20);

					waitForAngular5Load();

					waitMilliSec(20);
				}
			}
		} catch (WebDriverException ignored) {
		}
	}

	private void waitForAngular5Load() {
		String angularReadyScript = "return window.getAllAngularTestabilities().findIndex(x=>!x.isStable()) === -1";
		angularLoads(angularReadyScript);
	}

	private void angularLoads(String angularReadyScript) {
		try {
			ExpectedCondition<Boolean> angularLoad = driver -> Boolean
					.valueOf(((JavascriptExecutor) driver).executeScript(angularReadyScript).toString());

			boolean angularReady = Boolean.valueOf(js.executeScript(angularReadyScript).toString());

			if (!angularReady) {
				wait.until(angularLoad);
			}
		} catch (WebDriverException ignored) {
		}
	}

	/**
	 * Method to make sure a specific element has loaded on the page
	 *
	 * @param by
	 * @param expected
	 */
	protected void waitForElementAreComplete(By by, int expected) {
		ExpectedCondition<Boolean> angularLoad = driver -> {
			int loadingElements = this.driver.findElements(by).size();
			return loadingElements >= expected;
		};
		wait.until(angularLoad);
	}
	/**
	 * Waits for the elements animation to be completed
	 * 
	 * @param css
	 */
	protected void waitForAnimationToComplete(String css) {
		ExpectedCondition<Boolean> angularLoad = driver -> {
			int loadingElements = this.driver.findElements(By.cssSelector(css)).size();
			return loadingElements == 0;
		};
		wait.until(angularLoad);
	}
	
	protected void waitForAjax() {
		new WebDriverWait(driver, 30).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) d;
				return (Boolean) js.executeScript("return jQuery.active == 0");
			}
		});
	}
//	private void highlightElement(By elementBy, int index) {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", driver.findElements(elementBy).get(index),
//				"color: red; border: 1px dashed red;");
//	}
	
	private void highlightElement(String pure_element) {	
		highlightElement(driver.findElement(findElements(pure_element)));
	}
	
	private void highlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"color: red; border: 1px dashed red;");
	}
	
	protected void navigateToURL(String URL) {
		driver.navigate().to(URL);
	}
	
	protected void back() {
		driver.navigate().back();
	}
	
	// =================================================== Click
	// Click By Method
	protected void click(String pure_element) {
		try {
			By elementBy = findElements(pure_element);
			List<WebElement> elements = driver.findElements(elementBy);
			if (elements.size() > 0) {
				highlightElement(elements.get(0));
				elements.get(0).click();
			} else
				throwFail(">>> click(" + pure_element + "); fonksiyonu çalışmadı !!!");
		} catch (Exception ex) {
			throwFail(">>> click(" + pure_element + "); fonksiyonu çalışmadı !!!");
		}
	}
	
	// Click By Method
	protected void click(WebElement element) {
		if (isClickable(element)) {
			highlightElement(element);
			element.click();
		} else
			throwFail(">>> click(); fonksiyonu çalışmadı !!!");
	}

	// Click By Method
	protected void click(String pure_element, int index) {
		try {
			By elementBy = findElements(pure_element);
			List<WebElement> elements = driver.findElements(elementBy);
			if (elements.size() > 0) {
				highlightElement(elements.get(index));
				elements.get(index).click();
			} else
				throwFail(">>> click(" + pure_element + "); fonksiyonu çalışmadı !!!");
		} catch (Exception ex) {
			throwFail(">>> click(" + pure_element + "); fonksiyonu çalışmadı !!!");
		}
	}
		
		// click to coorditane of element X yukardan aşagı. Y soldak Saga
		protected void clickCoordinateOfElement(String pure_element, int coordinateX, int coordinateY) {
			By elementBy = findElements(pure_element);
			highlightElement(driver.findElement(elementBy));
			Actions builder = new Actions(driver);
			builder.moveToElement(driver.findElement(elementBy), coordinateX, coordinateY).click().build().perform();
		}
		
		
		
//çalıştımı bi denersin cok lazım olursa
//		// clickUnstable
//		public void clickUnstable(String pure_element) {
//			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
//			highlightElement(driver.findElement(findElements(pure_element)));
//			int x = -1, y = -1;
//			By elementBy = findElements(pure_element);
//			Actions builder = new Actions(driver);
//			for (int i = 0; i < 20; i++) {
//				try {
//
//					List<WebElement> elements = driver.findElements(elementBy);
//					if (x == -1)
//						x = elements.get(0).getLocation().getX() + 6;
//					y = elements.get(0).getLocation().getY();
//					break;
//				} catch (Exception ex) {
//
//				}
//			}
//			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//			if (x == -1 && y == -1) {
//				fail(pure_element + " elementi bulunamadı******");
//			}
//			
//			
//			builder.moveToElement(driver.findElement(By.tagName("body")), 0, 0);
//			builder.moveByOffset(x, y).click().build().perform();
//			System.out.println("11");
//		}	
		
		
		
		
		
	//===========================================Write Text
	// Write By Text
	protected void writeText(String pure_element, String text) {
		By elementBy = findElements(pure_element);
		List<WebElement> elements = driver.findElements(elementBy);
		if (elements.size() > 0 && isPresent(elements.get(0))) {
			highlightElement(elements.get(0));
			elements.get(0).clear();
			elements.get(0).sendKeys(text);
		} else
			throwFail(">>> writeText(" + pure_element + "); fonksiyonu çalışmadı !!!");
	}

		// Write By Text
		protected void writeText(String pure_element, String text, int index) {
			By elementBy = findElements(pure_element);
			List<WebElement> elements = driver.findElements(elementBy);
			if (elements.size() > 0 && isPresent(elements.get(index))) {
				highlightElement(elements.get(index));
				elements.get(index).clear();
				elements.get(index).sendKeys(text);
		}
			else
				throwFail(">>> writeText("+pure_element+"); fonksiyonu çalışmadı !!!");
		}
		
		protected void writeHotKey(String text) {
			Actions keyAction = new Actions(driver);
			keyAction.sendKeys(text).perform();
		}
		
		protected void writeHotKey(CharSequence key) {
			Actions keyAction = new Actions(driver);
			keyAction.sendKeys(key).perform();
		}
		
		protected void writeListItem(String pure_element, String text) {
			String compareText = null;
			int size = getSize(pure_element);
			for (int i=0; i < size; i++){
				if(readText(pure_element, i).equals(text))
				{
					compareText = text;
					writeText(pure_element, compareText, i);
					break;
				}
			}
			assertText(compareText, text);
		}

	//===========================================================================Read Text
		// Read Text
		protected String readText(String pure_element) {
			By elementBy = findElements(pure_element);
			List<WebElement> elements = driver.findElements(elementBy);
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
			List<WebElement> elements = driver.findElements(elementBy);
			if (elements.size() > 0 && isPresent(elements.get(index))) {
				System.out.println("readText : " + elements.get(index).getText());
				return elements.get(index).getText();
			}
				throwFail(">>> readText("+pure_element+"); fonksiyonu çalışmadı !!!");
				return "";
		}
				
		///ASSERTLER eşitlendi. assert class yapılabilir.
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
			WebElement element = driver.findElement(elementBy);
			assertText(element.getAttribute(attr), attrText);
		}
		
		
	//============================================================== Actions
	// Mouse Over
	protected void mouseOver(String pure_element) {
		By elementBy = findElements(pure_element);
		highlightElement(driver.findElement(elementBy));
		Actions myAction = new Actions(driver);
		myAction.moveToElement(driver.findElement(elementBy)).build().perform();
	}
	
	// exists
	/**
	 * @param elementBy
	 * @return returns true or false regarding to element existing
	 */
	protected boolean exists(By elementBy) {
		try {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			List<WebElement> elements = null;
			wait(1);
			elements = driver.findElements(elementBy);
			if (elements.get(0).isDisplayed()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		} finally {
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}
	
	// Exists Element
		/**
		 * @param pure_element
		 * @param second    -- set seconds to wait element in page
		 * @return returns true or false regarding to element existing
		 */
		protected boolean exists(String pure_element, int second) {
			try {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				List<WebElement> elements = null;
				for (int i = 0; i < second; i++) {
					Thread.sleep(1000);
					try {
						elements = driver.findElements(findElements(pure_element));
						if (elements.get(0).isDisplayed()) {
							highlightElement(pure_element);
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
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
		}
		// Exists Element
		/**
		 * @param pure_element
		 * @param second    -- set seconds to wait element in page
		 * @return returns true or false regarding to element existing
		 */
		protected boolean exists(String pure_element, int index, int second) {
			try {
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				List<WebElement> elements = null;
				for (int i = 0; i < second; i++) {
					Thread.sleep(1000);
					try {
						elements = driver.findElements(findElements(pure_element));
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
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
		}

		// Get Element Size
		protected int getSize(String pure_element) {
			try {
				By elementBy = findElements(pure_element);
				List<WebElement> elements = driver.findElements(elementBy);
				int size = elements.size();
				System.out.println("elemenet size = " + size);
				return size;
			} catch (Exception ex) {
				System.out.println("elemenet size = 0");
				return 0;
			}
		}

	protected void goToEndOfPage() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	protected void goToTopOfPage() {
		js.executeScript("window.scrollTo(0, 0)");
	}

	protected String executeJS(String txt, WebElement element) {
		wait(1);
		return (String)js.executeScript(txt, element);
	}
	
	// checked Method
	protected void checked(By elementBy) {
		waitClickable(elementBy);
		if (!driver.findElement(elementBy).isSelected()) {
			driver.findElement(elementBy).click();
		}
	}
	
	protected void dragDropToElement(WebElement sourceElement, WebElement targetElement) {
		Actions actionProvider = new Actions(driver);
		actionProvider.dragAndDrop(sourceElement, targetElement).build().perform();
	}	
	
	protected void dragDropToCoordinate(WebElement sourceElement, int x, int y) {
		Actions move = new Actions(driver);
		Action action = move.dragAndDropBy(sourceElement, x, y).build();
			   action.perform();
	}

	// checked Method
	protected void checked(By elementBy, int index) {
		List<WebElement> list = driver.findElements(elementBy);
		WebElement element = list.get(index);
		if (!element.isSelected()) {
			element.click();
		}
	}

	// unchecked Method
	protected void unchecked(By elementBy) {
		waitClickable(elementBy);
		if (driver.findElement(elementBy).isSelected()) {
			driver.findElement(elementBy).click();
		}
	}
	
	// unchecked Method
	protected void unchecked(By elementBy, int index) {
		List<WebElement> list = driver.findElements(elementBy);
		WebElement element = list.get(index);
		if (element.isSelected()) {
			element.click();
		}
	}

	// Wait Loading Image
//	protected void waitLoadingImg(By elementBy) {
//		try {
//			waitForPageToLoad();
//			Thread.sleep(1500);
//
//			for (int i = 0; i < 60; i++) {
//				// if
//				// (!driver.findElement(loadingImageBy).getCssValue("visibility").equals("hidden"))
//				if (!driver.findElement(elementBy).getAttribute("display").equals("none")) {
//					Thread.sleep(1500);
//				} else {
//					break;
//				}
//			}
//		}catch (Exception ex) {
//			}
//			
//		}

	// Select html <li> Item
	protected List<WebElement> selectiItem(WebElement ItemsUL, String tagname) {
		List<WebElement> ItemsList = ItemsUL.findElements(By.tagName(tagname));
		return ItemsList;
	}
	
	// Get Child Element
	/**
	 * @param elementBy -- By of main element
	 * @param tagName   -- main elementin 1. seviye altında aranacak child tag adı
	 *                  örnek : li tagı.
	 * @param index     -- gidilecek olan child tagın sırasını giriniz. örnek :
	 *                  (elementBy= XYZ, tagName=li, index=5) = XYZ elementinin
	 *                  altındaki 5. li tagının By değerini döndürür.
	 */
	protected WebElement getChild(String pure_element, String tag_name, int index) {
		By elementBy = findElements(pure_element);		
		WebElement element = driver.findElement(elementBy);
		List<WebElement> tableRows = element.findElements(By.tagName(tag_name));
		return tableRows.get(index);
	}
	// Get Child Element  ---  end.
	
	// Get Attiribute 
	protected String getAttribute(String pure_element, String attribute_name) {  
		waitAllRequest();
		return driver.findElements(findElements(pure_element)).get(0).getAttribute(attribute_name); // örn value
		}
	
	protected String getAttribute(String pure_element, int index, String attribute_name) {  
		waitAllRequest();
		return (driver.findElements(findElements(pure_element)).get(index)).getAttribute(attribute_name); // örn value
		}
	
	protected String getCssValue(String pure_element, String attribute_name) {  
		waitAllRequest();
		return (driver.findElements(findElements(pure_element)).get(0)).getCssValue(attribute_name); // örn value
		}
	
	
		// Switch Tab Page
		protected void switchToNewTab(boolean kill_old_tab) {
			waitAllRequest();
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			waitAllRequest();
			if (kill_old_tab) {
				driver.switchTo().window(tabs.get(0));
				driver.close();
				driver.switchTo().window(tabs.get(1));
			}
		}

		
		protected void swipeToElement(String pure_element, int index) {
			wait(1);
			try {
				js.executeScript(
						"arguments[0].scrollIntoView({behavior: \"smooth\", block: \"center\", inline: \"center\"});",
						driver.findElements(findElements(pure_element)).get(index));
			} catch (Exception ex) {
			}
			wait(1);
		}
		
		
//		protected void swipeOrj(String pure_element, int x, int y) {
//			Actions action = new Actions(driver);
//	        action.dragAndDropBy(driver.findElements(findElements(pure_element)).get(0), x, y).build().perform();
//		}
		
		protected void scroll() {
			JavascriptExecutor jsx = (JavascriptExecutor)driver;
			jsx.executeScript("window.scrollBy(100,200)", "");
		}
		
		protected void ExecuteCommandPrompt(String cmd_text) {
			try {
				new ProcessBuilder("cmd.exe", "/c", cmd_text).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		protected String getUrl()
		{
			return driver.getCurrentUrl();
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
		
	    protected void takeScreenshot() {
				scenario.attach(scale(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), 375, 667), "image/png",
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
}
