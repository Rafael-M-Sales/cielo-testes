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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Classe utilitária para operações comuns em elementos web e evidências de teste.
 * <p>
 * Fornece métodos estáticos para destacar elementos na tela (Highlight), 
 * tirar screenshots organizados por cenário e manipular interações com cliques seguros.
 * </p>
 */
public class ElementHelper {

    /**
     * Destaca um elemento visualmente na página com uma borda vermelha e sombra vermelha.
     * <p>
     * Utiliza JavascriptExecutor para injetar CSS diretamente no estilo do elemento.
     * Rola a página até que o elemento esteja centralizado antes de aplicar o destaque.
     * </p>
     *
     * @param elemento O WebElement a ser destacado.
     */
    public static void highlightElement(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Garante que o elemento esteja visível na viewport antes de destacar
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);

        // Usa cssText para aplicar estilos com !important, garantindo que o destaque
        // prevaleça sobre o estilo original. Altera borda, outline e sombra para vermelho.
        js.executeScript(
                "arguments[0].style.cssText += '; border: 3px solid red !important; outline: 3px solid red !important; box-shadow: 0 0 10px red !important;'",
                elemento);
    }

    /**
     * Método alternativo para destacar elemento.
     * Atualmente idêntico ao highlightElement, mantido para compatibilidade ou uso específico futuro.
     *
     * @param elemento O WebElement a ser destacado.
     */
    public static void highlightElementRed(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Garante que o elemento esteja visível antes de destacar
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", elemento);

        // Aplica o estilo vermelho
        js.executeScript(
                "arguments[0].style.cssText += '; border: 3px solid red !important; outline: 3px solid red !important; box-shadow: 0 0 10px red !important;'",
                elemento);
    }

    /**
     * Remove qualquer destaque visual aplicado anteriormente a um elemento.
     * <p>
     * Limpa as propriedades de borda, outline, cor e sombra que foram injetadas.
     * </p>
     *
     * @param elemento O WebElement a ter o destaque removido.
     */
    public static void unhighlightElement(WebElement elemento) {
        WebDriver driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // Reseta os estilos injetados para vazio
        js.executeScript("arguments[0].style.border=''; arguments[0].style.outline=''; arguments[0].style.color=''; arguments[0].style.boxShadow='';",
                elemento);
    }

    /** Variável estática para armazenar o nome do cenário atual. */
    private static String nomeCenario = "Padrao";

    /**
     * Define o nome do cenário atual para organização das pastas de screenshots.
     * <p>
     * Sanitiza o nome removendo caracteres especiais para evitar problemas no sistema de arquivos.
     * </p>
     *
     * @param nome O nome do cenário (geralmente vindo do Cucumber).
     */
    public static void setScenarioName(String nome) {
        // Substitui caracteres não alfanuméricos por underline
        nomeCenario = nome.replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    /**
     * Captura um screenshot da tela atual e salva no sistema de arquivos.
     * <p>
     * Estrutura de pastas: target/screenshots/{Data}/{NomeCenario}/{Tipo}/
     * O nome do arquivo inclui o nome fornecido e um timestamp para unicidade.
     * </p>
     *
     * @param nome O nome descritivo para o screenshot (ex: "PaginaInicial").
     * @param tipo O tipo de evidência (ex: "Acao", "Resultado", "Erro").
     */
    public static void takeScreenshot(String nome, String tipo) {
        WebDriver driver = DriverFactory.getDriver();
        // Captura o screenshot como arquivo
        File arquivoOrigem = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String pastaData = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String timestamp = new SimpleDateFormat("HHmmssSSS").format(new Date());

        // Monta o caminho do diretório baseado na data, cenário e tipo
        String caminhoDiretorio = "screenshots/" + pastaData + "/" + nomeCenario + "/" + tipo + "/";
        new File(caminhoDiretorio).mkdirs(); // Cria os diretórios se não existirem

        String nomeArquivo = caminhoDiretorio + nome + "_" + timestamp + ".png";

        try {
            // Copia o arquivo temporário para o destino final
            FileUtils.copyFile(arquivoOrigem, new File(nomeArquivo));
            System.out.println("Screenshot salvo: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sobrecarga do método takeScreenshot usando o tipo padrão "Geral".
     *
     * @param nome O nome descritivo para o screenshot.
     */
    public static void takeScreenshot(String nome) {
        takeScreenshot(nome, "Geral");
    }

    /**
     * Realiza um fluxo completo de interação: Hover -> Destaque -> Screenshot -> Clique.
     * <p>
     * Usado para garantir que a evidência mostre exatamente onde será clicado.
     * </p>
     *
     * @param elemento O WebElement a ser clicado.
     * @param nomeElemento Um nome descritivo para o log e screenshot.
     */
    public static void clickWithHighlight(WebElement elemento, String nomeElemento) {
        WebDriver driver = DriverFactory.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        
        try {
             wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf(elemento));
             wait.until(org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable(elemento));
        } catch (Exception e) {
            // Log warning but proceed
        }

        // Tenta simular o movimento do mouse (Hover) para ativar efeitos visuais (ex: menus que se abrem)
        try {
            new org.openqa.selenium.interactions.Actions(driver).moveToElement(elemento).perform();
            Thread.sleep(200); // Pausa breve para animações de hover
        } catch (Exception e) {
            // Ignora erro de hover se o elemento não suportar ou estiver inacessível, segue para o clique
        }

        // Aplica o destaque
        highlightElement(elemento);
        
        // Tira o screenshot da ação
        takeScreenshot("Clique_" + nomeElemento, "Acao");

        // Clica no elemento
        elemento.click();
        
        // Remove o destaque após o clique para não poluir o próximo passo visualmente
        try {
            unhighlightElement(elemento); 
        } catch (Exception e) {
            // Ignora se o elemento não existir mais
        }
    }

    /**
     * Variação do clique para sub-elementos (menus dropdown, etc).
     * <p>
     * Inclui pausas maiores para garantir que menus flutuantes estejam estáveis.
     * Usa highlightElementRed explicitamente.
     * </p>
     *
     * @param elemento O sub-elemento a ser clicado.
     * @param nomeElemento O nome descritivo para evidência.
     */
    public static void clickWithSubElement(WebElement elemento, String nomeElemento) {
        WebDriver driver = DriverFactory.getDriver();

        // Simula o movimento do mouse (Hover) com tempo maior para menus expandirem
        try {
            new org.openqa.selenium.interactions.Actions(driver).moveToElement(elemento).perform();
        } catch (Exception e) {
        }

        highlightElementRed(elemento);
        
        takeScreenshot("Clique_Sub_" + nomeElemento, "Acao");

        elemento.click();

        try {
            unhighlightElement(elemento);
        } catch (Exception e) {
            // Se o elemento ficou stale após o clique (navegação), apenas ignoramos
        }
    }

    /**
     * Destaca um campo de entrada, tira screenshot e digita o texto.
     *
     * @param elemento O campo de input.
     * @param texto O texto a ser digitado.
     * @param nomeElemento O nome descritivo do campo.
     */
    public static void sendKeysWithHighlight(WebElement elemento, String texto, String nomeElemento) {
        highlightElement(elemento);
        takeScreenshot("Digitar_" + nomeElemento, "Acao");

        elemento.sendKeys(texto);
        // Nota: Não removemos o destaque aqui para permitir visualizar o campo preenchido,
        // mas idealmente deveria limpar se houver validação visual subsequente.
    }
}
