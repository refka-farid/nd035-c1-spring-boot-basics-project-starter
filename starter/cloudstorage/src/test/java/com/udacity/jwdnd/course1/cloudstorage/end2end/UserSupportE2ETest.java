package com.udacity.jwdnd.course1.cloudstorage.end2end;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SignupRequestDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserSupportE2ETest {

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

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeEach
    public void beforeEach() {
        check_pageTitle();
        signupPage = new SignupPage(driver);
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
        credentialMapper.deleteAll();
        fileMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Test
    void registerNewUserShouldRedirectToLogin() {
        var user = new SignupRequestDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();

        String redirected_url = driver.getCurrentUrl();
        assertThat(redirected_url).contains("/login");

        loginPage = new LoginPage(driver);
        var successMsg = loginPage.registrationMsg.getText();
        assertEquals("You successfully signed up!", successMsg);

    }

    @Test
    void registerNewUserShouldShowError() {
        var user = new SignupRequestDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();

        loginPage = new LoginPage(driver);
        loginPage.signupLink();

        signupPage.registerUser(user);
        signupPage.submit();

        String prevValue = signupPage.getErrorMsg();
        assertEquals("The username already exists.", prevValue);
    }

    @Test
    void check_loginShouldShowError() {
        var user = new SignupRequestDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();

        loginPage = new LoginPage(driver);
        loginPage.loginUser("Franci", "1234Hashed");
        loginPage.submit();
        assertThat(loginPage.errorMsg.isDisplayed()).isTrue();
    }

    @Test
    void check_loginShouldShowSuccess() {
        var user = new SignupRequestDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();

        loginPage = new LoginPage(driver);
        loginPage.loginUser("Francis", "1234Hashed");
        loginPage.submit();
        String redirected_url = driver.getCurrentUrl();
        driver.get(redirected_url);
        assertThat(redirected_url).contains("/home");
    }

    @Test
    void check_pageTitle() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
