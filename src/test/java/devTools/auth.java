package devTools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static devTools.wayToDriver.hostUrl;

public class auth {
    public static void auth (WebDriver driver, String login, String pass) throws InterruptedException {

        driver.get(hostUrl);

        WebElement exm = driver.findElement(By.xpath("//input[@id='inputLogin']")); // �����
        exm.click();
        exm.sendKeys(login);

        exm = driver.findElement(By.xpath("//input[@id='inputPassword']")); // ������
        exm.click();
        exm.sendKeys(pass);

        exm = driver.findElement(By.xpath("//input[@value='�����']")); // ���� �� "�����"
        exm.click();
        Thread.sleep(1000);

    }
}
