package devTools;


import java.io.File;

public class wayToDriver {
    static File file = new File("chromedriver.exe");
    public static final String toFireFox = System.setProperty("webdriver.gecko.driver", "C:\\webDriver\\chrome\\88\\chromedriver.exe"); // путь до драйвера firefox
    public static final String dbUrl = "jdbc:postgresql://116.203.111.44:5432/quidditch";
    public static final String dbLogin = "quidditch_user";
    public static final String dbPass = "quidditch_2021";
    public static final String hostUrl = "http://localhost:8090/";

}
