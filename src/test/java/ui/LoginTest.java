package ui;

import static org.junit.Assert.*;

import java.util.stream.Stream;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.*;

public class LoginTest {
    private WebDriver driver;
    private String useridRandom;
    private final String password = "1234";
    private final String firstname = "Jan";

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        registerUser();
    }

    @After
    public void clean() {
        // deleteUser(useridRandom);
        driver.quit();
    }


    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

    private void registerUser() {
        useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");

        final SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.open();
        signUpPage.setUserId(useridRandom);
        signUpPage.setFirstName(firstname);
        signUpPage.setLastName("Janssens");
        signUpPage.setEmail("jan.janssens@hotmail.com");
        signUpPage.setPassword(password);
        signUpPage.submit();
    }

    private void deleteUser(String userid) {
        throw new NotImplementedException("Not implemented user deletion yet");
        /*
        // delete test user
        driver.get(url + "?action=overview");
        String cssSelector = "a[href*='deletePerson&userId=" + userid + "']";
        WebElement link = driver.findElement(By.cssSelector(cssSelector));
        link.click();

        WebElement deleteButtonConfirm = driver.findElement(By.cssSelector("input[type='submit'"));
        deleteButtonConfirm.click();
        */
    }


    private boolean findTextWelcome() {
        Stream<WebElement> elements = Stream.of(driver.findElement(By.tagName("p")));
        return elements.map(WebElement::getText)
            .anyMatch(e -> e.equals("Welcome, " + firstname + "."));
    }


    @Test
    public void testCorrectLoginHomepageWelcomeTextLogoutButton() {
        final LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(useridRandom, password);
        assertEquals("Home", driver.getTitle());

        assertTrue(findTextWelcome());

        // logout aanwezig
        assertNotNull(driver.findElement(By.id("logoutNav")));
    }

    @Test
    public void testKeepedLoggedInWhenVisitedOtherPage() {
        final LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.login(useridRandom, password);

        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final HomePage homePage = new HomePage(driver);
        homePage.open();

        assertTrue(findTextWelcome());
        assertNotNull(driver.findElement(By.id("logoutNav")));
    }

}
