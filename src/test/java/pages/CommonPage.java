package pages;

import org.openqa.selenium.By;

public class CommonPage extends BasePage {

    // Menu Navigation
    private final By linkMaquininhas = By.id("menu-machines");
    private final By linkEcommerce = By.id("menu-ecommerce");
    private final By linkSolucoes = By.id("menu-data");
    private final By linkBlog = By.cssSelector("a[href='https://blog.cielo.com.br/']");
    private final By linkAjuda = By.cssSelector("a.menu-help");
    private final By linkBaixeApp = By.xpath("//a[contains(., 'Baixe o App')]");
    private final By linkLogin = By.xpath("//a[contains(., 'Login')]");
    private final By linkSejaCielo = By.cssSelector("a[href='/contrate-agora/']");
    
    // Help Sub-links (recovered from logs)
    private final By linkAtendimentoWhatsapp = By.xpath("//a[contains(., 'WhatsApp')]");
    private final By linkAtendimentoLibras = By.xpath("//a[contains(., 'Libras')]");
    private final By linkStatusCielo = By.xpath("//a[contains(., 'Status')]");
    private final By linkCentralAtendimento = By.xpath("//a[contains(., 'Central de atendimento')]");
    private final By linkDuvidasFrequentes = By.xpath("//a[contains(., 'Dúvidas frequentes')]");
    private final By linkAcompanharPedido = By.xpath("//a[contains(., 'Acompanhar pedido')]");
    
    // Configurações e Cookies
    private final By btnConfigurarCookies = By.xpath("//button[contains(., 'Configurar')]");
    private final By btnRecusarCookies = By.xpath("//button[contains(., 'Recusar')]");
    private final By btnAceitarCookies = By.xpath("//button[contains(., 'Aceitar tudo')]");
    private final By linkPrivacidade = By.xpath("//a[contains(text(), 'Privacidade')]");

    // Actions
    public void acessarMaquininhas() {
        click(linkMaquininhas);
    }

    public void acessarEcommerce() {
        click(linkEcommerce);
    }

    public void acessarSolucoes() {
        click(linkSolucoes);
    }

    public void acessarAjuda() {
        click(linkAjuda);
    }
    
    public void clicarLogin() {
        click(linkLogin);
    }
    
    public void clicarBaixeApp() {
        click(linkBaixeApp);
    }
    
    public void clicarSejaCielo() {
        click(linkSejaCielo);
    }

    public void aceitarCookies() {
        if (isElementVisible(btnAceitarCookies)) {
            click(btnAceitarCookies);
        }
    }

    public void configurarCookies() {
        if (isElementVisible(btnConfigurarCookies)) {
            click(btnConfigurarCookies);
        }
    }

    public void recusarCookies() {
        if (isElementVisible(btnRecusarCookies)) {
            click(btnRecusarCookies);
        }
    }

    public void acessarPrivacidade() {
        click(linkPrivacidade);
    }
    
    // Help specific methods
    public void acessarSubMenuAjuda(String subMenu) {
        switch (subMenu) {
            case "Atendimento pelo WhatsApp": click(linkAtendimentoWhatsapp); break;
            case "Atendimento em Libras": click(linkAtendimentoLibras); break;
            case "Status Cielo": click(linkStatusCielo); break;
            case "Central de atendimento": click(linkCentralAtendimento); break;
            case "Dúvidas frequentes": click(linkDuvidasFrequentes); break;
            case "Acompanhar pedido": click(linkAcompanharPedido); break;
            default: throw new IllegalArgumentException("Sub-menu Ajuda não mapeado: " + subMenu);
        }
    }
    
    public void acessarSubMenuCookies(String subMenu) {
        By locator = By.xpath("//button[contains(., '" + subMenu + "')] | //a[contains(., '" + subMenu + "')]");
        if (isElementVisible(locator)) {
            click(locator);
        } else {
            throw new IllegalArgumentException("Sub-menu de Cookies não encontrado: " + subMenu);
        }
    }

    // Generic navigation method matching the previous style but cleaner
    public void acessarMenu(String menu) {
        switch (menu) {
            case "Maquininhas": acessarMaquininhas(); break;
            case "E-commerce": acessarEcommerce(); break;
            case "Soluções": acessarSolucoes(); break;
            case "Ajuda": acessarAjuda(); break;
            case "Login": clicarLogin(); break;
            default:
                throw new IllegalArgumentException("Menu não mapeado: " + menu);
        }
    }
}
