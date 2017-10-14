import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class SeleniumTest {
    protected final WebDriver driver;


    protected SeleniumTest() {
        System.setProperty("webdriver.gecko.driver", "C:\\Webdriver\\geckodriver.exe");
        driver = new FirefoxDriver();
    }
}
