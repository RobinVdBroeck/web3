import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {
    private final WebDriver driver;
    private final String url = Util.baseUrl + "Controller?action=signUp";

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
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
