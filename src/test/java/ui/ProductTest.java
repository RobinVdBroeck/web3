package ui;

import domain.Product;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.AddProductPage;
import ui.pages.ProductOverviewPage;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductTest {
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCorrect() {
        final AddProductPage addProductPage = new AddProductPage(driver);
        addProductPage.open();

        final Product product = new Product();
        product.setName("Book");
        product.setDescription("This is a book");
        product.setPrice(4.99);

        addProductPage.setProduct(product);
        addProductPage.submit();

        // We are allready at the Product Overview Page because of the redirect
        String title = driver.getTitle();
        assertEquals("Product Overview", title);
        final ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        List<Product> productList = productOverviewPage.getTable();
        Product found = productList.stream()
            .filter(p -> p.equals(product))
            .findAny()
            .get();
        assertNotNull(found);

        productOverviewPage.deleteProduct(found.getId());
    }

    @Test
    public void testNameEmpty() {
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
        assertEquals(price, addProductPage.getPrice(), 0);
    }

    @Test
    public void testDescriptionEmpty() {
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
        assertEquals(price, addProductPage.getPrice(), 0);
    }

    @Test
    public void testPriceEmpty() {
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
