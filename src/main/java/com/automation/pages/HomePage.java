package com.automation.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The home page
 */
public class HomePage {
	
	// Header
    @FindBy(css = "h1")
    private WebElement itemCount;
    
    // List
    @FindBy(css = "ul.media-list li")
    private List<WebElement> itemList;
	
    // Row elements
    private By imageBy = By.cssSelector("figure img");
    private By descriptionBy = By.cssSelector(".media-body p");
    private By editBy = By.cssSelector(".media-body button[ng-click*='set']");
    private By deleteBy = By.cssSelector(".media-body button[ng-click*='open']");
    
    // Form
    // Title
    @FindBy(css = ".details h3")
    private WebElement detailsTitle;
    
    // Image 
    // Label
    @FindBy(css = ".details label[for='inputTitle']")
    private WebElement imageLabel;
    // Input
    @FindBy(id = "inputImage")
    private WebElement inputImage;
    // Alerts
    @FindBy(css = ".controls i[ng-show*='imageSrc.$error.required']")
    private WebElement fileRequiredAlert;
    
    @FindBy(css = ".controls i[ng-show*='maxWidth']")
    private WebElement maxWidthAlert;
    
    @FindBy(css = ".controls i[ng-show*='maxHeight']")
    private WebElement maxHeightAlert;
    
    // Description
    // Label
    @FindBy(css = ".details label[for='inputText']")
    private WebElement textareaLabel;
    // Input
    @FindBy(css = "textarea")
    private WebElement textarea;
    // Alert
    @FindBy(css = ".controls i[ng-show*='text']")
    private WebElement textAlert;
    
    // Buttons
    @FindBy(css = "form[name='strangerlist.detailsForm'] button.btn-success")
    private WebElement createButton;
    @FindBy(css = "form[name='strangerlist.detailsForm'] button.btn-default")
    private WebElement cancelButton;
    @FindBy(css = "form[name='strangerlist.detailsForm'] button.btn-primary")
    private WebElement updateButton;
    
    
    
    WebDriver driver;
    
    public HomePage(WebDriver driver){
    	this.driver = driver;
    }
    
    
    
    // Header
    public int getCountFromTitle(){
    	String text = itemCount.getText();
    	String number = text.substring(text.indexOf('(')+1, text.indexOf(')')); 
    	return Integer.parseInt(number);
    }
    public String getCountText(){
    	String text = itemCount.getText();
    	return text;
    }
    
    // List
    public List<WebElement> getItemList(){
    	return itemList;
    }
    public int getItemListCount(){
    	return itemList.size();
    }
    
    public WebElement getLastItem(){
    	if (itemList.size()>0){
    		return itemList.get(itemList.size()-1);
    	}else{
    		return null;
    	}
    }
    
    // List Item
    public WebElement getImageFromItem(WebElement item){
    	return item.findElement(imageBy);
    }
    public WebElement getDescriptionFromItem(WebElement item){
    	return item.findElement(descriptionBy);
    }
    public WebElement getEditButtonFromItem(WebElement item){
    	return item.findElement(editBy);
    }
    public WebElement getDeleteButtonFromItem(WebElement item){
    	return item.findElement(deleteBy);
    }
    
    public String getImageNameFromItem(WebElement item){
    	String src = getImageFromItem(item).getAttribute("src");
    	return src.substring(src.lastIndexOf('/')+1);
    }


    // Details
    public WebElement getImageInput(){
    	return inputImage;
    }
    public WebElement getTextArea(){
    	return textarea;
    }
    public WebElement getCreateButton(){
    	return createButton;
    }
    public WebElement getCancelButton(){
    	return cancelButton;
    }
    public WebElement getUpdateButton(){
    	return updateButton;
    }


    public WebElement getDetailsTitle(){
    	return detailsTitle;
    }
    public WebElement getImageLabel(){
    	return imageLabel;
    }
    public WebElement getFileRequiredAlert(){
    	return fileRequiredAlert;
    }
    public WebElement getMaxWidthAlert(){
    	return maxWidthAlert;
    }
    public WebElement getMaxHeightAlert(){
    	return maxHeightAlert;
    }
    public WebElement getTextareaLabel(){
    	return textareaLabel;
    }
    public WebElement getTextAlert(){
    	return textAlert;
    }
    

    
}
