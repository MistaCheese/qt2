package devTools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static devTools.wayToDriver.hostUrl;

public class auth {
    public static void auth (WebDriver driver, String login, String pass) throws InterruptedException {

        driver.get(hostUrl);

        WebElement exm = driver.findElement(By.xpath("//input[@id='inputLogin']")); // Логин
        exm.click();
        exm.sendKeys(login);

        exm = driver.findElement(By.xpath("//input[@id='inputPassword']")); // Пароль
        exm.click();
        exm.sendKeys(pass);

        exm = driver.findElement(By.xpath("//input[@value='Войти']")); // Клик по "Войти"
        exm.click();
        Thread.sleep(1000);

    }
}
