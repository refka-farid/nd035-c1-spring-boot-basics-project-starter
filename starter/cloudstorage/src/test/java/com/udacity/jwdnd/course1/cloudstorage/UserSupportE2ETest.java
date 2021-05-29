package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserUiDto;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserSupportE2ETest {

    @LocalServerPort
    private int port;

    @Inject
    private UserMapper mapper;

    private static WebDriver driver;
    private SignupPage signupPage;

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
        mapper.deleteAll();
    }

    @Test
    void registerNewUserShouldShowSuccess() {
        var user = new UserUiDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();
        String prevValue = signupPage.getSuccessMsg();
        assertEquals("You successfully signed up! Please continue to the login page.", prevValue);
    }

    @Test
    void registerNewUserShouldShowError() {
        var user = new UserUiDto("Francis", "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user);
        signupPage.submit();
        signupPage.registerUser(user);
        signupPage.submit();

        String prevValue = signupPage.getErrorMsg();
        assertEquals("The username already exists.", prevValue);
    }

    @Test
    public void check() {
        // TODO: 30/05/2021 add two tests for login success and login fail
    }

    @Test
    public void check_pageTitle() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

//	@Test
//    public void getLoginPage() {
//        driver.get("http://localhost:" + this.port + "/login");
//        Assertions.assertEquals("Login", driver.getTitle());
//    }
}
