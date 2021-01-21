package fireFoxTest;

import devTools.auth;
import fireFoxTest.createUsers.create;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.sql.*;
import java.text.SimpleDateFormat;

import static devTools.wayToDriver.*;

public class game_TEST {
    public static void check(WebDriver driver) throws InterruptedException {

        Actions actions = new Actions(driver);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH.mm");


        Timestamp timestamp = new Timestamp(System.currentTimeMillis() + 100000000);
        String date = sdf.format(timestamp);
        String time = sdf1.format(timestamp);

        Action setDate = actions.sendKeys(date).build();
        Action setTime = actions.sendKeys(time).build();

        create.checkAndDellUser("operator1", "operators");
        create.user(driver, "operator1", "operator1", "operator", "ravenclaw", "operators");
        precedent_TEST.activateUser(driver, "operator1");
        auth.auth(driver, "operator1", "operator1");


        WebElement exm = driver.findElement(By.xpath("//a[contains(text(),'Добавить Матч')]"));
        exm.click();
        exm = driver.findElement(By.xpath("//input[@id='inputDate']"));
        exm.click();
        setDate.perform();

        exm = driver.findElement(By.xpath("//input[@id='inputTime']"));
        exm.click();
        setTime.perform();

        exm = driver.findElement(By.xpath("//input[@id='location']"));
        exm.click();
        exm.sendKeys("test123123");

        exm = driver.findElement(By.xpath("//input[@id='team1']"));
        exm.click();
        exm.sendKeys("teamOne");

        exm = driver.findElement(By.xpath("//input[@id='team2']"));
        exm.click();
        exm.sendKeys("teamTwo");

        exm = driver.findElement(By.xpath("//input[@type='submit']"));
        exm.click();
        Thread.sleep(3000);

        driver.switchTo().alert().accept();

        exm = driver.findElement(By.xpath("//a[contains(text(),'Календарь матчей')]"));
        exm.click();

        try {
            exm = driver.findElement(By.xpath("//td[contains(text(),'test123123')]"));
            System.out.println("Созданная ранее игра " + exm.getText() + ", найдена");


        } catch (Exception e) {
            Assert.fail("Созданная ранее игра, не найдена в расписании предстоящих матчей!");
        }

        Connection connection;

        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            timestamp = new Timestamp(System.currentTimeMillis() - 1000000000);
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
            System.out.println(sdf.format(timestamp));
            rs = st.executeQuery("SELECT date FROM games WHERE location = 'test123123'");
            while (rs.next()) {
                String temp = rs.getString(1).replaceAll("-", ".").substring(0, 10);
                String[] temp1 = temp.split("\\.");
                String temp2 = temp1[2] + "." + temp1[1] + "." + temp1[0];
                if (temp2.equals(date)) {
                    System.out.println("В базе обнаружена запись с верным временем, которое указали на сайте");
                } else {
                    st.executeUpdate("DELETE FROM games WHERE location = 'test123123'");
                    Assert.fail("Время у записи в базе не совпадает с временем, которое указали на сайте");
                }
            }
            System.out.println("Меняем время в базе у записи с игрой на прошедшую");
            st.executeUpdate("UPDATE games SET date = '" + sdf.format(timestamp) + "' WHERE location ='test123123'");


            st.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }
        exm = driver.findElement(By.xpath("//a[contains(text(),'Прошедшие матчи')]"));
        exm.click();
        Thread.sleep(3000);

        try {
            exm = driver.findElement(By.xpath("//td[contains(text(),'test123123')]"));
            System.out.println("Запись успешно перенесена в раздел \"Прошедие матчи\"");
        } catch (Exception e) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(
                        dbUrl, dbLogin, dbPass);
                Statement st = connection.createStatement();
                st.executeUpdate("DELETE FROM games WHERE location = 'test123123'");
                st.close();
            } catch (SQLException | ClassNotFoundException e1) {
                e1.printStackTrace();

            }
            Assert.fail("Запись не найдена в \"Прошедших матчах\"");
        }

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM games WHERE location = 'test123123'");
            st.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }


        deleteDataFromDB.users("operator1", "operators");
    }
}
