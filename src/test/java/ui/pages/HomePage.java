package ui.pages;

import org.openqa.selenium.WebDriver;
import ui.Util;

public class HomePage {
    private final WebDriver driver;
    private final static String url = Util.baseUrl + "Controller?action=indexGet";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }
}
