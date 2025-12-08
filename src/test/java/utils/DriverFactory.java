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
            // Configura o ChromeDriver automaticamente usando WebDriverManager
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // Permite origens remotas para evitar problemas de conexão
            options.addArguments("--remote-allow-origins=*");
            // Inicia o navegador maximizado
            options.addArguments("--start-maximized");

            driver = new ChromeDriver(options);
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
