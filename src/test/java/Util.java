import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Util {
    public final static String baseUrl = "http://localhost:8080/shop-web/";

    public static void enterField(WebDriver driver, String id, String content) {
        WebElement element = driver.findElement(By.id(id));
        enterField(element, content);
    }

    public static void enterField(WebElement field, String content) {
        field.clear();
        field.sendKeys(content);
    }
}
