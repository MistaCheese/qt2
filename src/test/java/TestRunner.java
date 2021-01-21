import devTools.auth;
import fireFoxTest.*;
import fireFoxTest.createUsers.create;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeUnit;

import static devTools.wayToDriver.*;

public class TestRunner {

    String setProperty = toFireFox; // путь до драйвера хрома
    static WebDriver driver;

    @Before
    public void singUP() throws InterruptedException {
//        FirefoxDriver driver1 = new FirefoxDriver();
//        create.user(driver1, "qwerty123", "qwerty123", "administrator", "ravenclaw", "userentity");
//        driver1.close();

        driver = new ChromeDriver();
        Dimension dimension = new Dimension(1920, 1080); // Разрешение браузера
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void checkDB() throws InterruptedException, SQLException, SQLException {

        auth.auth(driver, "qwe1", "qwe1");

        checkDB_TEST.check(driver);
        Thread.sleep(1000);
    }


    @Test
    public void precedent() throws InterruptedException {
        precedent_TEST.check(driver, true);
        Thread.sleep(1000);
    }

    @Test
    public void precedentDouble() throws InterruptedException {
        precedentDouble_TEST.check(driver, true);
        Thread.sleep(1000);
    }

    @Test
    public void doctor() throws InterruptedException {
        medicalCheckUp_TEST.check(driver);
        Thread.sleep(1000);
    }

    @Test
    public void security() throws InterruptedException, IOException {
        security_TEST.check(driver);
    }

    @Test
    public void gameCrate() throws InterruptedException {
        game_TEST.check(driver);
        Thread.sleep(1000);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }

}
