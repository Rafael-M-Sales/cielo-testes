package pages;

import org.openqa.selenium.By;

public class EcommercePage extends BasePage {

    private final By menuEcommerce = By.id("menu-ecommerce");
    
    // Sub-elements
    // Sub-elements
    private final By linkCheckout = By.xpath("//nav//a[contains(., 'Checkout') or contains(@href, 'checkout')]");
    private final By linkSplit = By.xpath("//nav//a[contains(., 'Split') or contains(@href, 'split')]");
    private final By linkRisco = By.xpath("//nav//a[contains(., 'Risco') or contains(@href, 'risco')]");
    private final By linkGateway = By.xpath("//nav//a[contains(., 'Gateway') or contains(@href, 'gateway')]");
    private final By linkApiEcommerce = By.xpath("//a[contains(., 'API E-commerce')]");
    
    private final By tituloEcommerce = By.xpath("//h1[contains(., 'E-commerce') or contains(., 'Venda Online')]");

    public void acessarPaginaEcommerce() {
        click(menuEcommerce);
    }
    
    public void acessarCheckout() {
        click(linkCheckout);
    }

    public void acessarSubMenu(String subMenu) {
        switch (subMenu) {
            case "Checkout": 
            case "Cielo Checkout": click(linkCheckout); break; // Alias
            case "Split de Pagamento": click(linkSplit); break;
            case "Gestão de Risco": click(linkRisco); break;
            case "Gateway de Pagamento": click(linkGateway); break;
            case "API E-commerce": click(linkApiEcommerce); break;
            default: throw new IllegalArgumentException("Sub-menu E-commerce não mapeado: " + subMenu);
        }
    }

    public boolean isPaginaEcommerceCarregada() {
        return isElementVisible(tituloEcommerce);
    }

    public void clicarCtaContrateAgora() {
        By cta = By.xpath("//main//a[contains(., 'Contrate agora') or contains(., 'Abra sua conta')]");
        click(cta);
    }
}
