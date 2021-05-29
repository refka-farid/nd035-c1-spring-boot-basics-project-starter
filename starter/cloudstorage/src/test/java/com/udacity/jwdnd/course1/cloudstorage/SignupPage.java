package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.models.UserUiDto;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "login-link")
    private WebElement loginLink;

    @FindBy(id = "success-msg")
    private WebElement successMsg;

    @FindBy(id = "error-msg")
    private WebElement errorMsg;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void registerUser(
            UserUiDto user
    ) {
        inputFirstName.sendKeys(user.getFirstName());
        inputLastName.sendKeys(user.getLastName());
        inputUserName.sendKeys(user.getUserName());
        inputPassword.sendKeys(user.getPassword());
    }

    public String getSuccessMsg() {
        return successMsg.getText() + "";
    }

    public String getErrorMsg() {
        return errorMsg.getText() + "";
    }

    public void submit() {
        submitButton.click();
    }

    public void goToLogin() {
        loginLink.click();
    }
}
