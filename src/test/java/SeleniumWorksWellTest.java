import io.github.bonigarcia.SeleniumExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SeleniumExtension.class)
public class SeleniumWorksWellTest {
    @Test
    public void browserVindtWikipedia(ChromeDriver driver) {
        driver.get("https://nl.wikipedia.org/wiki/Hoofdpagina");
        assertEquals("Wikipedia, de vrije encyclopedie", driver.getTitle());
    }

    @Test
    public void wikipediaVindtSelenium(ChromeDriver driver) {
        driver.get("https://nl.wikipedia.org/wiki/Hoofdpagina");

        WebElement field = driver.findElement(By.id("searchInput"));
        field.clear();
        field.sendKeys("selenium");
        WebElement link = driver.findElement(By.id("searchButton"));
        link.click();

        assertEquals("Selenium - Wikipedia", driver.getTitle());

        assertEquals("Selenium", driver.findElement(By.tagName("h1")).getText());

    }

}
