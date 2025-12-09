package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementHelper {

    /**
     * Destaca um elemento com uma borda azul e texto azul.
     */
    public static void highlightElement(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Garante que o elemento esteja visível antes de destacar
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);

    // Usa cssText para aplicar estilos com !important, garantindo que o destaque
    // prevaleça. Altera borda e cor do texto.
    js.executeScript(
            "arguments[0].style.cssText += '; border: 3px solid red !important; outline: 3px solid red !important; box-shadow: 0 0 10px red !important;'",
            elemento);
}

    /**
     * Destaca um elemento com uma borda vermelha e texto vermelho.
     */
    /**
     * Destaca um elemento com uma borda vermelha e texto vermelho.
     */
    public static void highlightElementRed(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Garante que o elemento esteja visível antes de destacar
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);

        // Usa cssText para aplicar estilos com !important, garantindo que o destaque
        // prevaleça. Altera borda e cor do texto para vermelho.
        js.executeScript(
                "arguments[0].style.cssText += '; border: 3px solid red !important; outline: 3px solid red !important; box-shadow: 0 0 10px red !important;'",
                elemento);
    }

    /**
     * Remove o destaque de um elemento.
     */
    public static void unhighlightElement(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border=''; arguments[0].style.outline=''; arguments[0].style.color=''; arguments[0].style.boxShadow='';",
                elemento);
    }

    /**
     * Tira um screenshot e salva em
     * target/screenshots/{Data}/{NomeCenario}/{Tipo}/.
     */
    private static String nomeCenario = "Padrao";

    // Define o nome do cenário para organização dos screenshots
    public static void setScenarioName(String nome) {
        nomeCenario = nome.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    // Método principal para tirar screenshot
    public static void takeScreenshot(String nome, String tipo) {
        WebDriver driver = DriverFactory.getDriver();
        File arquivoOrigem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String pastaData = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String timestamp = new SimpleDateFormat("HHmmssSSS").format(new Date());

        // Cria a estrutura de diretórios: screenshots/Data/NomeCenario/Tipo/
        String caminhoDiretorio = "screenshots/" + pastaData + "/" + nomeCenario + "/" + tipo + "/";
        new File(caminhoDiretorio).mkdirs();

        String nomeArquivo = caminhoDiretorio + nome + "_" + timestamp + ".png";

        try {
            FileUtils.copyFile(arquivoOrigem, new File(nomeArquivo));
            System.out.println("Screenshot salvo: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Sobrecarga para compatibilidade ou uso interno (tipo padrão "Geral")
    public static void takeScreenshot(String nome) {
        takeScreenshot(nome, "Geral");
    }

    /**
     * Destaca o elemento, tira um screenshot e depois clica.
     */
    public static void clickWithHighlight(WebElement elemento, String nomeElemento) {
        WebDriver driver = DriverFactory.getDriver();

        // Simula o movimento do mouse (Hover) para ativar efeitos visuais (ex: cor
        // azul)
        try {
            new org.openqa.selenium.interactions.Actions(driver).moveToElement(elemento).perform();
            Thread.sleep(200); // Pausa reduzida para otimizar tempo
        } catch (Exception e) {
            // Ignora erro de hover se não for possível, mas segue para o clique
        }

        highlightElement(elemento);
        try {
            Thread.sleep(500); // Garante que o destaque renderize antes do screenshot
        } catch (InterruptedException e) {
        }
        takeScreenshot("Clique_" + nomeElemento, "Acao");

        // Mantemos o destaque para o screenshot, depois clicamos.
        elemento.click();
        unhighlightElement(elemento); // Limpa o destaque após o clique para não poluir o próximo passo
    }

    /**
     * Destaca o elemento, tira screenshot e clica (para sub-elementos).
     */
    public static void clickWithSubElement(WebElement elemento, String nomeElemento) {
        WebDriver driver = DriverFactory.getDriver();

        // Simula o movimento do mouse (Hover)
        try {
            new org.openqa.selenium.interactions.Actions(driver).moveToElement(elemento).perform();
            Thread.sleep(2000); // Aumentado para 2s para garantir que o menu abra totalmente
        } catch (Exception e) {
        }

        highlightElementRed(elemento);
        try {
            Thread.sleep(500); // Garante que o destaque vermelho apareça no screenshot
        } catch (InterruptedException e) {
        }
        takeScreenshot("Clique_Sub_" + nomeElemento, "Acao");

        try {
            Thread.sleep(500); // Pausa ajustada para garantir captura do destaque
        } catch (InterruptedException e) {
        }

        elemento.click();
    }

    /**
     * Destaca o elemento, tira um screenshot e depois envia texto (sendKeys).
     */
    public static void sendKeysWithHighlight(WebElement elemento, String texto, String nomeElemento) {
        highlightElement(elemento);
        takeScreenshot("Digitar_" + nomeElemento, "Acao");
        try {
            Thread.sleep(200); // Pausa reduzida
        } catch (InterruptedException e) {
        }

        elemento.sendKeys(texto);
    }
}
