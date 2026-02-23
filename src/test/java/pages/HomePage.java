package pages;

import org.openqa.selenium.By;

public class HomePage extends BasePage {

    private final By btnAceitarCookies = By.xpath("//button[contains(., 'Aceitar tudo')]");
    private final By linkMaquininhas = By.id("menu-machines");
    private final By linkEcommerce = By.id("menu-ecommerce");
    private final By linkSolucoes = By.id("menu-data");
    private final By linkBlog = By.cssSelector("a[href='https://blog.cielo.com.br/']");
    private final By linkAjuda = By.cssSelector("a.menu-help");
    private final By linkBaixeApp = By.xpath("//a[contains(., 'Baixe o App')]");
    private final By linkLogin = By.xpath("//a[contains(., 'Login')]");
    
    public void acessarPagina() {
        driver.get("https://www.cielo.com.br/");
    }

    public void aceitarCookies() {
        if(isElementVisible(btnAceitarCookies)) {
            click(btnAceitarCookies);
        }
    }

    public void acessarMaquininhas() {
        click(linkMaquininhas);
    }

    public void acessarEcommerce() {
        click(linkEcommerce);
    }

    // Home Specific Locators
    private final By btnMaquininhaNoCelular = By.xpath("//button[contains(@class, 'cta') and .//span[contains(text(), 'Maquininha no celular')]]");
    private final By btnSolucoesPersonalizadas = By.xpath("//button[contains(@class, 'cta') and .//span[contains(text(), 'Soluções personalizadas')]]");
    private final By btnCtaEcommerce = By.xpath("//button[contains(@class, 'cta') and .//span[contains(text(), 'E-commerce')]]");

    public void clicarCtaMaquininhaCelular() {
        click(btnMaquininhaNoCelular);
    }

    public void clicarCtaSolucoes() {
        click(btnSolucoesPersonalizadas);
    }
    
    public void clicarCtaEcommerce() {
        click(btnCtaEcommerce);
    }
}
