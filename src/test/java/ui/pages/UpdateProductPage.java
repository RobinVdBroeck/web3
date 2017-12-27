package ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ui.Util;

public class UpdateProductPage {
    private final WebDriver driver;
    private int id;
    private static final String url = Util.baseUrl + "Controller?action=updateProductGet&id=";

    public UpdateProductPage(WebDriver driver, int id) {
        this.driver = driver;
        this.id = id;
    }

    public void open() {
        driver.get(url + id);
    }

    public void setPrice(Double price) {
        Util.enterField(driver, "price", price.toString());
    }

    public void submit() {
        driver.findElement(By.id("update")).click();
    }
}
