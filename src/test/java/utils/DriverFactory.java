package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    // Método para obter a instância do WebDriver (Singleton)
    public static WebDriver getDriver() {
        if (driver == null) {
            String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
            
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--start-maximized");
            // Opções recomendadas para rodar em container
            options.addArguments("--no-sandbox"); 
            options.addArguments("--disable-dev-shm-usage");

            if (remoteUrl != null && !remoteUrl.isEmpty()) {
                try {
                    System.out.println("DriverFactory: Conectando ao Selenium Grid em " + remoteUrl);
                    driver = new org.openqa.selenium.remote.RemoteWebDriver(new java.net.URL(remoteUrl), options);
                } catch (java.net.MalformedURLException e) {
                    throw new RuntimeException("URL do Selenium Remoto inválida: " + remoteUrl, e);
                }
            } else {
                // Configura o ChromeDriver automaticamente usando WebDriverManager (Execução Local)
                System.out.println("DriverFactory: Iniciando ChromeDriver local");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
            }

            // Define uma espera implícita padrão de 10 segundos
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            System.out.println("DriverFactory: Novo driver criado " + driver);
        }
        return driver;
    }

    // Método para encerrar a sessão do WebDriver
    public static void quitDriver() {
        if (driver != null) {
            System.out.println("DriverFactory: Encerrando driver " + driver);
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("DriverFactory: Erro ao encerrar driver: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }
}
