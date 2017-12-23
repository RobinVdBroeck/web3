import domain.Product;
import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class ProductTest {
    @Test
    void testCorrect(ChromeDriver driver) {
        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final Product product = new Product();
        product.setName("Book");
        product.setDescription("This is a book");
        product.setPrice(4.99);

        addProductPage.setProduct(product);
        addProductPage.submit();

        String title = driver.getTitle();
        assertEquals("Product Overview", title);

        final ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        List<Product> productList = productOverviewPage.getTable();

        boolean found = productList.contains(product);

        Assertions.assertTrue(found);
    }

    @Test
    void testNameEmpty(ChromeDriver driver) {
        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final String description = "This is a book";
        final double price = 4.99;

        addProductPage.setDescription(description);
        addProductPage.setPrice(price);
        addProductPage.submit();

        List<String> errors = addProductPage.getErrors();
        assertTrue(errors.contains("No name given"));

        assertEquals(description, addProductPage.getDescription());
        assertEquals(price, addProductPage.getPrice());
    }

    @Test
    void testDescriptionEmpty(ChromeDriver driver) {
        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final String name = "Book";
        final double price = 4.99;

        addProductPage.setName(name);
        addProductPage.setPrice(price);
        addProductPage.submit();

        List<String> errors = addProductPage.getErrors();
        assertTrue(errors.contains("No description given"));

        assertEquals(name, addProductPage.getName());
        assertEquals(price, addProductPage.getPrice());
    }

    @Test
    void testPriceEmpty(ChromeDriver driver) {
        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final String name = "Book";
        final String description = "This is a book";

        addProductPage.setName("Book");
        addProductPage.setDescription("This is a book");
        addProductPage.submit();

        List<String> errors = addProductPage.getErrors();
        assertTrue(errors.contains("Price cannot be empty"));

        assertEquals(name, addProductPage.getName());
        assertEquals(description, addProductPage.getDescription());
    }
}
