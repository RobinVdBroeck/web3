import domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductOverviewPage {
    private final WebDriver driver;
    private static final String url = Util.baseUrl + "Controller?action=products";

    public ProductOverviewPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public List<Product> getTable() {
        List<WebElement> table = driver.findElements(By.cssSelector("table tbody tr"));
        return table.stream()
            .skip(1)
            .map(row -> {
                List<WebElement> columns = row.findElements(By.cssSelector("td"));
                List<String> columnValues = columns.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
                Product product = new Product();
                product.setName(columnValues.get(0));
                product.setDescription(columnValues.get(1));
                product.setPrice(Double.parseDouble(columnValues.get(2)));

                // Find the id
                String url = columns.get(0).findElement(By.cssSelector("a")).getAttribute("href");
                Pattern idPattern = Pattern.compile(".id=(\\d+)$");
                Matcher idMatcher = idPattern.matcher(url);
                if(idMatcher.find()) {
                    String id = idMatcher.group(1);
                    product.setId(Integer.parseInt(id, 10));
                }
                else {
                    throw new RuntimeException("Could not find id in url " + url);
                }

                return product;
            })
            .collect(Collectors.toList());
    }
}
