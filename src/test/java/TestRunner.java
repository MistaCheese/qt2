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

    String setProperty = toChrome; // путь до драйвера хрома
    static WebDriver driver;

    @Before
    public void singUP() {
        driver = new ChromeDriver();
        Dimension dimension = new Dimension(1920, 1080); // Разрешение браузера
        driver.manage().window().setSize(dimension);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    /* пункт 5.2.1 старой версии документа
1.	Создать объект матча с ограничением участников(предварительно создав все связанные объекты). Последовательно сохранить объекты в бд.
2.	Сделать запрос к бд по получению сохраненного объекта матча. При отсутствии возвращаемого результата окончить тест.
3.	Попытаться путем запроса к бд обновить поле счёта команд-участников соответствующего объекта-матча значениям.
    */
    @Test
    public void checkDB() throws InterruptedException, SQLException, SQLException {

        auth.auth(driver, "qwe1", "qwe1");

        checkDB_TEST.check(driver);
        Thread.sleep(1000);
    }


    /* пункт п 5.2.2 старой версии документа
    1.	Создать объект пользователя-тренера.
    2.	Активировать пользователя-тренера.
    3.	Создать объект расписания тренировок и связанные с ним объекты записи в расписании(указав интересующее время) и тренировки.
    4.	Последовательно сохранить созданные объекты в бд.
    5.	Сделать запрос к бд с целью убедиться в наличиии обновления в расписании тренировок у всех членов команды.
     */
    @Test
    public void precedent() throws InterruptedException {
        precedent_TEST.check(driver, true);
        Thread.sleep(1000);
    }


    /* пункт 5.2.3 новой версии документа
    1-4 из функционального тестирования.
    5.	Создать объект пользователя-тренера. Сохранить его в бд.
    6.	Через первого пользователя удалить расписание в одном из дней. Синхронизировать изменения с бд.
    7.	Попытаться создать объект записи за второго пользователя на то же время, что уже было освобождено первым пользователем.
    8.	Сохранить объект записи в бд.
    9.	Сделать запрос к бд с целью получения информации об успешности обновления расписания тренировок для всех членов команды.
    */
    @Test
    public void precedentDouble() throws InterruptedException {
        precedentDouble_TEST.check(driver, true);
        Thread.sleep(1000);
    }

    /* пункт 5.2.4 старой версии
    1.	Создать пользователя-врача.
    2.	Активировать пользователя-врача.
    3.	Авторизоваться за пользователя-тренера.
    4.	Создать пользователя-игрока.
    5.	Активировать пользователя-игрока
    6.	Перейти на эндпоинт с созданием медицинского осмотра для созданного игрока.
    7.	Добавить запись о состоянии пользователя-игрока.
    8.	Нажать на сохранить изменения. При отсутствии сообщения об успешности операции завершить тест.

     */
    @Test
    public void doctor() throws InterruptedException {
        medicalCheckUp_TEST.check(driver);
        Thread.sleep(1000);
    }

    /*  пункт 5.2.7
    1. Создать пользователя Врача
    2. Создать пользователя Тренера
    3. Авторизоваться под пользователем Врача
    4. Перейти на страницу по правам доступная только тренеру
    5. Авторизоваться под Тренером
    6. Перейти на страницу по правам доступную только врачу
     */
    @Test
    public void security() throws InterruptedException, IOException {
        security_TEST.check(driver);
    }

    /* пункт 5.2.9 старой версии файла
1.	Создать объект запроса с помощью пользователя-оператора.
2.	Создать объект матча на предстоящее число и связанные с ним объекты. Сохранить объекты в бд.
3.	Проверить, что расписание обновилось и созданный объект отображается в предстоящих матчах.
4.	Обратиться к полю со временем в объекте записи в расписании. Убедиться, что его значение соответствует выставленному при создании объекта значению, изменить значение на прошедшую дату.
5.	Проверить, что расписание обновилось и созданный объект отображается в прошедших матчах.

     */

    @Test
    public void gameCreate() throws InterruptedException {
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
