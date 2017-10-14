import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class SeleniumWorksWellTest extends SeleniumTest {
    @Before
    public void setUp() {
        driver.get("https://nl.wikipedia.org/wiki/Hoofdpagina");
    }


    @After
    public void clean() {
        driver.quit();
    }

    @Test
    public void browserVindtWikipedia() {
        assertEquals("Wikipedia, de vrije encyclopedie", driver.getTitle());
    }

    @Test
    public void wikipediaVindtSelenium() {
        WebElement field = driver.findElement(By.id("searchInput"));
        field.clear();
        field.sendKeys("selenium");
        WebElement link = driver.findElement(By.id("searchButton"));
        link.click();

        assertEquals("Selenium - Wikipedia", driver.getTitle());

        assertEquals("Selenium", driver.findElement(By.tagName("h1")).getText());

    }

}
