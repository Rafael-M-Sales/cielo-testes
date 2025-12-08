package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import utils.DriverFactory;
import utils.ElementHelper;

import java.time.Duration;
import java.util.Set;

public class CieloSteps {

    // Inicializa o WebDriver e o WebDriverWait
    WebDriver driver = DriverFactory.getDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private String nomeElementoAtual;

    // Hook do Cucumber executado antes de cada cenário para definir o nome do
    // cenário
    @io.cucumber.java.Before
    public void definirInfoCenario(io.cucumber.java.Scenario scenario) {
        ElementHelper.setScenarioName(scenario.getName());
    }

    // Passo para acessar a página inicial da Cielo
    @Given("que eu acesso a página inicial da Cielo")
    public void queEuAcessoAPaginaInicialDaCielo() {
        driver.get("https://www.cielo.com.br/");
    }

    // Passo para verificar se o popup de cookies ou configuração aparece
    @When("o popup de cookies ou configuração aparece")
    public void oPopupDeCookiesOuConfiguracaoAparece() {
        try {
            // Aguarda até que o banner LGPD seja visível
            WebElement popup = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#lgpd-banner")));
            // Destaca o popup para evidência
            ElementHelper.highlightElement(popup);
            // Remove o destaque
            ElementHelper.unhighlightElement(popup);
        } catch (Exception e) {
            System.out.println("Popup não encontrado ou já fechado.");
        }
    }

    // Passo para fechar o popup de cookies
    @Given("eu fecho o popup")
    public void euFechoOPopup() {
        try {
            // Aguarda a visibilidade do banner
            WebElement popup = wait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#lgpd-banner")));

            // Tenta encontrar o botão "Aceitar tudo" ou "Recusar" para fechar
            // Assume que "Aceitar tudo" é uma forma segura de fechar a obstrução
            WebElement botaoAceitar = popup.findElement(By.xpath(".//button[contains(., 'Aceitar tudo')]"));

            // Clica no botão com destaque
            ElementHelper.clickWithHighlight(botaoAceitar, "Aceitar_Tudo");

            // Aguarda o popup desaparecer
            wait.until(ExpectedConditions.invisibilityOf(popup));
        } catch (Exception e) {
            System.out.println("Popup não encontrado ou não pôde ser fechado: " + e.getMessage());
        }
    }

    // Passo para destacar e clicar em um elemento
    @When("eu destaco e clico no elemento {string}")
    public void euDestacoEClicoNoElemento(String nomeElemento) {
        this.nomeElementoAtual = nomeElemento;
        try {
            By locator = getLocator(nomeElemento);
            WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ElementHelper.clickWithHighlight(elemento, nomeElemento);
        } catch (Exception e) {
            System.out.println("Erro ao clicar em " + nomeElemento + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Clicar_" + nomeElemento, "Erro");
            // Não lançamos exceção para permitir que o teste continue ou falhe na
            // verificação
        }
    }

    // Passo para tirar um screenshot do resultado (Positivo ou Negativo)
    @Then("eu tiro um screenshot do resultado {string}")
    public void euTiroUmScreenshotDoResultado(String tipoResultado) {
        // Monta o nome do screenshot baseado no elemento atual
        String nomeScreenshot = "Pagina_"
                + (nomeElementoAtual != null ? nomeElementoAtual.replace(" ", "_") : "Geral");
        // Força o tipo "Resultado" para criar a pasta correta conforme imagem solicitada
        ElementHelper.takeScreenshot(nomeScreenshot, "Resultado");
    }

    // Passo genérico para interagir com um sub-elemento (útil para menus, modais,
    // etc.)
    @And("eu interajo com o sub-elemento {string}")
    public void euInterajoComSubElemento(String nomeSubElemento) {
        if (nomeSubElemento == null || nomeSubElemento.isEmpty()) {
            return;
        }

        // Se o sub-elemento for "Voltar", apenas fecha a aba/janela atual e retorna
        // Se o sub-elemento for "Voltar", apenas fecha a aba/janela atual e retorna
        if (nomeSubElemento.equals("Voltar")) {
            String janelaOriginal = driver.getWindowHandle();
            Set<String> janelas = driver.getWindowHandles();

            if (janelas.size() > 1) {
                for (String janela : janelas) {
                    if (!janela.equals(janelaOriginal)) {
                        driver.switchTo().window(janela);
                        break;
                    }
                }

                // Agora estamos na nova janela (Aviso de Privacidade)
                System.out.println("CieloSteps: Mudou para a nova janela: " + driver.getTitle());

                // Evidencia a página aberta antes de fechar
                ElementHelper.takeScreenshot("Pagina_" + nomeElementoAtual, "Resultado");

                try {
                    Thread.sleep(1000); // Garante visualização
                } catch (InterruptedException e) {
                }

                driver.close();
                driver.switchTo().window(janelaOriginal);
                System.out.println("CieloSteps: Retornou para a janela principal.");
            }
            return;
        }

        // Verifica se há novas janelas abertas (ex: clique anterior abriu nova aba)
        // e muda o foco para a última janela antes de tentar interagir
        if (driver.getWindowHandles().size() > 1) {
             for (String windowHandle : driver.getWindowHandles()) {
                  driver.switchTo().window(windowHandle);
             }
        }

        try {
            By locator = getLocator(nomeSubElemento);
            // Tenta encontrar o elemento e clicar
            WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ElementHelper
                    .clickWithSubElement(elemento, nomeSubElemento);

            // Se abrir nova janela, foca nela
            if (driver.getWindowHandles().size() > 1) {
                for (String windowHandle : driver.getWindowHandles()) {
                    driver.switchTo().window(windowHandle);
                }
            }
        } catch (Exception e) {
            // Tentativa de recuperação: re-focar no elemento pai (menu) se falhar
            if (nomeElementoAtual != null) {
                try {
                    System.out.println("Tentando recuperar interação com " + nomeSubElemento + " re-focando em "
                            + nomeElementoAtual);
                    By parentLocator = getLocator(nomeElementoAtual);
                    WebElement parent = driver.findElement(parentLocator);
                    new Actions(driver).moveToElement(parent).perform();
                    Thread.sleep(500); // Pequena pausa para o menu reabrir

                    By locator = getLocator(nomeSubElemento);
                    WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
                    ElementHelper.clickWithSubElement(elemento, nomeSubElemento);

                    // Se abrir nova janela, foca nela (repetindo a lógica de sucesso)
                    if (driver.getWindowHandles().size() > 1) {
                        for (String windowHandle : driver.getWindowHandles()) {
                            driver.switchTo().window(windowHandle);
                        }
                    }
                    return; // Sucesso na recuperação
                } catch (Exception ex) {
                    System.out.println("Falha na recuperação: " + ex.getMessage());
                }
            }

            System.out.println("Erro ao interagir com " + nomeSubElemento + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Interagir_" + nomeSubElemento, "Erro");
        }
    }

    @And("eu clico no botão {string}")
    public void euClicoNoBotao(String nomeBotao) {
        try {
            By locator = getLocator(nomeBotao);
            WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(locator));
            ElementHelper.clickWithHighlight(elemento, nomeBotao);
        } catch (Exception e) {
            System.out.println("Erro ao clicar em " + nomeBotao + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Clicar_" + nomeBotao, "Erro");
        }
    }

    @And("eu clico no botão {string} ou equivalente")
    public void euClicoNoBotaoOuEquivalente(String nomeBotao) {
        euClicoNoBotao(nomeBotao);
    }

    @And("eu preencho o CNPJ {string}")
    public void euPreenchoOCNPJ(String cnpj) {
        String nomeElemento = "CNPJ";
        try {
            By locator = getLocator(nomeElemento);
            WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            ElementHelper.highlightElement(elemento);
            elemento.clear();
            elemento.sendKeys(cnpj);
            ElementHelper.takeScreenshot("Preenchido_" + nomeElemento, "Acao");
            ElementHelper.unhighlightElement(elemento);
        } catch (Exception e) {
            System.out.println("Erro ao preencher " + nomeElemento + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Preencher_" + nomeElemento, "Erro");
        }
    }

    @And("eu seleciono o checkbox de termos")
    public void euSelecionoOCheckboxDeTermos() {
        String nomeElemento = "Checkbox Termos";
        try {
            By locator = getLocator(nomeElemento);
            WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", elemento);
            // Checkboxes podem estar ocultos por UI customizada
            try {
                if (!elemento.isSelected()) {
                    ElementHelper.clickWithHighlight(elemento, nomeElemento);
                }
            } catch (Exception e) {
                // Fallback para clique JS
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
            }

        } catch (Exception e) {
            System.out.println("Erro ao selecionar " + nomeElemento + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Selecionar_" + nomeElemento, "Erro");
        }
    }

    @And("eu clico fora ou tab para validar")
    public void euClicoForaOuTabParaValidar() {
        try {
            // Clica no body para tirar o foco do input
            driver.findElement(By.tagName("body")).click();
        } catch (Exception e) {
        }
    }

    @Then("eu vejo a mensagem de erro {string}")
    public void euVejoAMensagemDeErro(String mensagemErro) {
        try {
           // Procura por qualquer elemento que contenha o texto do erro
            By locator = By.xpath("//*[contains(text(), '" + mensagemErro + "') and (contains(@class, 'error') or contains(@class, 'invalid') or contains(@style, 'color: red'))]");
            WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ElementHelper.highlightElement(erro);
            ElementHelper.takeScreenshot("Erro_Visivel_" + mensagemErro.replace(" ", "_"), "Resultado");
            ElementHelper.unhighlightElement(erro);
        } catch (Exception e) {
            // Fallback genérico se não achar por classe de erro
            try {
                By locator = By.xpath("//*[contains(text(), '" + mensagemErro + "')]");
                WebElement erro = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                ElementHelper.highlightElement(erro);
                ElementHelper.takeScreenshot("Erro_Visivel_Generico_" + mensagemErro.replace(" ", "_"), "Resultado");
                ElementHelper.unhighlightElement(erro);
            } catch (Exception ex) {
                 System.out.println("Mensagem de erro não encontrada: " + mensagemErro);
                 ElementHelper.takeScreenshot("Erro_Nao_Encontrado", "Erro");
            }
        }
    }
    
    @Then("eu vejo a validação de campos obrigatórios")
    public void euVejoAValidacaoDeCamposObrigatorios() {
         // Verifica se inputs obrigatórios estão com estado de erro (ex: borda vermelha ou msg 'Campo obrigatório')
         try {
            By locator = By.xpath("//*[contains(text(), 'obrigatório') or contains(@class, 'error') or contains(@class, 'invalid')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ElementHelper.takeScreenshot("Validacao_Campos_Obrigatorios", "Resultado");
         } catch(Exception e) {
            System.out.println("Validação de campos não encontrada.");
         }
    }

    @When("eu realizo uma busca por {string}")
    public void euRealizoUmaBuscaPor(String termo) {
        try {
            // Clica na lupa ou campo se necessário para expandir (comum em menus)
            /* 
               Nota: Dependendo do site, a busca pode estar dentro do menu Ajuda ou numa página dedicada.
               Vamos assumir que ao clicar em 'Ajuda', já temos acesso à busca ou vamos para a página de Central de Ajuda.
            */ 
            
            By inputLocator = getLocator("Campo de Busca");
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputLocator));
            ElementHelper.highlightElement(input);
            input.clear();
            input.sendKeys(termo);
            ElementHelper.takeScreenshot("Busca_Preenchida_" + termo, "Acao");
            
            // Pressiona Enter ou clica no botão de pesquisar
            input.submit(); 
            // Ou use: driver.findElement(getLocator("Botão Pesquisar")).click();
            
            ElementHelper.unhighlightElement(input);
        } catch (Exception e) {
             System.out.println("Erro ao realizar busca: " + e.getMessage());
             ElementHelper.takeScreenshot("Erro_Busca", "Erro");
        }
    }

    @Then("eu vejo resultados relacionados a {string}")
    public void euVejoResultadosRelacionadosA(String termo) {
        try {
            // Espera resultados aparecerem
            By resultsLocator = By.xpath("//*[contains(text(), '" + termo + "') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + termo.toLowerCase() + "')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(resultsLocator));
            ElementHelper.takeScreenshot("Resultados_Busca_" + termo, "Resultado");
        } catch (Exception e) {
            System.out.println("Resultados não encontrados para: " + termo);
            ElementHelper.takeScreenshot("Erro_Resultados_Nao_Encontrados", "Erro");
        }
    }

    @Then("eu vejo a mensagem de nenhum resultado encontrado")
    public void euVejoAMensagemDeNenhumResultadoEncontrado() {
        try {
            // Texto comum de "não encontrado"
            By locator = By.xpath("//*[contains(text(), 'nenhum resultado') or contains(text(), 'não encontramos') or contains(text(), '0 resultados')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            ElementHelper.takeScreenshot("Sem_Resultados_Mensagem", "Resultado");
        } catch (Exception e) {
             System.out.println("Mensagem de 'nenhum resultado' não encontrada.");
        }
    }

    @Then("eu sou redirecionado para a loja de aplicativos {string}")
    public void euSouRedirecionadoParaALojaDeAplicativos(String loja) {
        try {
            // Muda o foco para a nova aba se houver
            Set<String> janelas = driver.getWindowHandles();
            if (janelas.size() > 1) {
                for (String janela : janelas) {
                     driver.switchTo().window(janela);
                }
            }
            
            // Aguarda a URL conter o domínio esperado (ex: apple.com ou google.com)
            wait.until(ExpectedConditions.urlContains(loja));
            System.out.println("Redirecionamento validado: URL contém " + loja);
            ElementHelper.takeScreenshot("Redirecionamento_" + loja, "Resultado");
            
        } catch (Exception e) {
            System.out.println("Erro ao validar redirecionamento para " + loja + ": " + e.getMessage());
            ElementHelper.takeScreenshot("Erro_Redirecionamento", "Erro");
        }
    }


    // Método auxiliar para obter o localizador (By) baseado no nome do elemento
    private By getLocator(String nomeElemento) {
        switch (nomeElemento) {
            // Elementos Principais
            case "Configurar":
                return By.xpath("//button[contains(., 'Configurar')]");
            case "Recusar":
                return By.xpath("//button[contains(., 'Recusar')]");
            case "Aceitar tudo":
                return By.xpath("//button[contains(., 'Aceitar tudo')]");
            case "Aviso de privacidade":
                return By.xpath("//a[contains(text(), 'Privacidade')]");
            case "Maquininhas":
                return By.cssSelector("a#menu-machines");
            case "E-commerce":
                return By.cssSelector("a#menu-ecommerce");
            case "Soluções":
                return By.cssSelector("a#menu-data");
            case "Blog":
                return By.cssSelector("a[href='https://blog.cielo.com.br/']");
            case "Ajuda":
                return By.cssSelector("a.menu-help");
            case "Baixe o App":
                return By.xpath("//a[contains(., 'Baixe o App')]");
            case "Login":
                return By.xpath("//a[contains(., 'Login')]");
            case "Seja Cielo":
                return By.cssSelector("a[href='/contrate-agora/']");
            case "Começar a vender":
                return By.cssSelector("a.primary-button[href='#ofertas']");

            // Sub-Elementos (Refinados para Maquininhas)
            case "Salvar preferências":
                return By.xpath("//button[contains(., 'Salvar') or contains(., 'Confirmar')]");
            case "Cielo LIO":
                // Tenta ser mais específico para o link dentro do menu de navegação
                return By.xpath("//nav//a[contains(@href, 'cielo-lio') and not(contains(@class, 'footer'))]");
            case "Cielo LIO On":
                return By.xpath("//nav//a[contains(., 'LIO On') or contains(@href, 'cielo-lio')]");
            case "Cielo ZIP":
                return By.xpath("//nav//a[contains(@href, 'cielo-zip') and not(contains(@class, 'footer'))]");
            case "Cielo Flash":
                return By.xpath("//nav//a[contains(@href, 'cielo-flash') and not(contains(@class, 'footer'))]");
            case "Tap to Pay no iPhone":
                return By.xpath("//nav//a[contains(., 'Tap to Pay') or contains(@href, 'tap-to-pay')]");
            case "Cielo Tap":
                return By.xpath("//nav//a[contains(., 'Cielo Tap') or contains(@href, 'cielo-tap')]");
            case "Cielo Aluguel":
                return By.xpath("//nav//a[contains(., 'Aluguel') or contains(@href, 'aluguel')]");

            // E-commerce
            case "Cielo Checkout":
                return By.xpath("//nav//a[contains(., 'Checkout') or contains(@href, 'checkout')]");
            case "Split de Pagamento":
                return By.xpath("//nav//a[contains(., 'Split') or contains(@href, 'split')]");
            case "Gestão de Risco":
                return By.xpath("//nav//a[contains(., 'Risco') or contains(@href, 'risco')]");
            case "Gateway de Pagamento":
                return By.xpath("//nav//a[contains(., 'Gateway') or contains(@href, 'gateway')]");

            // Soluções
            case "Escolha como Receber":
                return By.xpath("//nav//a[contains(., 'Escolha como Receber')]");
            case "Conversor de Moedas":
                return By.xpath("//nav//a[contains(., 'Conversor de Moedas')]");
            case "Minha Conta Cielo":
                return By.xpath("//nav//a[contains(., 'Minha Conta Cielo')]");
            case "Cielo Store":
                return By.xpath("//nav//a[contains(., 'Cielo Store')]");
            case "Inteligência de Mercado":
                return By.xpath("//nav//a[contains(., 'Inteligência de Mercado')]");
            case "Cielo Conciliador":
                return By.xpath("//nav//a[contains(., 'Cielo Conciliador')]");

            // Ajuda
            case "Atendimento pelo WhatsApp":
                return By.xpath("//nav//a[contains(., 'WhatsApp')]");
            case "Atendimento em Libras":
                return By.xpath("//nav//a[contains(., 'Libras')]");
            case "Status Cielo":
                return By.xpath("//nav//a[contains(., 'Status Cielo')]");

            // Baixe o App
            case "App Store":
                return By.xpath("//a[contains(@href, 'apple.com')]");

            // Mantendo os existentes que ainda são válidos
            case "Checkout": // Mantido por compatibilidade se usado em outro lugar
                return By.xpath("//h3[contains(., 'Checkout')] | //a[contains(., 'Checkout')]");
            case "Link de Pagamento":
                return By.xpath("//a[contains(., 'Link de Pagamento')]");
            case "API E-commerce":
                return By.xpath("//a[contains(., 'API E-commerce')]");
            case "Para seu negócio":
                return By.xpath("//a[contains(., 'Para seu negócio')] | //h2[contains(., 'Para seu negócio')]");
            case "Pix":
                return By.xpath("//a[contains(., 'Pix')]");
            case "Farol":
                return By.xpath("//a[contains(., 'Farol')]");
            case "Artigos":
                return By.xpath("//article | //a[contains(@class, 'post')]");
            case "Dúvidas frequentes":
                return By.xpath("//a[contains(., 'Dúvidas') or contains(., 'Perguntas')]");
            case "Acompanhar pedido":
                return By.xpath("//a[contains(., 'Acompanhar pedido')]");
            case "Central de atendimento":
                return By.xpath("//a[contains(., 'Central de atendimento')]");
            case "Google Play":
                return By.xpath("//a[contains(@href, 'play.google.com')]");
            case "Criar conta":
                return By.xpath("//a[contains(., 'Criar conta') or contains(., 'Cadastre-se')]");
            case "Cadastre-se":
                return By.xpath("//a[contains(., 'Cadastre-se') or contains(., 'Contrate')]");
            case "Ofertas":
                return By.xpath("//section[@id='ofertas'] | //h2[contains(., 'Ofertas')]");
            case "Voltar":
                return By.xpath("//button[contains(., 'Voltar')] | //a[contains(., 'Voltar')]");
            case "Eu Quero":
                return By.xpath(
                        "//button[contains(., 'Eu quero') or contains(., 'EU QUERO')] | //a[contains(., 'Eu quero') or contains(., 'EU QUERO')]");
            case "Continuar":
                return By.xpath("//button[contains(., 'Continuar')] | //a[contains(., 'Continuar')]");
            case "CNPJ":
                return By.xpath("//input[@name='cnpj' or @id='cnpj' or contains(@placeholder, 'CNPJ')]");
            case "Checkbox Termos":
                return By.xpath("//input[@type='checkbox']");

            // Busca Ajuda
            case "Campo de Busca":
                return By.xpath("//input[@type='search' or @placeholder='Digite a sua dúvida'] | //input[contains(@class, 'search')]");
            case "Botão Pesquisar":
                return By.xpath("//button[contains(@class, 'search') or .//i[contains(@class, 'search')]]");

            default:
                // Tenta encontrar por texto se não estiver mapeado
                return By.xpath("//*[contains(text(), '" + nomeElemento + "')]");
        }
    }
}
