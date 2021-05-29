package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
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
        getSignupPage();
        signupPage = new SignupPage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
        mapper.deleteAll();
    }

    @Test
    void registerNewUser() {
        User user = new User(null, "Francis", null, "1234Hashed", "Francis", "Babier");
        signupPage.registerUser(user.getFirstName(), user.getLastName(), user.getUserName(), user.getHashedPassword());
        signupPage.submit();
        String prevValue = signupPage.getSuccessMsg();
        assertEquals("You successfully signed up! Please continue to the login page.", prevValue);
//        mapper.deleteUser(user);
    }

    @Test
    public void getSignupPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

//	@Test
//    public void getLoginPage() {
//        driver.get("http://localhost:" + this.port + "/login");
//        Assertions.assertEquals("Login", driver.getTitle());
//    }
}
