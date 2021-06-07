package com.udacity.jwdnd.course1.cloudstorage.end2end;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SignupRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.util.WebDriverHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
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
    void check_createNoteShouldAppear() {
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

        List<Note> list = noteMapper.getAll(user2.getUserId());
        assertThat(list.get(0).getNoteTitle()).isEqualTo("FIRST NOTE");
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

        List<Note> listEdit = noteMapper.getAll(user2.getUserId());
        assertThat(listEdit.get(0).getNoteTitle()).isEqualTo("THIS EDIT TITLE");

        /*Delete Note Test Block*/
        WebElement deleteNoteBtn = driver.findElement(By.id("delete_note"));
        JavascriptExecutor jse5Delete = (JavascriptExecutor) driver;
        jse5Delete.executeScript("arguments[0].click();", deleteNoteBtn);
        List<Note> listDelete = noteMapper.getAll(user2.getUserId());
        assertThat(listDelete).isEmpty();

//        😂
    }
}
