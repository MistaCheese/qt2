package fireFoxTest.createUsers;

import fireFoxTest.deleteDataFromDB;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import static devTools.wayToDriver.*;

public class create {

    public static void user(WebDriver driver, String login, String pass, String role, String faculty, String table) throws InterruptedException {
        Dimension dimension = new Dimension(1920, 1080); // Разрешение браузера
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        checkAndDellUser(login, table);

        driver.get(hostUrl);

        WebElement exm = driver.findElement(By.xpath("//a[contains(text(),'Не зарегистрированы? Зарегистрируйтесь')]"));
        exm.click();
        Thread.sleep(1000);

        exm = driver.findElement(By.xpath("//input[@id='inputLogin']"));
        exm.click();
        exm.sendKeys(login);


        exm = driver.findElement(By.xpath("//input[@id='inputPassword']"));
        exm.click();
        exm.sendKeys(pass);


        exm = driver.findElement(By.xpath("//input[@id='inputFirstName']"));
        exm.click();
        exm.sendKeys("firstName " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputLastName']"));
        exm.click();
        exm.sendKeys("lastName " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputPatronymic']"));
        exm.click();
        exm.sendKeys("patronymic " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputBirthDate']"));
        exm.click();
        Actions actions = new Actions(driver);
        Action sendTAB = actions.sendKeys("29121993").build();
        sendTAB.perform();


        exm = driver.findElement(By.xpath("//input[@id='inputEmailAddress']"));
        exm.click();
        exm.sendKeys(login + "@" + login);


        exm = driver.findElement(By.xpath("//select[@id='selectRole']"));
        exm.click();


        exm = driver.findElement(By.xpath("//option[@value='" + role + "']"));
        exm.click();


        exm = driver.findElement(By.xpath("//select[@id='selectFaculty']"));
        exm.click();



        exm = driver.findElement(By.xpath("//option[@value='" + faculty + "']"));
        exm.click();


        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();
        Thread.sleep(2000);

    }

    public static void checkAndDellUser(String login, String table) {

        Connection connection = null;
        ResultSet rs = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT * FROM userentity WHERE login ='" + login + "'");
            while (rs.next()) {

                if (rs.getString(8).equals(login)) {
                    System.out.println("В базе онаружен тестовый пользователь, начинаем удаление");
                    deleteDataFromDB.users(login, table);
                }
            }

            st.close();
            rs.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }

    }
    public static void user(WebDriver driver, String login, String pass, String role, String faculty, String table, boolean delete) throws InterruptedException {
        Dimension dimension = new Dimension(1920, 1080); // Разрешение браузера
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);



        driver.get(hostUrl);

        WebElement exm = driver.findElement(By.xpath("//a[contains(text(),'Не зарегистрированы? Зарегистрируйтесь')]"));
        exm.click();
        Thread.sleep(1000);

        exm = driver.findElement(By.xpath("//input[@id='inputLogin']"));
        exm.click();
        exm.sendKeys(login);


        exm = driver.findElement(By.xpath("//input[@id='inputPassword']"));
        exm.click();
        exm.sendKeys(pass);


        exm = driver.findElement(By.xpath("//input[@id='inputFirstName']"));
        exm.click();
        exm.sendKeys("firstName " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputLastName']"));
        exm.click();
        exm.sendKeys("lastName " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputPatronymic']"));
        exm.click();
        exm.sendKeys("patronymic " + login);


        exm = driver.findElement(By.xpath("//input[@id='inputBirthDate']"));
        exm.click();
        Actions actions = new Actions(driver);
        Action sendTAB = actions.sendKeys("29121993").build();
        sendTAB.perform();


        exm = driver.findElement(By.xpath("//input[@id='inputEmailAddress']"));
        exm.click();
        exm.sendKeys(login + "@" + login);


        exm = driver.findElement(By.xpath("//select[@id='selectRole']"));
        exm.click();


        exm = driver.findElement(By.xpath("//option[@value='" + role + "']"));
        exm.click();


        exm = driver.findElement(By.xpath("//select[@id='selectFaculty']"));
        exm.click();



        exm = driver.findElement(By.xpath("//option[@value='" + faculty + "']"));
        exm.click();


        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();
        Thread.sleep(2000);

    }
}
