package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 5000;
        // Можно добавить другие настройки:
        // Configuration.headless = true; // для запуска без GUI
        // Configuration.browser = "firefox"; // если нужен Firefox
    }

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }
}