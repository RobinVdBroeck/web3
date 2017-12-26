import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private final static String signUp = "http://localhost:8080/shop-web/Controller?action=signUp";
    private final static String users = "http://localhost:8080/shop-web/Controller?action=users";

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

    private void fillOutField(WebDriver driver, String name, String value) {
        WebElement field = driver.findElement(By.id(name));
        field.clear();
        field.sendKeys(value);
    }

    private void submitForm(WebDriver driver, String userid, String firstName, String lastName, String email, String password) {
        fillOutField(driver, "userid", userid);
        fillOutField(driver, "firstName", firstName);
        fillOutField(driver, "lastName", lastName);
        fillOutField(driver, "email", email);
        fillOutField(driver, "password", password);

        WebElement button = driver.findElement(By.id("signUp"));
        button.click();
    }


    @Test
    public void testRegisterCorrect() {
        driver.get(signUp);

        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
        submitForm(driver, useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

        String title = driver.getTitle();
        assertEquals("Home", title);

        driver.get(users);

        List<WebElement> listItems = driver.findElements(By.cssSelector("table tr"));
        boolean found = listItems.stream()
            .map(WebElement::getText)
            .anyMatch(text -> text.contains("jan.janssens@hotmail.com") && text.contains("Jan Janssens"));

        assertEquals(true, found);
    }

    @Test
    public void testRegisterUseridEmpty() {
        driver.get(signUp);

        submitForm(driver, "", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("No userid given", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals("", fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("Jan", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("Janssens", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));


    }

    @Test
    public void testRegisterFirstNameEmpty() {
        driver.get(signUp);

        submitForm(driver, "jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("No firstname given", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals("jakke", fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("Janssens", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));


    }

    @Test
    public void tSelestRegisterLastNameEmpty() {
        driver.get(signUp);
        submitForm(driver, "jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("No last name given", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals("jakke", fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("Jan", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));


    }

    @Test
    public void testRegisterEmailEmpty() {
        driver.get(signUp);

        submitForm(driver, "jakke", "Jan", "Janssens", "", "1234");

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("No email given", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals("jakke", fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("Jan", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("Janssens", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("", fieldEmail.getAttribute("value"));
    }


    @Test
    public void testRegisterPasswordEmpty() {
        driver.get(signUp);

        submitForm(driver, "jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("No password given", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals("jakke", fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("Jan", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("Janssens", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("jan.janssens@hotmail.com", fieldEmail.getAttribute("value"));


    }

    @Test
    public void testRegisterUserAlreadyExists() {
        driver.get(signUp);

        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");
        submitForm(driver, useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

        driver.get(signUp);
        submitForm(driver, useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals("User already exists", errorMsg.getText());

        WebElement fieldUserid = driver.findElement(By.id("userid"));
        assertEquals(useridRandom, fieldUserid.getAttribute("value"));

        WebElement fieldFirstName = driver.findElement(By.id("firstName"));
        assertEquals("Pieter", fieldFirstName.getAttribute("value"));

        WebElement fieldLastName = driver.findElement(By.id("lastName"));
        assertEquals("Pieters", fieldLastName.getAttribute("value"));

        WebElement fieldEmail = driver.findElement(By.id("email"));
        assertEquals("pieter.pieters@hotmail.com", fieldEmail.getAttribute("value"));
    }

    @Test
    public void testAllFieldsEmptyWhenNothingEntered() {
        driver.get(signUp);

        List<WebElement> fields = driver.findElements(By.cssSelector("form p input:not(:last-child)"));

        fields.stream()
            .map(element -> element.getAttribute("value"))
            .forEach(value -> assertEquals("", value));
    }
}
