package com.udacity.jwdnd.course1.cloudstorage.end2end;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SignupRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.util.WebDriverHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.inject.Inject;
import java.util.List;

import static com.udacity.jwdnd.course1.cloudstorage.util.FileResourceHelper.isFileDownloaded;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomeE2ETest {

    @LocalServerPort
    private int port;
    private static WebDriver driver;

    @Inject
    private UserMapper userMapper;

    @Inject
    private FileMapper fileMapper;

    @Inject
    private NoteMapper noteMapper;

    @Inject
    private CredentialMapper credentialMapper;

    private SignupPage signupPage;
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeEach
    public void beforeEach() {
        signupPage = new SignupPage(driver);
    }

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    static void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterEach
    public void afterEach() {
        credentialMapper.deleteAll();
        noteMapper.deleteAll();
        fileMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Test
    void check_logoutShouldRedirectToLogin() {
        var user = new SignupRequestDto("Francis", "azerty", "Francis", "Babier");
        driver.get("http://localhost:" + port + "/signup");
        signupPage.registerUser(user);
        signupPage.submit();
        signupPage.goToLogin();
        driver.get("http://localhost:" + port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.loginUser("Francis", "azerty");
        loginPage.submit();

        homePage = new HomePage(driver);
        homePage.logout();
        String redirected_url = driver.getCurrentUrl();
        driver.get(redirected_url);
        assertThat(redirected_url).contains("/login");
    }

    @Test
    void check_uploadAndViewFileShouldAppearInListFiles() {
        var user = new SignupRequestDto("kati", "azerty", "kati", "Babier");
        driver.get("http://localhost:" + port + "/signup");
        signupPage.registerUser(user);
        signupPage.submit();
        signupPage.goToLogin();
        driver.get("http://localhost:" + port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.loginUser("kati", "azerty");
        loginPage.submit();

        homePage = new HomePage(driver);
        homePage.fileUploadInput.sendKeys("/Users/houssemzaier/Documents/oiseauTest.pdf");
        homePage.fileUploadBtn.submit();

        homePage.viewBtn.sendKeys("/Users/houssemzaier/Downloads/oiseauTest.pdf");
        homePage.viewBtn.click();
        String redirected_url = driver.getCurrentUrl();
        driver.get(redirected_url);

        WebDriverHelper.wait_s(driver, 1_000);
        assertThat(isFileDownloaded("/Users/houssemzaier/Downloads", "oiseauTest")).isTrue();
        assertThat(redirected_url).contains("/home");

    }

    @Test
    void check_deleteFileShouldNotAppearInListFiles() {
        var user2 = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        userMapper.addUser(user2);
        driver.get("http://localhost:" + port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.loginUser("lucie", "azerty");
        loginPage.submit();

        homePage = new HomePage(driver);
        homePage.fileUploadInput.sendKeys("/Users/houssemzaier/Documents/oiseauTest.pdf");
        homePage.fileUploadBtn.submit();

        homePage.deleteBtn.click();
        assertThat(fileMapper.getAll(user2.getUserId())).isEmpty();
    }

    @Test
    void check_noteFeature() {
        var user2 = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        userMapper.addUser(user2);
        driver.get("http://localhost:" + port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.loginUser("lucie", "azerty");
        loginPage.submit();
        driver.get("http://localhost:" + port + "/home");

        /*Add new note Test block*/
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
        marker.sendKeys("Notes");
        marker.click();

        WebElement addNewNote = driver.findElement(By.id("add_new_note"));
        JavascriptExecutor jse1 = (JavascriptExecutor) driver;
        jse1.executeScript("arguments[0].click();", addNewNote);

        WebElement noteTitleInput = driver.findElement(By.id("note-title"));
        String inputText = "FIRST NOTE";
        noteTitleInput.getAttribute("noteTitle");
        JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        jse2.executeScript("arguments[1].value = arguments[0]; ", inputText, noteTitleInput);
        driver.switchTo().defaultContent();

        WebElement noteDescriptionTxtArea = driver.findElement(By.id("note-description"));
        String inputText2 = "This is a description of myNote";
        noteDescriptionTxtArea.getAttribute("noteDescription");
        JavascriptExecutor jse3 = (JavascriptExecutor) driver;
        jse3.executeScript("arguments[1].value = arguments[0]; ", inputText2, noteDescriptionTxtArea);
        driver.switchTo().defaultContent();

        WebElement noteSubmit = driver.findElement(By.id("noteSubmit"));
        JavascriptExecutor jse4 = (JavascriptExecutor) driver;
        jse4.executeScript("arguments[0].click();", noteSubmit);

        WebElement navNotes = driver.findElement(By.id("nav-notes-tab"));
        JavascriptExecutor jse5 = (JavascriptExecutor) driver;
        jse5.executeScript("arguments[0].click();", navNotes);

        WebElement successCrudNoteAdd =  driver.findElement(By.id("success-crud-note"));
        WebDriverHelper.wait_s(driver, 1_000);
        var msgSuccessAdd = successCrudNoteAdd.getText();

        List<Note> list = noteMapper.getAll(user2.getUserId());
        assertThat(list.get(0).getNoteTitle()).isEqualTo("FIRST NOTE");
        assertThat(msgSuccessAdd).isEqualTo("Your New Note was successfully added.");
        assertThat(list.get(0).getNoteDescription()).isEqualTo("This is a description of myNote");

        /*Edit Note Test Block*/
        WebDriverWait waitEdit = new WebDriverWait(driver, 1000);
        WebElement markerEdit = waitEdit.until(webDriver -> webDriver.findElement(By.id("nav-notes-tab")));
        markerEdit.sendKeys("Notes");
        markerEdit.click();

        WebElement editNoteBtn = driver.findElement(By.id("edit_note_btn"));
        JavascriptExecutor jse6 = (JavascriptExecutor) driver;
        jse6.executeScript("arguments[0].click();", editNoteBtn);

        WebElement noteTitleTxt2 = driver.findElement(By.id("note-title"));
        String inputTextEdit = "THIS EDIT TITLE";
        noteTitleTxt2.getAttribute("noteTitle");
        JavascriptExecutor jse7 = (JavascriptExecutor) driver;
        jse7.executeScript("arguments[1].value = arguments[0]; ", inputTextEdit, noteTitleTxt2);
        driver.switchTo().defaultContent();

        WebElement noteSubmitEdit = driver.findElement(By.id("noteSubmit"));
        JavascriptExecutor jse8 = (JavascriptExecutor) driver;
        jse8.executeScript("arguments[0].click();", noteSubmitEdit);

        WebElement navToNote = driver.findElement(By.id("nav-notes-tab"));
        JavascriptExecutor jseNavToNote = (JavascriptExecutor) driver;
        jseNavToNote.executeScript("arguments[0].click();", navToNote);

        WebElement successCrudNoteEdit =  driver.findElement(By.id("success-crud-note"));
        WebDriverHelper.wait_s(driver, 1_000);
        var msgSuccessEdit = successCrudNoteEdit.getText();

        assertThat(msgSuccessEdit).isEqualTo("Your changes was successfully saved.");
        List<Note> listEdit = noteMapper.getAll(user2.getUserId());
        assertThat(listEdit.get(0).getNoteTitle()).isEqualTo("THIS EDIT TITLE");

        /*Delete Note Test Block*/
        WebElement deleteNoteBtn = driver.findElement(By.id("delete_note"));
        JavascriptExecutor jse5Delete = (JavascriptExecutor) driver;
        jse5Delete.executeScript("arguments[0].click();", deleteNoteBtn);

        WebElement navToNote2 = driver.findElement(By.id("nav-notes-tab"));
        JavascriptExecutor jseNavToNote2 = (JavascriptExecutor) driver;
        jseNavToNote2.executeScript("arguments[0].click();", navToNote2);

        WebElement successCrudNoteDelete =  driver.findElement(By.id("success-crud-note"));
        WebDriverHelper.wait_s(driver, 1_000);

        var msgSuccessDelete = successCrudNoteDelete.getText();
        assertThat(msgSuccessDelete).isEqualTo("Your Note was successfully deleted.");
        List<Note> listDelete = noteMapper.getAll(user2.getUserId());
        assertThat(listDelete).isEmpty();

    }

    @Test
    void check_credentialFeature() {
        var user2 = new User(null, "lucie", "HIxi7PbCRU9uIyET6sdGEg==", "8H7jlDi3a2iPiu9ZI1+krA==", "lucie", "Babier");
        userMapper.addUser(user2);
        driver.get("http://localhost:" + port + "/login");

        loginPage = new LoginPage(driver);
        loginPage.loginUser("lucie", "azerty");
        loginPage.submit();
        driver.get("http://localhost:" + port + "/home");

        /*Add new credential Test block*/
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id("nav-credentials-tab")));
        marker.sendKeys("Credentials");
        marker.click();

        WebElement addNewCredential = driver.findElement(By.id("add_new_credential"));
        JavascriptExecutor jse1 = (JavascriptExecutor) driver;
        jse1.executeScript("arguments[0].click();", addNewCredential);

        WebElement credentialUrl = driver.findElement(By.id("credential-url"));
        String inputText = "https://intellij-support.jetbrains.com/hc/en-us";
        credentialUrl.getAttribute("url");
        JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        jse2.executeScript("arguments[1].value = arguments[0]; ", inputText, credentialUrl);
        driver.switchTo().defaultContent();

        WebElement credentialUsername = driver.findElement(By.id("credential-username"));
        String inputText2 = "Admin";
        credentialUsername.getAttribute("username");
        JavascriptExecutor jse3 = (JavascriptExecutor) driver;
        jse3.executeScript("arguments[1].value = arguments[0]; ", inputText2, credentialUsername);
        driver.switchTo().defaultContent();

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        String inputText3 = "1234";
        credentialUsername.getAttribute("password");
        JavascriptExecutor jse4 = (JavascriptExecutor) driver;
        jse4.executeScript("arguments[1].value = arguments[0]; ", inputText3, credentialPassword);
        driver.switchTo().defaultContent();

        WebElement credentialSubmit = driver.findElement(By.id("credentialSubmit"));
        JavascriptExecutor jse5 = (JavascriptExecutor) driver;
        jse5.executeScript("arguments[0].click();", credentialSubmit);

        WebElement navToCredentials = driver.findElement(By.id("nav-credentials-tab"));
        JavascriptExecutor jse6 = (JavascriptExecutor) driver;
        jse6.executeScript("arguments[0].click();", navToCredentials);

        List<Credential> list = credentialMapper.getAll(user2.getUserId());
        assertThat(list.get(0).getUrl()).isEqualTo("https://intellij-support.jetbrains.com/hc/en-us");
        assertThat(list.get(0).getUserName()).isEqualTo("Admin");

        /*Edit Credential Test Block*/
        WebDriverWait waitEdit = new WebDriverWait(driver, 1000);
        WebElement markerEdit = waitEdit.until(webDriver -> webDriver.findElement(By.id("nav-credentials-tab")));
        markerEdit.sendKeys("Credentials");
        markerEdit.click();

        WebElement editCredentialBtn = driver.findElement(By.id("edit_credential_btn"));
        JavascriptExecutor jse7 = (JavascriptExecutor) driver;
        jse7.executeScript("arguments[0].click();", editCredentialBtn);

        var staleElement = true;
        while (staleElement) {
            try {
                WebDriverWait t = new WebDriverWait(driver, 1000);
                WebElement credentialUsernameEdited = driver.findElement(By.xpath("//input[@id=\"credential-username\"]"));
                t.until(ExpectedConditions.visibilityOf(credentialUsernameEdited));
                t.until(ExpectedConditions.elementToBeClickable(credentialUsernameEdited));
                String inputTextEdited = "SimpleUser";
                credentialUsernameEdited.getAttribute("username");
                credentialUsernameEdited.clear();
                JavascriptExecutor jse8 = (JavascriptExecutor) driver;
                jse8.executeScript("arguments[1].value = arguments[0]; ", inputTextEdited, credentialUsernameEdited);
                driver.switchTo().defaultContent();
                staleElement = false;
            } catch (StaleElementReferenceException e) {
                staleElement = true;
            }
        }

        WebElement credentialSubmit2 = driver.findElement(By.id("credentialSubmit"));
        JavascriptExecutor jse9 = (JavascriptExecutor) driver;
        jse9.executeScript("arguments[0].click();", credentialSubmit2);

        List<Credential> listEdited = credentialMapper.getAll(user2.getUserId());
        assertThat(listEdited.get(0).getUserName()).isEqualTo("SimpleUser");

        /*Delete Credential Test Block*/
        WebElement deleteCredentialBtn = driver.findElement(By.id("delete_credential"));
        JavascriptExecutor jse10Delete = (JavascriptExecutor) driver;
        jse10Delete.executeScript("arguments[0].click();", deleteCredentialBtn);
        List<Note> listDelete = noteMapper.getAll(user2.getUserId());
        assertThat(listDelete).isEmpty();
    }

}
