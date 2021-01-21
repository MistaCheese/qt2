package fireFoxTest;

import devTools.auth;
import fireFoxTest.createUsers.create;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.Statement;

import static devTools.wayToDriver.*;
import static fireFoxTest.precedent_TEST.*;


public class precedentDouble_TEST {
    public static void check(WebDriver driver, boolean deleteRow) throws InterruptedException {

        Actions actions = new Actions(driver);
        Action sendBackspace = actions.sendKeys(Keys.BACK_SPACE).build();
        Action right = actions.sendKeys(Keys.ARROW_RIGHT).build();

        create.user(driver, "coach2", "coach2", "coach", "ravenclaw", "coaches");
        precedent_TEST.activateUser(driver, "coach2");

        createCoachAndCreateObjectTraining(driver, "test1234", "test1234", "coach", "ravenclaw");

        auth.auth(driver, "test1234", "test1234");

        String plan = "1. test\n2. test3\n3. test";

        WebElement exm1 = driver.findElement(By.xpath("//a[contains(text(),'Тренировки')]"));
        exm1.click();

        WebElement exm = driver.findElement(By.xpath("//input[@id='monday']"));
        exm.click();
        for (int i = 0; i < 100; i++) {
            right.perform();
        }
        for(int i = 0; i < 100; i++) {
            sendBackspace.perform();
        }


        exm = driver.findElement(By.xpath("//textarea[@id='mondayPlan']"));
        exm.click();
        for (int i = 0; i < 100; i++) {
            right.perform();
            sendBackspace.perform();
        }

        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();
        Thread.sleep(3000);
        driver.switchTo().alert().accept();


        exm = driver.findElement(By.xpath("//button[@type='button']"));
        exm.click();
        Thread.sleep(3000);



        auth.auth(driver, "coach2", "coach2");
        exm = driver.findElement(By.xpath("//a[contains(text(),'Тренировки')]"));
        exm.click();

        exm = driver.findElement(By.xpath("//input[@id='monday']"));
        exm.click();
        exm.sendKeys("11:35");

        exm = driver.findElement(By.xpath("//textarea[@id='mondayPlan']"));
        exm.click();
        exm.sendKeys(plan);

        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();
        Thread.sleep(3000);

        driver.switchTo().alert().accept();

        Connection connection;
        ResultSet rs;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT * FROM training WHERE faculty ='0'");

            while (rs.next()) {
                if (rs.getString(5).equals("11:35")) {
                    System.out.println("Тест успешно пройден, запись с тренировкой записана и доступна");
                    System.out.println("Удаляем запись с тренировкой из БД");
                } else {
                    Assert.fail("Запись с тренировкой не найдена!");
                }
            }
            if (deleteRow) {
                st.executeUpdate("DELETE FROM training WHERE faculty = '0'");
            }

            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteDataFromDB.users("test1234", "coaches");
        deleteDataFromDB.users("coach2", "coaches");

    }
}
