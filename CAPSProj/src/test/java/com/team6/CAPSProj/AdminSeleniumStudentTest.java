package com.team6.CAPSProj;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class AdminSeleniumStudentTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
	System.setProperty("webdriver.gecko.driver", "C:\\Users\\Edmund koo\\Downloads\\geckodriver-v0.29.1-win64\\geckodriver.exe");
    driver = new FirefoxDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void admintest() {
    driver.get("http://localhost:8080/home");
    driver.manage().window().setSize(new Dimension(1550, 838));
    driver.findElement(By.id("emailAdd")).sendKeys("admin@gmail.com");
    driver.findElement(By.id("password")).click();
    driver.findElement(By.id("password")).sendKeys("admin");
    driver.findElement(By.cssSelector("form")).click();
    driver.findElement(By.id("role")).click();
    {
      WebElement dropdown = driver.findElement(By.id("role"));
      dropdown.findElement(By.xpath("//option[. = 'Admin']")).click();
    }
    driver.findElement(By.cssSelector("option:nth-child(3)")).click();
    driver.findElement(By.cssSelector(".button")).click();
    driver.findElement(By.cssSelector("div > .button")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys("donald");
    driver.findElement(By.id("lastName")).click();
    driver.findElement(By.id("lastName")).sendKeys("trump");
    driver.findElement(By.id("personEmail")).click();
    driver.findElement(By.id("personEmail")).sendKeys("dt@hotmail.com");
    driver.findElement(By.id("matrDate")).click();
    driver.findElement(By.id("matrDate")).sendKeys("2021-07-31");
    driver.findElement(By.cssSelector(".button:nth-child(1)")).click();
    driver.findElement(By.linkText("2")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2)")).getText(), is("donald"));
    driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(5) .button")).click();
    driver.findElement(By.id("firstName")).click();
    driver.findElement(By.id("firstName")).sendKeys(" j");
    driver.findElement(By.cssSelector(".button:nth-child(1)")).click();
    driver.findElement(By.linkText("2")).click();
    driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(2)")).getText(), is("donald j"));
    driver.findElement(By.cssSelector("tr:nth-child(6) > td:nth-child(6) .button")).click();
    driver.findElement(By.cssSelector("div:nth-child(1) > div:nth-child(1)")).click();
    assertThat(driver.findElement(By.cssSelector("div:nth-child(1) > div:nth-child(1)")).getText(), is("Total Rows: 9"));
  }
}
