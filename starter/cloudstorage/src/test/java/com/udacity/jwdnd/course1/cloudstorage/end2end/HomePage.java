package com.udacity.jwdnd.course1.cloudstorage.end2end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-btn")
    public WebElement logoutBtn;

    @FindBy(id = "fileUpload")
    public WebElement fileUploadInput;

    @FindBy(id = "upload-btn")
    public WebElement fileUploadBtn;

    @FindBy(id = "error-msg")
    public WebElement errorMsg;

    @FindBy(id = "view_btn")
    public WebElement viewBtn;

    @FindBy(id = "delete_btn")
    public WebElement deleteBtn;

    @FindBy(id = "file_name")
    public WebElement fileName;

    @FindBy(id = "file_tabel_body")
    public WebElement fileTabelBody;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logout() {
        logoutBtn.click();
    }

    public void downloadFile() {
        viewBtn.click();
    }

    public void deleteFile() {
        deleteBtn.click();
    }
}
