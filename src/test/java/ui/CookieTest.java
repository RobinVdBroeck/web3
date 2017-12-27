package ui;

import static org.junit.Assert.*;

import domain.Product;
import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.pages.ProductOverviewPage;
import ui.pages.UpdateProductPage;
import ui.pages.UserOverviewPage;

import java.util.Optional;

public class CookieTest {
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.get(Util.baseUrl);
    }

    @After
    public void teardown() {
        driver.quit();
    }

    private String getColorFooter() {
        WebElement footer = driver.findElement(By.tagName("footer"));
        if (footer.getCssValue("background-color").equals("rgba(185, 151, 3, 1)")) {
            return "yellow";
        } else {
            return "red";
        }
    }

    private String giveOppositeColor(String color) {
        if (color.equals("red")) {
            return "yellow";
        } else {
            return "red";
        }
    }

    private void checkColorFooter(String title) {
        String color = getColorFooter();
        final WebElement changeColorLink = driver.findElement(By.id("changeColorLink"));
        changeColorLink.click();

        assertEquals(title, driver.getTitle());
        assertEquals(giveOppositeColor(color), getColorFooter());
    }

    @Test
    public void testHomepageSwitchColorOnceCorrect() {
        checkColorFooter("Home");
    }

    @Test
    public void testOverviewUsersSwitchColorTwiceCorrect() {
        driver.get(UserOverviewPage.url);
        // first change
        checkColorFooter("User Overview");
        // second change
        checkColorFooter("User Overview");
    }

    @Test
    public void testSwitchColorAfterUpdateProductCorrect() {
        final ProductOverviewPage productOverviewPage = new ProductOverviewPage(driver);
        productOverviewPage.open();

        final Optional<Product> product = productOverviewPage.getTable().stream().findAny();
        if (!product.isPresent()) {
            throw new IllegalStateException("Test is invalid state. It needs a product to succeed");
        }

        // Open a product page
        final UpdateProductPage updateProductPage = new UpdateProductPage(driver, product.get().getId());
        updateProductPage.open();

        // Change the color
        final WebElement changeColorLink = driver.findElement(By.id("changeColorLink"));
        changeColorLink.click();
        assertEquals("Product Overview", driver.getTitle());

        checkColorFooter("Product Overview");
    }
}
