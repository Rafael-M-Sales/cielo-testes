package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By linkLogin = By.xpath("//a[contains(., 'Login')]");
    private final By inputCpfCnpj = By.id("cpf-cnpj"); // Hypothetical ID, based on common patterns
    private final By btnContinuar = By.xpath("//button[contains(., 'Continuar')]");

    public void clicarEmLogin() {
        click(linkLogin);
    }
}
