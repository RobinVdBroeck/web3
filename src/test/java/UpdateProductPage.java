import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UpdateProductPage {
    private final WebDriver driver;
    private static final String url = Util.baseUrl + "Controller?action=updateProduct&productId=";

    public UpdateProductPage(WebDriver driver, int id) {
        this.driver = driver;
        driver.get(url + id);
    }

    public void setPrice(Double price) {
        Util.enterField(driver, "price", price.toString());
    }

    public void submit() {
        driver.findElement(By.id("update")).click();
    }
}
