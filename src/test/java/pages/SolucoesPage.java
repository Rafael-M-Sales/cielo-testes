package pages;

import org.openqa.selenium.By;

public class SolucoesPage extends BasePage {

    // Sub-menus
    private final By linkEscolhaReceber = By.xpath("//nav//a[contains(., 'Escolha como Receber')]");
    private final By linkConversorMoedas = By.xpath("//nav//a[contains(., 'Conversor de Moedas')]");
    private final By linkMinhaConta = By.xpath("//nav//a[contains(., 'Minha Conta Cielo')]");
    private final By linkCieloStore = By.xpath("//nav//a[contains(., 'Cielo Store')]");
    private final By linkInteligenciaMercado = By.xpath("//nav//a[contains(., 'Inteligência de Mercado')]");
    private final By linkConciliador = By.xpath("//nav//a[contains(., 'Cielo Conciliador')]");
    private final By linkOfertas = By.xpath("//nav//a[contains(., 'Ofertas')]"); // Missing

    // Page Elements
    private final By tituloSolucoes = By.xpath("//h1[contains(., 'Soluções') or contains(., 'negócio')] | //h2[contains(., 'Soluções')]");

    public boolean isPaginaCarregada() {
        return isElementVisible(tituloSolucoes);
    }

    public void acessarSubMenu(String subMenu) {
        switch (subMenu) {
            case "Escolha como Receber": click(linkEscolhaReceber); break;
            case "Conversor de Moedas": click(linkConversorMoedas); break;
            case "Minha Conta Cielo": click(linkMinhaConta); break;
            case "Cielo Store": click(linkCieloStore); break;
            case "Inteligência de Mercado": click(linkInteligenciaMercado); break;
            case "Cielo Conciliador": click(linkConciliador); break;
            case "Ofertas": click(linkOfertas); break; // Handle 'Ofertas'
            default: throw new IllegalArgumentException("Sub-menu Soluções não mapeado: " + subMenu);
        }
    }
}
