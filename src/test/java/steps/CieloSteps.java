package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import pages.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CieloSteps {

    // Page Objects injetados via PicoContainer (ou instanciados no construtor)
    private final HomePage homePage;
    private final CommonPage commonPage;
    private final MaquininhasPage maquininhasPage;
    private final EcommercePage ecommercePage;
    private final SolucoesPage solucoesPage;
    private final LioPage lioPage;          // [NEW]
    private final CheckoutPage checkoutPage; // [NEW]
    private final WebDriver driver;
    private Response apiResponse;

    public CieloSteps() {
        this.homePage = new HomePage();
        this.commonPage = new CommonPage();
        this.maquininhasPage = new MaquininhasPage();
        this.ecommercePage = new EcommercePage();
        this.solucoesPage = new SolucoesPage();
        this.lioPage = new LioPage();             // [NEW]
        this.checkoutPage = new CheckoutPage();   // [NEW]
        this.driver = utils.DriverFactory.getDriver();
    }

    @Given("que eu acesso a página inicial da Cielo")
    public void queEuAcessoAPaginaInicialDaCielo() {
        homePage.acessarPagina();
    }

    @When("o popup de cookies ou configuração aparece")
    public void oPopupDeCookiesOuConfiguracaoAparece() {
        // Opzional: A lógica de verificação silenciosa pode ficar aqui ou na Page
    }

    @Given("eu fecho o popup")
    public void euFechoOPopup() {
        commonPage.aceitarCookies();
    }

    @When("eu destaco e clico no elemento {string}")
    public void euDestacoEClicoNoElemento(String nomeElemento) {
        switch (nomeElemento) {
            case "Maquininhas": commonPage.acessarMaquininhas(); break;
            case "E-commerce": commonPage.acessarEcommerce(); break;
            case "Soluções": commonPage.acessarSolucoes(); break;
            case "Ajuda": commonPage.acessarAjuda(); break;
            case "Login": commonPage.clicarLogin(); break;
            case "Blog": driver.get("https://blog.cielo.com.br/"); break; // Simple navigation

            // Cookies
            case "Aceitar tudo": commonPage.aceitarCookies(); break;
            case "Configurar": commonPage.configurarCookies(); break;
            case "Recusar": commonPage.recusarCookies(); break;
            case "Aviso de privacidade": commonPage.acessarPrivacidade(); break;
            
            // Novos itens recuperados
            case "Baixe o App": commonPage.clicarBaixeApp(); break;
            case "Seja Cielo": commonPage.clicarSejaCielo(); break;
            
            // Home CTAs
            case "Maquininha no celular": homePage.clicarCtaMaquininhaCelular(); break;
            case "Soluções personalizadas": homePage.clicarCtaSolucoes(); break;
            case "CTA E-commerce": homePage.clicarCtaEcommerce(); break;
            
            default:
                throw new IllegalArgumentException("Elemento principal não mapeado nos Steps: " + nomeElemento);
        }
    }

    @Then("eu tiro um screenshot do resultado {string}")
    public void euTiroUmScreenshotDoResultado(String tipoResultado) {
        utils.ElementHelper.takeScreenshot(tipoResultado, "Resultado");
    }

    @And("eu interajo com o sub-elemento {string}")
    public void euInterajoComSubElemento(String nomeSubElemento) {
        if (nomeSubElemento == null || nomeSubElemento.isEmpty()) return;

        // Tenta interagir em diferentes contextos (Cookies, Maquininhas, etc)
        try { commonPage.acessarSubMenuCookies(nomeSubElemento); return; } catch (Exception e) {}
        try { maquininhasPage.acessarSubMenu(nomeSubElemento); return; } catch (Exception e) {}
        try { ecommercePage.acessarSubMenu(nomeSubElemento); return; } catch (Exception e) {}
        try { solucoesPage.acessarSubMenu(nomeSubElemento); return; } catch (Exception e) {}
        try { commonPage.acessarSubMenuAjuda(nomeSubElemento); return; } catch (Exception e) {}

        throw new IllegalArgumentException("Sub-elemento não encontrado em nenhuma pagina mapeada: " + nomeSubElemento);
    }

    @And("eu clico no botão {string} ou equivalente")
    public void euClicoNoBotaoOuEquivalente(String nomeBotao) {
        // Mapeamento genérico para botões diversos espalhados pelo site
        org.openqa.selenium.By locator = org.openqa.selenium.By.xpath(
            "//a[contains(., '" + nomeBotao + "')] | " +
            "//button[contains(., '" + nomeBotao + "')] | " +
            "//span[contains(., '" + nomeBotao + "')]"
        );
        
        if (commonPage.isElementVisible(locator)) {
            utils.ElementHelper.clickWithHighlight(driver.findElement(locator), nomeBotao);
        } else {
             // Tenta achar via texto aproximado se falhar exato
             locator = org.openqa.selenium.By.xpath("//*[contains(text(), '" + nomeBotao + "')]");
             utils.ElementHelper.clickWithHighlight(driver.findElement(locator), nomeBotao);
        }
    }

    @And("eu clico no botão {string}")
    public void euClicoNoBotao(String nomeBotao) {
         euClicoNoBotaoOuEquivalente(nomeBotao);
    }
    
    @And("eu preencho o CNPJ {string}")
    public void euPreenchoOCNPJ(String cnpj) {
        checkoutPage.preencherCnpj(cnpj);
    }

    @And("eu seleciono o checkbox de termos")
    public void euSelecionoOCheckboxDeTermos() {
       checkoutPage.aceitarTermos();
    }

    @When("eu realizo uma busca por {string}")
    public void euRealizoUmaBuscaPor(String termo) {
        org.openqa.selenium.By locator = org.openqa.selenium.By.xpath("//input[@type='search' or @placeholder='Digite a sua dúvida'] | //input[contains(@class, 'search')]");
        org.openqa.selenium.WebElement input = driver.findElement(locator);
        utils.ElementHelper.sendKeysWithHighlight(input, termo, "Busca");
        input.submit();
    }

    @Then("eu vejo resultados relacionados a {string}")
    public void euVejoResultadosRelacionadosA(String termo) {
        org.openqa.selenium.By locator = org.openqa.selenium.By.xpath("//*[contains(text(), '" + termo + "') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + termo.toLowerCase() + "')]");
        if (!commonPage.isElementVisible(locator)) {
            throw new AssertionError("Resultados não encontrados para: " + termo);
        }
    }
    
    @Then("eu vejo a mensagem de nenhum resultado encontrado")
    public void euVejoAMensagemDeNenhumResultadoEncontrado() {
         org.openqa.selenium.By locator = org.openqa.selenium.By.xpath("//*[contains(text(), 'nenhum resultado') or contains(text(), 'não encontramos')]");
         if (!commonPage.isElementVisible(locator)) {
             throw new AssertionError("Mensagem de nenhum resultado não encontrada");
         }
    }

    @Then("eu vejo a mensagem de erro {string}")
    public void euVejoAMensagemDeErro(String mensagemErro) {
        org.openqa.selenium.By locator = org.openqa.selenium.By.xpath("//*[contains(text(), '" + mensagemErro + "')]");
        if (!commonPage.isElementVisible(locator)) {
             throw new AssertionError("Mensagem de erro não encontrada: " + mensagemErro);
        }
    }

    @Then("eu vejo a validação de campos obrigatórios")
    public void euVejoAValidacaoDeCamposObrigatorios() {
        org.openqa.selenium.By locator = org.openqa.selenium.By.xpath("//*[contains(text(), 'obrigatório') or contains(@class, 'error') or contains(@class, 'invalid')]");
        if (!commonPage.isElementVisible(locator)) {
            throw new AssertionError("Validação de campos obrigatórios não visível");
        }
    }

    @Then("eu verifico todos os elementos da tela de {string}")
    public void euVerificoTodosOsElementosDaTela(String pagina) {
        BasePage activePage;
        switch (pagina.toLowerCase()) {
            case "home":
            case "página inicial": activePage = homePage; break;
            case "maquininhas": activePage = maquininhasPage; break;
            case "e-commerce": activePage = ecommercePage; break;
            case "soluções": activePage = solucoesPage; break;
            case "lio": activePage = lioPage; break;
            case "login": activePage = new LoginPage(); break;
            default:
                throw new IllegalArgumentException("Página não mapeada para varredura: " + pagina);
        }
        activePage.waitForPageLoad();
        activePage.validateAllElementsOnPage();
    }

    @Then("eu vejo o elemento {string}")
    public void euVejoOElemento(String nomeElemento) {
        // ... (conteúdo existente)
    }

    // --- NOVOS STEPS DE API ---

    @Given("que eu inicializo a requisição para a API da Cielo")
    public void queEuInicializoARequisicaoParaAApiDaCielo() {
        RestAssured.baseURI = "https://www.cielo.com.br";
    }

    @When("eu disparo um GET para o endpoint de saúde do sistema")
    public void euDisparoUmGetParaOEndpointDeSaudeDoSistema() {
        apiResponse = RestAssured.get("/");
    }

    @Then("eu recebo um status code {int}")
    public void euReceboUmStatusCode(int statusCode) {
        assertEquals(statusCode, apiResponse.getStatusCode());
    }

    @And("a mensagem de resposta deve ser válida")
    public void aMensagemDeRespostaDeveSerValida() {
        assertTrue(apiResponse.getTime() < 5000, "API demorou mais que 5 segundos");
        assertTrue(apiResponse.getContentType().contains("text/html"), "Content type inesperado");
    }
}
