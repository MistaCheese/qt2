package fireFoxTest;

import devTools.auth;
import fireFoxTest.createUsers.create;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class medicalCheckUp_TEST {
    public static void check(WebDriver driver) throws InterruptedException {
        create.checkAndDellUser("med123", "doctors");
        create.user(driver, "med123", "med123", "doctor", "ravenclaw", "doctors");
        precedent_TEST.activateUser(driver, "med123");

        create.checkAndDellUser("coach123", "coaches");
        create.user(driver, "coach123", "coach123", "coach", "ravenclaw", "coaches");
        precedent_TEST.activateUser(driver, "coach123");
        create.checkAndDellUser("player123", "players");
        create.user(driver, "player123", "player123", "player", "ravenclaw", "players");
        precedent_TEST.activateUser(driver, "player123");

        auth.auth(driver, "med123", "med123");

        WebElement exm;

        int i = 1;


        while (true) {
            try {
                driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                exm = driver.findElement(By.xpath("//tbody[1]/tr[" + i + "]/td[2]"));
                if (exm.getText().equals("lastName player123")) {

                    exm = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/patients[1]/main[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[" + i + "]/td[5]/a[1]"));
                    exm.click();
                    break;
                } else {
                    i++;
                }
            } catch (NoSuchElementException ignored) {
            }
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        exm = driver.findElement(By.xpath("//textarea[@id='text']"));
        exm.click();
        exm.sendKeys("test");

        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();

        Thread.sleep(3000);


        if(driver.switchTo().alert().getText().equals("Карточка добавлена")) {
            System.out.println("Тест успешно пройден, окно с подтверждение об успешном создании карточки пациента создана");
        } else {
            Assert.fail("Окно об успешном создании карточки не обнаружено!");
        }
        driver.switchTo().alert().accept();
        deleteDataFromDB.users("med123", "doctors");
        deleteDataFromDB.users("coach123", "coaches");
        deleteDataFromDB.users("player123", "players");



    }
}
