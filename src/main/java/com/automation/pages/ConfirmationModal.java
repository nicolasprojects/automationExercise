package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * The delete confirmation modal
 */
public class ConfirmationModal {
	
	
    @FindBy(css = ".modal")
    private WebElement modal;

    @FindBy(css = ".modal-title")
    private WebElement title;

    @FindBy(css = ".modal-body p")
    private WebElement body;
    
    @FindBy(css = ".modal-footer .btn-primary")
    private WebElement yesButton;
    
    @FindBy(css = ".modal-footer .btn-warning")
    private WebElement cancelButton;
    
    
    
    WebDriver driver;
    
    public ConfirmationModal(WebDriver driver){
    	this.driver = driver;
    }
    
    

    public String getTitleText(){
    	return title.getText();
    }
    public String getBodyText(){
    	return body.getText();
    }
    public String getYesButtonText(){
    	return yesButton.getText();
    }
    public String getCancelButtonText(){
    	return cancelButton.getText();
    }
    

    public WebElement getYesButton(){
    	return yesButton;
    }
    
}
