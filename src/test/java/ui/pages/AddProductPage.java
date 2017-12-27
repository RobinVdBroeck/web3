package ui.pages;

import domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.Util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AddProductPage {
    private final WebDriver driver;
    private static final String url = Util.baseUrl + "Controller?action=addProductGet";

    public AddProductPage(WebDriver driver) {
        this.driver = driver;
    }


    public void open() {
        driver.get(url);
    }

    public void setProduct(Product product) {
        setName(product.getName());
        setDescription(product.getDescription());
        setPrice(product.getPrice());
    }

    public void setName(String name) {
        Util.enterField(driver, "name", name);
    }

    public void setDescription(String description) {
        Util.enterField(driver, "description", description);
    }

    public void setPrice(String price) {
        Util.enterField(driver, "price", price);
    }

    public void setPrice(Double price) {
        setPrice(price.toString());
    }

    public void submit() {
        driver.findElement(By.id("save")).click();
    }

    public List<String> getErrors() {
        final WebElement container = driver.findElement(By.className("alert-danger"));

        if (container == null) {
            return Collections.emptyList();
        }

        return container.findElements(By.cssSelector("li"))
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public String getName() {
        return driver.findElement(By.name("name")).getAttribute("value");
    }

    public String getDescription() {
        return driver.findElement(By.name("description")).getAttribute("value");
    }

    public double getPrice() {
        return Double.parseDouble(driver.findElement(By.name("price")).getAttribute("value"));
    }
}
