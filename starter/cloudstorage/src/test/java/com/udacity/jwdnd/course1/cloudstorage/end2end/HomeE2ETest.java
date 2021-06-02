package com.udacity.jwdnd.course1.cloudstorage.end2end;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.SignupRequestDto;
import com.udacity.jwdnd.course1.cloudstorage.util.WebDriverHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import javax.inject.Inject;

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
}
