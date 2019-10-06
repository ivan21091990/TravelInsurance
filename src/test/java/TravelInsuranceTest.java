import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TravelInsuranceTest {
    WebDriver driver;
    String baseURL;

    @Before
    public  void beforeTest(){
    System.setProperty("webdriver.chrome.driver", "drv/chromedriver_1.exe");
    baseURL = "https://www.sberbank.ru/ru/person";
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    driver.get(baseURL);
    }

    @Test
    public void testInsuranceTravel(){
        driver.findElement(By.xpath("//span[contains(text(),'Страхование')]")).click();
        driver.findElement(By.xpath("//ul[contains(@class, 'sub-list')]//li//a[contains(text(),'Страхование путешественников')]")).click();

        WebElement sendBtn = driver.findElement(By.xpath("//span[contains(text(),'Оформить онлайн')]"));
        Wait<WebDriver> wait = new WebDriverWait(driver, 30,1000);
        wait.until(ExpectedConditions.visibilityOf(sendBtn)).click();

        driver.findElement(By.xpath("//div/p/a/img")).click();

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        WebElement minBtn = driver.findElement(By.xpath(("//*[text()='Минимальная']/../..")));
        wait.until(ExpectedConditions.visibilityOf(minBtn)).click();

        driver.findElement(By.xpath(("//span[text()='Оформить']"))).click();

        fillField(By.xpath("//input[@name='insured0_surname']"), "Ivanov");
        fillField(By.xpath("//input[@name='insured0_name']"),"Ivan");
        fillField(By.xpath("//input[@name='insured0_birthDate']"), "21091990");

        fillField(By.xpath("//input[@name='surname']"), "Сергеев");
        fillField(By.xpath("//input[@name='name']"),"Сергей");
        fillField(By.xpath("//input[@name='middlename']"), "Сергеевич");
        fillField(By.xpath("//input[@name='birthDate']"), "01011985");
        driver.findElement(By.xpath(("//input[@name='male']"))).click();

        Assert.assertEquals("Ivanov",
                driver.findElement(By.xpath("//input[@name='insured0_surname']")).getAttribute("value"));
        Assert.assertEquals("Ivan",
                driver.findElement(By.xpath("//input[@name='insured0_name']")).getAttribute("value"));
        Assert.assertEquals("21.09.1990",
                driver.findElement(By.xpath("//input[@name='insured0_birthDate']")).getAttribute("value"));
        Assert.assertEquals("Сергеев",
                driver.findElement(By.xpath("//input[@name='surname']")).getAttribute("value"));
        Assert.assertEquals("Сергей",
                driver.findElement(By.xpath("//input[@name='name']")).getAttribute("value"));
        Assert.assertEquals("Сергеевич",
                driver.findElement(By.xpath("//input[@name='middlename']")).getAttribute("value"));
        Assert.assertEquals("01.01.1985",
                driver.findElement(By.xpath("//input[@name='birthDate']")).getAttribute("value"));

        driver.findElement(By.xpath(("//span[text()='Продолжить']"))).click();

        Assert.assertEquals("Заполнены не все обязательные поля",
                driver.findElement(By.xpath("//*[text()='Заполнены не все обязательные поля']")).getText());

    }
   public void fillField(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
   }
    @After
    public void afterTest(){
    driver.quit();
    }
}
