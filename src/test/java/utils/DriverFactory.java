package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = ConfigReader.getProperty("browser", "chrome");
            boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless", "false"));

            String remoteUrl = ConfigReader.getProperty("SELENIUM_REMOTE_URL");
            
            if (remoteUrl != null && !remoteUrl.isEmpty()) {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                
                try {
                    driver.set(new RemoteWebDriver(new URL(remoteUrl), options));
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Invalid Remote WebDriver URL: " + remoteUrl, e);
                }
            } else {
                switch (browser.toLowerCase()) {
                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        driver.set(new FirefoxDriver());
                        break;
                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        driver.set(new EdgeDriver());
                        break;
                    case "chrome":
                    default:
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions options = new ChromeOptions();
                        if (headless) {
                            options.addArguments("--headless");
                        }
                        options.addArguments("--start-maximized");
                        options.addArguments("--remote-allow-origins=*");
                        driver.set(new ChromeDriver(options));
                        break;
                }
            }
            
            driver.get().manage().window().maximize();
            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("timeout", "10"))));
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
