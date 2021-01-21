package devTools;


import java.io.File;

public class wayToDriver {
    static File file = new File("chromedriver.exe");
    public static final String toChrome = System.setProperty("webdriver.gecko.driver", file.getAbsolutePath()); // путь до драйвера firefox
    public static final String dbUrl = "jdbc:postgresql://116.203.111.44:5432/quidditch";
    public static final String dbLogin = "quidditch_user";
    public static final String dbPass = "quidditch_2021";
    public static final String hostUrl = "http://192.168.0.129:8090/";

}
