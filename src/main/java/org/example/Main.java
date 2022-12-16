package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://host.globalhitss.com/");
        logar(driver);
        marcarPresenca(driver);
    }

    public static void logar(WebDriver driver) {
        WebElement login = driver.findElement(By.id("UserName"));
        WebElement senha = driver.findElement(By.id("Password"));
        WebElement submitButton = driver.findElement(By.id("boton"));
        login.sendKeys("*");
        senha.sendKeys("*");
        submitButton.click();
    }

    public static void marcarPresenca(WebDriver driver) {
        List<WebElement> diasUteis = driver.findElements(By.xpath("//a[contains(@href,'sideMenu1756')]"));

        for(WebElement diaUtil: diasUteis) {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(9000));
            diaUtil.click();
        }
    }
}