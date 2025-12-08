package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;
import utils.ElementHelper;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("Hooks: setUp");
        DriverFactory.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("Hooks: tearDown - " + scenario.getName());
        if (scenario.isFailed()) {
            // Take a screenshot if the scenario fails
            ElementHelper.takeScreenshot("failed_" + scenario.getName().replace(" ", "_"));
        }
        DriverFactory.quitDriver();
    }
}
