import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.stream.Collectors;

@ExtendWith(SeleniumExtension.class)
public class ProductTest {
    private static final String addUrl = "http://localhost:8080/shop-web/Controller?action=addProduct";

    private void filloutField(WebDriver driver, String name, String value) {
        WebElement field = driver.findElement(By.id(name));
        field.clear();
        field.sendKeys(value);
    }

    private void submitForm(WebDriver driver, String name, String description, String price) {
        filloutField(driver, "name", name);
        filloutField(driver, "description", description);
        filloutField(driver, "price", price);

        WebElement submit = driver.findElement(By.cssSelector("input[type=submit]"));
        submit.click();
    }

    @Test
    void testCorrect(ChromeDriver driver) {
        driver.get(addUrl);

        String name = "Book";
        String description = "This is a book";
        String price = "4.99";

        submitForm(driver, name, description, price);

        String title = driver.getTitle();
        Assertions.assertEquals("Product Overview", title);

        boolean found = driver.findElements(By.tagName("tr"))
            .stream()
            .map(row -> row.findElements(By.tagName("td"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList())
            )
            .skip(1)
            .anyMatch(row ->
                row.get(0).equals(name)
                    && row.get(1).equals(description)
                    && row.get(2).equals(price)
            );

        Assertions.assertTrue(found);
    }

    @Test
    void testNameEmpty(ChromeDriver driver) {
        driver.get(addUrl);

        String name = "";
        String description = "This is a book";
        String price = "4.99";

        submitForm(driver, name, description, price);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        Assertions.assertEquals("No name given", errorMsg.getText());

        WebElement nameField = driver.findElement(By.name("name"));
        Assertions.assertEquals(name, nameField.getAttribute("value"));

        WebElement descriptionField = driver.findElement(By.name("description"));
        Assertions.assertEquals(description, descriptionField.getAttribute("value"));

        WebElement priceField = driver.findElement(By.name("price"));
        Assertions.assertEquals(price, priceField.getAttribute("value"));
    }

    @Test
    void testDescriptionEmpty(ChromeDriver driver) {
        driver.get(addUrl);

        String name = "Book";
        String description = "";
        String price = "4.99";

        submitForm(driver, name, description, price);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        Assertions.assertEquals("No description given", errorMsg.getText());

        WebElement nameField = driver.findElement(By.name("name"));
        Assertions.assertEquals(name, nameField.getAttribute("value"));

        WebElement descriptionField = driver.findElement(By.name("description"));
        Assertions.assertEquals(description, descriptionField.getAttribute("value"));

        WebElement priceField = driver.findElement(By.name("price"));
        Assertions.assertEquals(price, priceField.getAttribute("value"));
    }

    @Test
    void testPriceEmpty(ChromeDriver driver) {
        driver.get(addUrl);

        String name = "Book";
        String description = "This is a book";
        String price = "";

        submitForm(driver, name, description, price);

        WebElement errorMsg = driver.findElement(By.cssSelector("div.alert-danger ul li"));
        Assertions.assertEquals("Price cannot be empty", errorMsg.getText());

        WebElement nameField = driver.findElement(By.name("name"));
        Assertions.assertEquals(name, nameField.getAttribute("value"));

        WebElement descriptionField = driver.findElement(By.name("description"));
        Assertions.assertEquals(description, descriptionField.getAttribute("value"));

        WebElement priceField = driver.findElement(By.name("price"));
        Assertions.assertEquals(price, priceField.getAttribute("value"));
    }
}
