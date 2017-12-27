package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.Util;

public class LoginPage {
    private final WebDriver driver;
    private static final String url = Util.baseUrl + "Controller?action=loginGet";

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }

    public void login(String id, String password) {
        setUserId(id);
        setPassword(password);
        submit();
    }

    public void setUserId(String id) {
        Util.enterField(driver, "userid", id);
    }

    public String getUserId() {
        return Util.getValue(driver, "userid");
    }

    public void setPassword(String password) {
        Util.enterField(driver, "password", password);
    }

    public String getPassword() {
        return Util.getValue(driver, "password");
    }

    public void submit() {
        driver.findElement(By.id("login")).click();
    }
}
