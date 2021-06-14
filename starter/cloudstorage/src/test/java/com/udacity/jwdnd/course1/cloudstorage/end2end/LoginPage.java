package com.udacity.jwdnd.course1.cloudstorage.end2end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "error-msg")
    public WebElement errorMsg;

    @FindBy(id = "registration-msg")
    public WebElement registrationMsg;

    @FindBy(id = "logout-msg")
    public WebElement logoutMsg;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "signup-link")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loginUser(
            String Username,
            String Password
    ) {
        inputUsername.sendKeys(Username);
        inputPassword.sendKeys(Password);
    }

    public String getErrorMsg() {
        return errorMsg.getText() + "";
    }

    public void submit() {
        submitButton.click();
    }

    public void signupLink() {
        signupLink.click();
    }
}
