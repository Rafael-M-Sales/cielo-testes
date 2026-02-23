package pages;

import org.openqa.selenium.By;

public class LioPage extends BasePage {

    // Hero Section
    private final By btnPecaSua = By.xpath("//section[contains(@class, 'hero')]//a[contains(., 'Peça a sua') or contains(., 'Compre agora')]");
    
    // Technical Specs (Example)
    private final By sectionFichaTecnica = By.id("ficha-tecnica");
    private final By btnVerDetalhes = By.xpath("//button[contains(., 'Ver detalhes') or contains(., 'Especificações')]");

    public void clicarPecaSua() {
        click(btnPecaSua);
    }

    public void verDetalhesTecnicos() {
        if (isElementVisible(btnVerDetalhes)) {
            click(btnVerDetalhes);
        }
        // Force scroll/highlight if needed
        if (isElementVisible(sectionFichaTecnica)) {
            highlight(driver.findElement(sectionFichaTecnica));
        }
    }
    
    public boolean isPaginaLioCarregada() {
        return isElementVisible(btnPecaSua);
    }
}
