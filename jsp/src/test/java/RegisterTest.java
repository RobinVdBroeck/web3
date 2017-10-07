import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "C:\\Webdriver\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/shop-web/Controller?action=signUp");
    }

    @After
    public void clean() {
        driver.quit();
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

    private void fillOutField(String name, String value) {
        WebElement field = driver.findElement(By.id(name));
        field.clear();
        field.sendKeys(value);
    }

    private void submitForm(String userid, String firstName, String lastName, String email, String password) {
        fillOutField("userid", userid);
        fillOutField("firstName", firstName);
        fillOutField("lastName", lastName);
        fillOutField("email", email);
        fillOutField("password", password);

        WebElement button = driver.findElement(By.id("signUp"));
        button.click();
    }


    @Test
    public void testRegisterCorrect() {
        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");
        submitForm(useridRandom, "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

        String title = driver.getTitle();
        assertEquals("Home", title);

        driver.get("http://localhost:8080/shop-web/Controller?action=overview");

        List<WebElement> listItems = driver.findElements(By.cssSelector("table tr"));
        boolean found = listItems.stream()
            .map(WebElement::getText)
            .anyMatch(text -> text.contains("jan.janssens@hotmail.com") && text.contains("Jan Janssens"));

        assertEquals(true, found);

    }

    @Test
    public void testRegisterUseridEmpty() {
        submitForm("", "Jan", "Janssens", "jan.janssens@hotmail.com", "1234");

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
        submitForm("jakke", "", "Janssens", "jan.janssens@hotmail.com", "1234");

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
    public void testRegisterLastNameEmpty() {
        submitForm("jakke", "Jan", "", "jan.janssens@hotmail.com", "1234");

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
        submitForm("jakke", "Jan", "Janssens", "", "1234");

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
        submitForm("jakke", "Jan", "Janssens", "jan.janssens@hotmail.com", "");

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
        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");
        submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

        driver.get("http://localhost:8080/shop-web/Controller?action=signUp");

        submitForm(useridRandom, "Pieter", "Pieters", "pieter.pieters@hotmail.com", "1234");

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
        driver.get("http://localhost:8080/shop-web/Controller?action=signUp");

        List<WebElement> fields = driver.findElements(By.cssSelector("form p input:not(:last-child)"));

        fields.stream()
            .map(element -> element.getAttribute("value"))
            .forEach(value -> assertEquals("", value));
    }
}
