package fireFoxTest;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.sql.*;

import static devTools.wayToDriver.*;


public class checkDB_TEST {

    public static void check(WebDriver driver) throws InterruptedException, SQLException {
        Actions actions = new Actions(driver);
        Action sendTAB = actions.sendKeys(Keys.TAB).build();
        Action sendDate = actions.sendKeys("11112021").build();
        Action sendTime = actions.sendKeys("1111").build();


        WebElement exm = driver.findElement(By.xpath("//a[contains(text(),'Добавить Матч')]")); // Клик по "добавить Матч"
        exm.click();

        exm = driver.findElement(By.xpath("//h3[@class='text-center font-weight-light my-4']")); // Ввод даты
        exm.click();
        sendTAB.perform();
        Thread.sleep(1000);
        sendDate.perform();
        sendTAB.perform(); // Ввод времени
        Thread.sleep(1000);
        sendTime.perform();

        exm = driver.findElement(By.xpath("//input[@id='location']"));
        exm.click();
        exm.sendKeys("autotest");

        exm = driver.findElement(By.xpath("//input[@id='team1']"));
        exm.click();
        exm.sendKeys("Слизерин");

        exm = driver.findElement(By.xpath("//input[@id='team2']"));
        exm.click();
        exm.sendKeys("Гриффиндор");

        exm = driver.findElement(By.xpath("//input[@value='Добавить матч']")); // Клик по "Добавить матч"
        exm.click();
        Thread.sleep(7000);

        driver.switchTo().alert().accept();

        Connection connection = null;
        ResultSet rs = null;
        String team1score = "";
        String team2score = "";

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    dbUrl, dbLogin, dbPass);
            Statement st = connection.createStatement();
            rs = st.executeQuery("SELECT * FROM games WHERE location = 'autotest'");
            while (rs.next()) {


                if (rs.getString(3).equals("autotest")) {
                    System.out.println("Тест по добавлению записи пройден успешно, запись в БД обнаружена");
                } else {
                    Assert.fail("Запись объекта-матча не сохраняется в бд.");

                }
                team1score = rs.getString(5);
                team2score = rs.getString(7);

            }

            int rs1 = st.executeUpdate("UPDATE games SET team1score = '3', team2score='4'  WHERE location ='autotest'");

            rs = st.executeQuery("SELECT * FROM games WHERE location = 'autotest'");
            while (rs.next()) {


                if (rs.getString(5).equals("3") && rs.getString(7).equals("4")) {
                    System.out.println("Тест по изменению записи счета пройден успешно, запись в БД изменена успешно");
                } else {
                    Assert.fail("Попытка заменить счет через запрос к базе не удалась");

                }
                team1score = rs.getString(5);
                team2score = rs.getString(7);

            }


            System.out.println("Удаляем тестовую запись из БД");
            rs1 = st.executeUpdate("DELETE FROM games WHERE location = 'autotest'");

            rs.close();
            st.close();


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();

        }


    }

}
