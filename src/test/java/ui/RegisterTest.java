package ui;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.SignUpPage;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private final static String users = "http://localhost:8080/shop-web/Controller?action=users";
    private SignUpPage signUpPage;

    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        signUpPage = new SignUpPage(driver);
    }

    @After
    public void teardown() {
        driver.quit();
        signUpPage = null;
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }

    private void testFieldStayTheSameAndErrorMessage(String userId, String firstName, String lastName, String email,
                                                     String password, String errormessage) {
        signUpPage.open();
        signUpPage.setUserId(userId);
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setEmail(email);
        signUpPage.setPassword(password);
        signUpPage.submit();

        String title = driver.getTitle();
        assertEquals("Sign Up", title);

        // TODO: refactor this into ui.pages.SignUpPage
        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        assertEquals(errormessage, errorMsg.getText());

        assertFieldsStayTheSame(userId, firstName, lastName, email, password);
    }

    private void assertFieldsStayTheSame(String userId, String firstName, String lastName, String email, String password) {
        assertEquals(userId, signUpPage.getUserId());
        assertEquals(firstName, signUpPage.getFirstName());
        assertEquals(lastName, signUpPage.getLastName());
        assertEquals(email, signUpPage.getEmail());
        assertEquals(password, signUpPage.getPassword());
    }


    @Test
    public void testRegisterCorrect() {
        String useridRandom = generateRandomUseridInOrderToRunTestMoreThanOnce("jakke");

        signUpPage.open();
        signUpPage.setUserId(useridRandom);
        signUpPage.setFirstName("Jan");
        signUpPage.setLastName("Jansens");
        signUpPage.setEmail("jan.janssens@hotmail.com");
        signUpPage.setPassword("1234");
        signUpPage.submit();

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
        testFieldStayTheSameAndErrorMessage(
            "",
            "Jan",
            "Jansens",
            "jan.janssens@hotmail.com",
            "1234",
            "No userid given"
        );
    }

    @Test
    public void testRegisterFirstNameEmpty() {
        testFieldStayTheSameAndErrorMessage(
            "Jakke",
            "",
            "Jansens",
            "jan.janssens@hotmail.com",
            "1234",
            "No firstname given"
        );
    }

    @Test
    public void testRegisterLastNameEmpty() {
        testFieldStayTheSameAndErrorMessage(
            "Jakke",
            "Jan",
            "",
            "jan.janssens@hotmail.com",
            "1234",
            "No last name given"
        );
    }

    @Test
    public void testRegisterEmailEmpty() {
        testFieldStayTheSameAndErrorMessage(
            "Jakke",
            "Jan",
            "Jansens",
            "",
            "1234",
            "No email given"
        );
    }


    @Test
    public void testRegisterPasswordEmpty() {
        testFieldStayTheSameAndErrorMessage(
            "Jakke",
            "Jan",
            "Jansens",
            "jan.janssens@hotmail.com",
            "",
            "No password given"
        );
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        final String userId = generateRandomUseridInOrderToRunTestMoreThanOnce("pierke");
        final String firstName = "Pieter";
        final String lastName = "Pieters";
        final String email = "pieter.pieters@hotmail.com";
        final String password = "1234";

        // Register user for the first time
        signUpPage.open();
        signUpPage.setUserId(userId);
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setEmail(email);
        signUpPage.setPassword(password);
        signUpPage.submit();

        testFieldStayTheSameAndErrorMessage(
            userId,
            firstName,
            lastName,
            email,
            password,
            "User already exists"
        );
    }

    @Test
    public void testAllFieldsEmptyWhenNothingEntered() {
        driver.get(SignUpPage.url);

        List<WebElement> fields = driver.findElements(By.cssSelector("form p input:not(:last-child)"));

        fields.stream()
            .map(element -> element.getAttribute("value"))
            .forEach(value -> assertEquals("", value));
    }
}
