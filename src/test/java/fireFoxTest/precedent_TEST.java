package fireFoxTest;

import fireFoxTest.createUsers.create;
import devTools.auth;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import static devTools.wayToDriver.*;

public class precedent_TEST {
    public static void check(WebDriver driver, boolean dellRow) throws InterruptedException {

        createCoachAndCreateObjectTraining(driver, "test123", "test123", "coach", "ravenclaw");

        Connection connection;
        ResultSet rs;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT * FROM training WHERE faculty ='0'");

            while (rs.next()) {
                if (rs.getString(2).equals("0")) {
                    System.out.println("Тест успешно пройден, запись с тренировкой записана и доступна");
                    System.out.println("Удаляем запись с тренировкой из БД");
                } else {
                    Assert.fail("Запись с тренировкой не найдена!");
                }
            }
            if (dellRow) {
                st.executeUpdate("DELETE FROM training WHERE faculty = '0'");
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

        deleteDataFromDB.users("test123", "coaches");


    }

    public static void createCoachAndCreateObjectTraining(WebDriver driver, String login, String pass, String role, String faculty) throws InterruptedException {
        create.checkAndDellUser(login, "coaches");
        create.user(driver, login, pass, role, faculty, "coaches");
//        precedent_TEST.activateUser(driver, login);

        Actions actions = new Actions(driver);
        Action sendBackspace = actions.sendKeys(Keys.BACK_SPACE).build();
        Action right = actions.sendKeys(Keys.ARROW_RIGHT).build();

        driver.get(hostUrl);

        activateUser(driver, login);
        WebElement exm;

        auth.auth(driver, login, pass);

        exm = driver.findElement(By.xpath("//a[contains(text(),'Тренировки')]"));
        exm.click();    // Клик по кнопке

        exm = driver.findElement(By.xpath("//input[@id='monday']"));
        exm.click();

        Thread.sleep(1000);
        exm.sendKeys("10:30");

        exm = driver.findElement(By.xpath("//textarea[@id='mondayPlan']"));
        exm.click();
        Thread.sleep(1000);
        String plan = "1. test\n2. test2\n3. test";
        exm.sendKeys(plan);

        exm = driver.findElement(By.xpath("//input[@id='tuesday']"));
        exm.click();
        Thread.sleep(1000);
        exm.sendKeys("10:31");

        exm = driver.findElement(By.xpath("//textarea[@id='tuesdayPlan']"));
        exm.click();
        Thread.sleep(1000);
        exm.sendKeys(plan);


        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();

        Thread.sleep(2000);

        driver.switchTo().alert().accept();

        exm = driver.findElement(By.xpath("//button[@type='button']"));
        exm.click();
        Thread.sleep(3000);
    }

    public static void activateUser(WebDriver driver, String login) throws InterruptedException {
        auth.auth(driver, "qwerty123", "qwerty123");

        WebElement exm = driver.findElement(By.xpath("//a[contains(text(),'Управление пользователями')]"));
        exm.click();
        Thread.sleep(5000);
        int i = 1;


        while (true) {
            try {
                driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
                exm = driver.findElement(By.xpath("//tbody[1]/tr[" + i + "]/td[2]"));
                if (exm.getText().equals("lastName " + login)) {
                    exm = driver.findElement(By.xpath("//body[1]/div[1]/div[1]/div[1]/admin-content[1]/main[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[" + i + "]/td[4]/a[1]"));
                    exm.click();
                    break;
                } else {
                    i++;
                }
            } catch (NoSuchElementException ignored) {
            }
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        exm = driver.findElement(By.xpath("//button[@ng-click='authorisation.logOut()']"));
        exm.click();
    }
}
