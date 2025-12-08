
package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// Define que o JUnit deve usar a classe Cucumber para executar os testes
@RunWith(Cucumber.class)
// Configurações do Cucumber
@CucumberOptions(
                // Caminho para os arquivos de feature
                features = "src/test/resources/features",
                // Pacote onde estão as classes de passos (Steps)
                glue = "steps",
                // Plugins para formatação da saída (console e relatório HTML)
                plugin = { "pretty", "html:target/cucumber-reports.html" },
                // Torna a saída do console mais legível
                monochrome = true)
public class TestRunner {
}
