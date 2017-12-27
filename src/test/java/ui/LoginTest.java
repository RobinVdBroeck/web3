package ui;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
import ui.pages.SignUpPage;

public class LoginTest {
    private WebDriver driver;
    private String useridRandom;
    private final String password = "1234";
    private final String firstname = "Jan";
    private final String url = "http://localhost:8080/shop-web/Controller";

    @BeforeClass
    private static void setupClass() {
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

    private void fillOutField(String name, String value) {
        WebElement field = driver.findElement(By.id(name));
        field.clear();
        field.sendKeys(value);
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

    private void logIn(String userid, String password) {
        fillOutField("userid", userid);
        fillOutField("password", password);

        WebElement button = driver.findElement(By.id("logIn"));
        button.click();
    }

    private boolean findTekstWelcome() {
        ArrayList<WebElement> paragraphs = (ArrayList<WebElement>) driver.findElements(By.tagName("p"));
        Boolean found = false;
        for (WebElement webElement : paragraphs) {
            if (webElement.getText().equals("Welcome, " + firstname + ".")) {
                found = true;
            }
        }
        return found;
    }


    @Test
    public void testCorrectLoginHomepageWelcomeTextLogoutButton() {
        driver.get(Util.baseUrl);
        logIn(useridRandom, password);
        assertEquals("Home", driver.getTitle());

        assertTrue(findTekstWelcome());

        // loginknop aanwezig
        assertNotNull(driver.findElement(By.id("logOut")));
    }

    @Test
    public void testKeepedLoggedInWhenVisitedOtherPage() {
        driver.get(Util.baseUrl);
        logIn(useridRandom, password);

        driver.get(url + "?action=overview");
        driver.get(url + "?action=addProduct");
        driver.get(url);

        assertTrue(findTekstWelcome());
        assertNotNull(driver.findElement(By.id("logOut")));

    }

}
