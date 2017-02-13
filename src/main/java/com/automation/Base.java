package com.automation;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * This class contains before and after methods, and helper functions
 */
public class Base {

    WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(ITestContext context){
        try {
            driver = new FirefoxDriver();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestContext context){
        try {
            driver.close();
            driver.quit();
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public void sleep(int ms){
        try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
    }

    /**
     * Tests the list size during <code>seconds</code> seconds.
     * @return true if list reached specified length.
     */
    public boolean waitForListToHaveLength(int elementExpectedLength, List<WebElement> list, int tries){
        int timeout=0;
        
        // milliseconds pause
        int pause = 500;
        
        while (list.size() != elementExpectedLength && timeout < tries*1000/pause){
            this.sleep(pause);
            timeout++;
        }
        return list.size() == elementExpectedLength;
    }

    public void waitUntilStale(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
    	wait.until(ExpectedConditions.stalenessOf(element));
    }

    public boolean isElementVisible(WebElement element){
    	boolean isDisplayed = false;
        try{
            isDisplayed = element.isDisplayed();
        }catch(NoSuchElementException e){
        	return false;
        }
        return isDisplayed;
    }
    
}
