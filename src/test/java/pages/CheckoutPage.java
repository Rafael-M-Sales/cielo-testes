package pages;

import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    // Identificação
    private final By inputCnpj = By.xpath("//input[@name='cnpj' or @id='cnpj' or contains(@placeholder, 'CNPJ')]");
    private final By inputNome = By.xpath("//input[@name='name' or @id='name' or contains(@placeholder, 'Nome')]");
    private final By inputEmail = By.xpath("//input[@name='email' or @id='email' or contains(@placeholder, 'E-mail')]");
    
    // Termos
    private final By checkboxTermos = By.xpath("//input[@type='checkbox']");
    
    // Navigation
    private final By btnContinuar = By.xpath("//button[contains(., 'Continuar') or contains(., 'Avançar')]");

    public void preencherCnpj(String cnpj) {
        type(inputCnpj, cnpj);
    }
    
    public void preencherDadosBasicos(String nome, String email) {
        type(inputNome, nome);
        type(inputEmail, email);
    }

    public void aceitarTermos() {
       try {
            if (isElementVisible(checkboxTermos) && !driver.findElement(checkboxTermos).isSelected()) {
                click(checkboxTermos);
            }
       } catch (Exception e) {
           // Fallback/Log se falhar mas não bloqueante critico
       }
    }

    public void clicarContinuar() {
        click(btnContinuar);
    }
    
    public boolean isPaginaCheckoutCarregada() {
        return isElementVisible(inputCnpj);
    }
}
