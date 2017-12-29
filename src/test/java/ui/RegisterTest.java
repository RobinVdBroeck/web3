package ui;

import domain.Person;
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
import ui.pages.UserOverviewPage;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegisterTest {
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

        final String firstName = "Jan";
        final String lastName = "Jansens";
        final String email = "jan.janssens@hotmail.com";

        signUpPage.open();
        signUpPage.setUserId(useridRandom);
        signUpPage.setFirstName(firstName);
        signUpPage.setLastName(lastName);
        signUpPage.setEmail(email);
        signUpPage.setPassword("1234");
        signUpPage.submit();

        String title = driver.getTitle();
        assertEquals("Home", title);

        final UserOverviewPage userOverviewPage = new UserOverviewPage(driver);
        userOverviewPage.open();
        List<Person> table = userOverviewPage.getTable();
        Person found = table.stream()
            .filter(p -> p.getEmail().equals(email) && p.getFirstName().equals(firstName) && p.getLastName().equals(lastName))
            .findAny()
            .get();
        assertNotNull(found);

        // Delete it
        userOverviewPage.delete(found.getUserid());
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

        // Delete the user
        final UserOverviewPage userOverviewPage = new UserOverviewPage(driver);
        userOverviewPage.open();
        userOverviewPage.delete(userId);
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
