package pages;

import org.openqa.selenium.By;

public class MaquininhasPage extends BasePage {

    // Sub-menus (Locators from CieloSteps)
    private final By linkCieloLio = By.xpath("//nav//a[contains(@href, 'cielo-lio') and not(contains(@class, 'footer'))]");
    private final By linkCieloLioOn = By.xpath("//nav//a[contains(., 'LIO On') or contains(@href, 'cielo-lio')]");
    private final By linkCieloZip = By.xpath("//nav//a[contains(@href, 'cielo-zip') and not(contains(@class, 'footer'))]");
    private final By linkCieloFlash = By.xpath("//nav//a[contains(@href, 'cielo-flash') and not(contains(@class, 'footer'))]");
    private final By linkTapToPay = By.xpath("//nav//a[contains(., 'Tap to Pay') or contains(@href, 'tap-to-pay')]");
    private final By linkCieloTap = By.xpath("//nav//a[contains(., 'Cielo Tap') or contains(@href, 'cielo-tap')]");
    private final By linkConhecaTodas = By.xpath("//nav//a[contains(., 'Conheca todas') or contains(@href, 'maquininhas-cielo')]");
    private final By linkAluguel = By.xpath("//nav//a[contains(., 'Aluguel') or contains(@href, 'aluguel')]");

    // Page Elements
    private final By titulo = By.xpath("//h1[contains(., 'Maquininhas')]");
    private final By bannerMaquininhas = By.xpath("//h1[contains(., 'Maquininhas') or contains(., 'Ideal')] | //section[contains(@class, 'hero')]");

    // Methods
    public boolean isPaginaCarregada() {
        return isElementVisible(titulo) || isElementVisible(bannerMaquininhas);
    }

    public void acessarSubMenu(String subMenu) {
        switch (subMenu) {
            case "Cielo LIO": click(linkCieloLio); break;
            case "Cielo LIO On": click(linkCieloLioOn); break;
            case "Cielo ZIP": click(linkCieloZip); break;
            case "Cielo Flash": click(linkCieloFlash); break;
            case "Tap to Pay no iPhone": click(linkTapToPay); break;
            case "Cielo Tap": click(linkCieloTap); break;
            case "Conheça todas": click(linkConhecaTodas); break;
            case "Cielo Aluguel": click(linkAluguel); break;
            default: throw new IllegalArgumentException("Sub-menu Maquininhas não mapeado: " + subMenu);
        }
    }
}
