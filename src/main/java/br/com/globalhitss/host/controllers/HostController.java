package br.com.globalhitss.host.controllers;

import br.com.globalhitss.host.dto.UserHostDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HostController {

    public void hostController() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Type your user:");
        String user = sc.nextLine();

        System.out.println("Type your password:");
        String passwordKey = sc.nextLine();

        UserHostDTO userHostDTO = new UserHostDTO();
        userHostDTO.setUser(user);
        userHostDTO.setPassword(passwordKey);

        String pathToChromeDriver = "chromedriver_win32/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        WebDriver driver = new ChromeDriver(optionsChrome());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://host.globalhitss.com/");
        login(driver, userHostDTO);
        attend(driver);
        driver.quit();
    }

    public ChromeOptions optionsChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        return options;
    }

    public void login(WebDriver driver, UserHostDTO userHostDTO) {
        try {
            WebElement loggin = driver.findElement(By.id("UserName"));
            WebElement password = driver.findElement(By.id("Password"));
            WebElement submitButton = driver.findElement(By.id("boton"));
            loggin.sendKeys(userHostDTO.getUser());
            password.sendKeys(userHostDTO.getPassword());
            submitButton.click();
        } catch (Exception e) {
            System.out.println("Something wrong with performing the automation");

            // TODO add message dialog error.
        }
    }

    public void attend(WebDriver driver) {
        WebElement calendar = driver.findElement(By.className("ui-datepicker-calendar"));
        List<WebElement> businessDays = calendar.findElements(By.cssSelector("tbody td a.ui-state-default"));

        for (WebElement businessDay : businessDays) {

            WebDriverWait wait = (new WebDriverWait(driver, Duration.ofSeconds(10)));
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("ui-state-default")));
            element.click();

            WebElement hoursCaptured = driver.findElement(By.id("HorasCapturadas"));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("HorasCapturadas")));
            Select select = new Select(hoursCaptured);
            select.selectByValue("8");

            WebElement buttonConfirmBusinessDay = wait.until(ExpectedConditions.elementToBeClickable(By.className("botonOk")));
            buttonConfirmBusinessDay.click();

            WebElement uselessDay = wait.until(ExpectedConditions.elementToBeClickable(By.className("diaInhabil")));
            uselessDay.click();

            attend(driver);
        }
    }
}
