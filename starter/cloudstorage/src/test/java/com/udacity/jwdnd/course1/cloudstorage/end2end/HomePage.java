package com.udacity.jwdnd.course1.cloudstorage.end2end;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "logout-btn")
    public WebElement logoutBtn;

    /*nav-file*/

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

    /*nav-note*/

    @FindBy(id = "nav-notes-tab")
    public WebElement navNotes;

    @FindBy(id = "add_new_note")
    public WebElement addNewNote;

    @FindBy(id = "edit_note_btn")
    public WebElement editNoteBtn;

    @FindBy(id = "delete_note")
    public WebElement deleteNoteBtn;

    @FindBy(id = "noteSubmit")
    public WebElement submitNote;

    @FindBy(id = "note-title")
    public WebElement noteTitleInput;

    @FindBy(id = "note-description")
    public WebElement noteDescriptionTextArea;

    @FindBy(id = "note_title_txt")
    public WebElement noteTitleTxt;

    @FindBy(id = "note_description_txt")
    public WebElement noteDescriptionTxt;

    /*nav-credentials*/
    @FindBy(id = "nav-credentials-tab")
    public WebElement navCredentials;


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
