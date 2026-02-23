package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;
import utils.ElementHelper;

/**
 * Classe de Hooks do Cucumber para configuração e finalização dos testes.
 * <p>
 * Define métodos que são executados antes e depois de cada cenário de teste.
 * É responsável por inicializar o WebDriver e lidar com falhas (screenshots) e encerramento.
 * </p>
 */
public class Hooks {

    /**
     * Executado antes de qualquer cenário.
     * <p>
     * Garante que o WebDriver esteja inicializado e pronto para uso.
     * </p>
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("Hooks: setUp - " + scenario.getName());
        ElementHelper.setScenarioName(scenario.getName());
        // Inicializa ou recupera a instância ativa do WebDriver
        DriverFactory.getDriver();
    }

    /**
     * Executado após cada cenário.
     * <p>
     * Verifica o status do cenário. Se tiver falhado, tira um screenshot de evidência.
     * Encerra o driver para garantir um estado limpo para o próximo teste (ou fim da execução).
     * </p>
     *
     * @param scenario O objeto Scenario contendo metadados da execução atual.
     */
    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Hooks: tearDown - " + scenario.getName());
        
        // Verifica se o teste falhou
        if (scenario.isFailed()) {
            // Tira um screenshot com o prefixo 'failed' para fácil identificação
            ElementHelper.takeScreenshot("failed_" + scenario.getName().replace(" ", "_"), "Erro");
        }
        
        // Finaliza a sessão do navegador
        DriverFactory.quitDriver();
    }
}
