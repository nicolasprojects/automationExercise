package com.automation;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.pages.ConfirmationModal;
import com.automation.pages.HomePage;

import junit.framework.Assert;

public class App extends Base
{

    /**
     * Verifies the header.
     */
    @Test(
        description = "test01"
    )
    public void verifyListCount() {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // Title count and actual list size should match
        Assert.assertEquals(hp.getCountFromTitle(), hp.getItemListCount());
        
        // Title should have correct format
        Assert.assertTrue(hp.getCountText().matches("List of items \\(\\d+\\)"));
    }
    
    /**
     * Verifies the addition of an accepted item.
     */
    @Test(
        description = "test02",
        dataProvider = "test02",
        dataProviderClass = Provider.class
    )
    public void addItemOk(String pImage, String pImageName, String pDescription) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // get count before addition
        int listCount = hp.getItemListCount();
        
        // add new item
        hp.getImageInput().sendKeys(pImage);
        hp.getTextArea().sendKeys(pDescription);
        hp.getCreateButton().click();
        
        // wait
        this.waitForListToHaveLength(listCount+1, hp.getItemList(), 10);
        
        // verify list size is increased by one element
        Assert.assertEquals(listCount+1, hp.getItemListCount());
        // verify title count matches with new list size
        Assert.assertEquals(hp.getCountFromTitle(), hp.getItemListCount());
        
        // verify the item was added to the list
        WebElement currentItem = hp.getLastItem();
        
        String imageName = hp.getImageNameFromItem(currentItem);
        String description = hp.getDescriptionFromItem(currentItem).getText();
        // image name and description should match
        Assert.assertEquals(pDescription, description);
        Assert.assertEquals(pImageName, imageName);
    }
    
    /**
     * Verifies item that does not meet requirements is not added
     */
    @Test(
    	description = "test03",
        dataProvider = "test03",
        dataProviderClass = Provider.class
    )
    public void addItem400px(String pImage, String pDescription) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);

        // get count before addition
        int listCount = hp.getItemListCount();
        
        // try to send a larger that accepted image
        hp.getImageInput().sendKeys(pImage);
        hp.getTextArea().sendKeys(pDescription);
        hp.getCreateButton().click();
        
        // wait
        this.waitForListToHaveLength(listCount+1, hp.getItemList(), 3);
        
        // verify item was not added
        Assert.assertEquals(listCount, hp.getItemListCount());
        Assert.assertEquals(hp.getCountFromTitle(), hp.getItemListCount());
    }
    
    /**
     * Verifies item that does not meet requirements is not added
     */
    @Test(
    	description = "test04",
        dataProvider = "test04",
        dataProviderClass = Provider.class
    )
    public void addItemDescription(String pImage) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);

        // get count before addition
        int listCount = hp.getItemListCount();
        
        // try to send a larger that accepted image
        hp.getImageInput().sendKeys(pImage);
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");//100
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");//200
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");
        hp.getTextArea().sendKeys("01234567890123456789012345678901234567890123456789");//300
        hp.getTextArea().sendKeys("1");
        hp.getCreateButton().click();

        // wait
        this.waitForListToHaveLength(listCount+1, hp.getItemList(), 3);
        
        // verify item was not added
        Assert.assertEquals(listCount, hp.getItemListCount());
        Assert.assertEquals(hp.getCountFromTitle(), hp.getItemListCount());
    }
    

    /**
     * Verifies alert is displayed
     */
    @Test(
    	description = "test05",
        dataProvider = "test05",
        dataProviderClass = Provider.class
    )
    public void alert(String pDescription) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // fill only the description
        hp.getTextArea().sendKeys(pDescription);
        hp.getCreateButton().click();
        
        // alert shows up
        Alert alert = driver.switchTo().alert();
        
        // verify alert message
        Assert.assertEquals("You must to select an image", alert.getText());
    }
    
    /**
     * Verify items can be deleted
     */
    @Test(
        description = "test06",
        dataProvider = "test06",
        dataProviderClass = Provider.class
    )
    public void deleteItem(String pImage, String pDescription) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);

        // get count before addition
        int listCount = hp.getItemListCount();
        
        // add a new item
        hp.getImageInput().sendKeys(pImage);
        hp.getTextArea().sendKeys(pDescription);
        hp.getCreateButton().click();

        // wait
        this.waitForListToHaveLength(listCount+1, hp.getItemList(), 10);
        
        // verify item was inserted
        Assert.assertEquals(listCount+1, hp.getItemListCount());
        
        // get inserted item
        WebElement currentItem = hp.getLastItem();
        
        // click on delete
        hp.getDeleteButtonFromItem(currentItem).click();
        
        // confirmation modal
        ConfirmationModal cm = PageFactory.initElements(driver, ConfirmationModal.class);
        
        // click on yes
        cm.getYesButton().click();
        
        // wait
        this.waitForListToHaveLength(listCount, hp.getItemList(), 10);
        
        // verify list goes back to what it was before addition 
        Assert.assertEquals(listCount, hp.getItemListCount());
        
        // verify element added cannot be found in the list
        for (WebElement item : hp.getItemList()){
            String description = hp.getDescriptionFromItem(item).getText();
            Assert.assertFalse(pDescription.equals(description));
        }
    }
    
    /**
     * Verify drag and drop functionality
     */
    @Test(
        description = "test07"
    )
    public void dragAndDrop() {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // get first and second elements
        WebElement firstElement = hp.getItemList().get(0);
        WebElement secondElement = hp.getItemList().get(1);
        
        // remember their texts
        String text1 = firstElement.getText();
        String text2 = secondElement.getText();
        
        // prepare the drag and drop action
        Actions builder = new Actions(driver);

        builder = builder.clickAndHold(firstElement);
        
        // movement will be slow due to elements being relocated while being dragged
        // we need to go from 0 to 400 in the y axis
        for (int i = 0; i<400; i=i+10){
        	builder = builder.moveToElement(secondElement, 800, i);
        }
        builder = builder.release(null);
        Action dragAndDrop = builder.build();
        
        // perform the action
        dragAndDrop.perform();
        sleep(500);
        
        // verify first and second items are swapped
        Assert.assertEquals(text1, hp.getItemList().get(1).getText());
        Assert.assertEquals(text2, hp.getItemList().get(0).getText());
    }

    /**
     * Bug. User cannot update image. 
     * This test should pass once the functionality is fixed.
     * Verify user can update an item. (image and description)
     */
    @Test(
        description = "bug01",
        dataProvider = "bug01",
        dataProviderClass = Provider.class
    )
    public void updateItem(	String pImage1,
    						String pImageName1,
    						String pDescription1,
    						String pImage2,
    						String pImageName2,
    						String pDescription2
    						) {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);

        // get count before addition
        int listCount = hp.getItemListCount();
        
        // add a new item
        hp.getImageInput().sendKeys(pImage1);
        hp.getTextArea().sendKeys(pDescription1);
        hp.getCreateButton().click();

        // wait
        this.waitForListToHaveLength(listCount+1, hp.getItemList(), 10);
        
        // we will work with the new item
        WebElement currentItem = hp.getLastItem();
        
        // verify it is the correct item
        String imageName = hp.getImageNameFromItem(currentItem);
        String description = hp.getDescriptionFromItem(currentItem).getText();
        Assert.assertEquals(pImageName1, imageName);
        Assert.assertEquals(pDescription1, description);
        
        // click edit button
        hp.getEditButtonFromItem(currentItem).click();
        
        // update our item with new data
        // change image
        hp.getImageInput().sendKeys(pImage2);
        // change description
        hp.getTextArea().clear();
        hp.getTextArea().sendKeys(pDescription2);
        hp.getUpdateButton().click();
        
        this.waitUntilStale(currentItem);
        
        // verify our changes
        currentItem = hp.getLastItem();
        
        // image and description should be the new ones
        imageName = hp.getImageNameFromItem(currentItem);
        description = hp.getDescriptionFromItem(currentItem).getText();
        Assert.assertEquals(pDescription2, description);
        Assert.assertEquals(pImageName2, imageName);
    }

    /**
     * Bug. Modal has wording errors.
     * This test should pass once the functionality is fixed.
     * Verify wording.
     */
    @Test(
        description = "bug02"
    )
    public void deleteModal() {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // get one item
        WebElement currentItem = hp.getLastItem();
        
        // open the delete modal
        hp.getDeleteButtonFromItem(currentItem).click();
        
        ConfirmationModal cm = PageFactory.initElements(driver, ConfirmationModal.class);
        
        // verify wording
        Assert.assertEquals("Warning!", cm.getTitleText());
        Assert.assertEquals("Are you sure you want to delete this item?", cm.getBodyText());
        Assert.assertEquals("Yes, delete it!", cm.getYesButtonText());
        Assert.assertEquals("Cancel", cm.getCancelButtonText());
    }
    
    /**
     * Bug. Error alerts for image are not being displayed.
     * This test should pass once the functionality is fixed.
     * Verify Item Details elements.
     */
    @Test(
        description = "bug03"
    )
    public void itemDetails() {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);
        
        // verify every element from the Item Details form
        Assert.assertTrue(hp.getImageInput().isDisplayed());
        Assert.assertTrue(hp.getTextArea().isDisplayed());
        Assert.assertTrue(hp.getCreateButton().isDisplayed());
        // cancel button should not be displayed
        Assert.assertFalse(this.isElementVisible(hp.getCancelButton()));
        // update button should not be displayed
        Assert.assertFalse(this.isElementVisible(hp.getUpdateButton()));
        
        Assert.assertEquals("Item Details", hp.getDetailsTitle().getText());
        Assert.assertEquals("*Image 320px x 320px", hp.getImageLabel().getText());
        Assert.assertEquals("*required", hp.getFileRequiredAlert().getText());
        Assert.assertEquals("*max width: 320px", hp.getMaxWidthAlert().getText());
        Assert.assertEquals("*max height: 320px", hp.getMaxHeightAlert().getText());
        Assert.assertEquals("*Text", hp.getTextareaLabel().getText());
        Assert.assertEquals("*required", hp.getTextAlert().getText());
    }
    
    /**
     * Bug. Edit deleted item.
     * This test should pass once the functionality is fixed.
     * Verify Item Details elements.
     */
    @Test(
        description = "bug04",
        // disabled because it breaks the application
        enabled = false
    )
    public void editDeletedItem() {
        driver.get("http://immense-hollows-74271.herokuapp.com");
        HomePage hp = PageFactory.initElements(driver, HomePage.class);

        // we will work with the last item
        WebElement currentItem = hp.getLastItem();

        // click edit button
        hp.getEditButtonFromItem(currentItem).click();

        // click on delete
        hp.getDeleteButtonFromItem(currentItem).click();
        
        // confirmation modal
        ConfirmationModal cm = PageFactory.initElements(driver, ConfirmationModal.class);
        
        // click on yes
        cm.getYesButton().click();
        sleep(1000);
        

        // update button should not be displayed
        Assert.assertFalse(this.isElementVisible(hp.getUpdateButton()));
    }
}
