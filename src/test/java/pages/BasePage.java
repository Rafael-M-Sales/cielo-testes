package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import utils.ElementHelper;

import java.lang.reflect.Field;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Varre todos os campos 'By' declarados na classe (e subclasses) 
     * e valida sua visibilidade com destaque visual.
     */
    public void validateAllElementsOnPage() {
        System.out.println("Iniciando varredura de elementos na página: " + this.getClass().getSimpleName());
        
        // Obtém todos os campos da classe atual (incluindo privados)
        Field[] fields = this.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            if (field.getType().equals(By.class)) {
                try {
                    field.setAccessible(true);
                    By locator = (By) field.get(this);
                    
                    if (isElementVisible(locator)) {
                        WebElement element = driver.findElement(locator);
                        ElementHelper.highlightElement(element);
                        ElementHelper.takeScreenshot("Validacao_" + field.getName(), "ValidacaoTela");
                        System.out.println("Elemento validado: " + field.getName());
                    } else {
                        System.err.println("Elemento NÃO localizado na varredura: " + field.getName());
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao validar campo " + field.getName() + ": " + e.getMessage());
                }
            }
        }
    }

    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ElementHelper.clickWithHighlight(element, locator.toString());
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ElementHelper.sendKeysWithHighlight(element, text, locator.toString());
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void highlight(WebElement element) {
        ElementHelper.highlightElement(element);
    }

    public void waitForPageLoad() {
        wait.until(d -> ((org.openqa.selenium.JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        try { Thread.sleep(500); } catch (InterruptedException e) {} // Pausa estética para estabilidade visual
    }
}
