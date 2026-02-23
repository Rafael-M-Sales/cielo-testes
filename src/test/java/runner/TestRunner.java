
package runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

/**
 * Classe principal para execução da suíte de testes cucumber com JUnit 5.
 * <p>
 * Configura o motor de execução 'cucumber', define a localização das features 
 * e dos steps (glue code), além de configurar os plugins de relatório.
 * </p>
 */
@Suite
@IncludeEngines("cucumber") // Define que o motor de execução é o Cucumber
@SelectClasspathResource("features") // Define onde estão os arquivos .feature (classpath)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps") // Define pacote dos steps definitions
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports.html, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm") // Plugins
public class TestRunner {
}
