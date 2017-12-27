import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SignUpPage {
    private final WebDriver driver;
    public static final String url = Util.baseUrl + "Controller?action=signUpGet";

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }

    public List<String> getErrors() {
        final WebElement container = driver.findElement(By.className("alert-danger"));

        if (container == null) {
            return Collections.emptyList();
        }

        return container.findElements(By.cssSelector("li"))
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public void setUserId(String id) {
        Util.enterField(driver, "userid", id);
    }

    public String getUserId() {
        return Util.getValue(driver, "userid");
    }

    public void setFirstName(String firstName) {
        Util.enterField(driver, "firstName", firstName);
    }

    public String getFirstName() {
        return Util.getValue(driver, "firstName");
    }

    public void setLastName(String lastName) {
        Util.enterField(driver, "lastName", lastName);
    }

    public String getLastName() {
        return Util.getValue(driver, "lastName");
    }

    public void setEmail(String email) {
        Util.enterField(driver, "email", email);
    }

    public String getEmail() {
        return Util.getValue(driver, "email");
    }

    public void setPassword(String password) {
        Util.enterField(driver, "password", password);
    }

    public String getPassword() {
        return Util.getValue(driver, "password");
    }

    public void submit() {
        driver.findElement(By.id("signUp")).click();
    }
}
