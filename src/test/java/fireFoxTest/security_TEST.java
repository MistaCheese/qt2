package fireFoxTest;

import devTools.auth;
import fireFoxTest.createUsers.create;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;


import static devTools.wayToDriver.hostUrl;

public class security_TEST {


    static String tokenUser = ""; // Токен юзера

    public static void check(WebDriver driver) throws InterruptedException, IOException {
        create.checkAndDellUser("coachSecurity", "coaches");
        create.user(driver, "coachSecurity", "coachSecurity", "coach", "ravenclaw", "coaches");
        create.checkAndDellUser("doctorSecurity", "doctors");
        create.user(driver, "doctorSecurity", "doctorSecurity", "doctor", "ravenclaw", "doctors");
        precedent_TEST.activateUser(driver, "coachSecurity");
        precedent_TEST.activateUser(driver, "doctorSecurity");
        create.user(driver, "player321", "player321", "player", "ravenclaw", "players");
        precedent_TEST.activateUser(driver, "player321");
        auth.auth(driver, "coachSecurity", "coachSecurity");


        driver.get(hostUrl + "#!/createMedExam/" + deleteDataFromDB.getIdUser("player321", "userentity"));
        Thread.sleep(2000);

        try {
            WebElement exm = driver.findElement(By.xpath("//h3[@class='text-center font-weight-light my-4']"));
            if (exm.getText().equals("Войти")) {
                System.out.println("Страница с пациентами не доступна тренеру");
            }

        } catch (Exception e) {
            Assert.fail("Страница с пациентами доступна тренеру!");
            e.printStackTrace();
        }
        driver.get(hostUrl);
        WebElement exm = driver.findElement(By.xpath("//button[@ng-click='authorisation.logOut()']"));
        exm.click();
        Thread.sleep(2000);

        auth.auth(driver, "doctorSecurity", "doctorSecurity");
        Thread.sleep(2000);
        driver.get(hostUrl + "#!/coach");
        Thread.sleep(2000);
        try {
            exm = driver.findElement(By.xpath("//h3[@class='text-center font-weight-light my-4']"));
            if (exm.getText().equals("Войти")) {
                System.out.println("Страница с расписанием тренировок не доступна доктору");
            }

        } catch (Exception e) {
            Assert.fail("Страница с расписанием тренировок доступна доктору!");
            e.printStackTrace();
        }
        deleteDataFromDB.users("coachSecurity", "coaches");
        deleteDataFromDB.users("doctorSecurity", "doctors");
        deleteDataFromDB.users("player321", "players");

    }
}
