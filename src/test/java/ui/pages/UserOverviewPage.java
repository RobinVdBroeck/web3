package ui.pages;

import domain.Person;
import domain.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ui.Util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserOverviewPage {
    public static final String url = Util.baseUrl + "Controller?action=usersGet";
    private final WebDriver driver;

    public UserOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Person> getTable() {
        List<WebElement> table = driver.findElements(By.cssSelector("table tbody tr"));
        return table.stream()
            .skip(1)
            .map(row -> {
                List<WebElement> columns = row.findElements(By.cssSelector("td"));
                List<String> columnValues = columns.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
                Person person = new Person();
                person.setEmail(columnValues.get(0));
                person.setFirstName(columnValues.get(1));
                person.setLastName(columnValues.get(2));

                // Find the id
                String url = columns.get(3).findElement(By.cssSelector("a")).getAttribute("href");
                Pattern idPattern = Pattern.compile(".id=(.+)$");
                Matcher idMatcher = idPattern.matcher(url);
                if (idMatcher.find()) {
                    String id = idMatcher.group(1);
                    person.setUserid(id);
                } else {
                    throw new RuntimeException("Could not find id in url " + url);
                }

                return person;
            })
            .collect(Collectors.toList());
    }

    public void open() {
        driver.get(url);
    }

    public void delete(String userid) {
        WebElement element = driver.findElement(By.id("delete-" + userid));
        element.click();
    }
}
