package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigReaderTest {

    @Test
    public void testPropertyPrioritization() {
        // Test system property override
        System.setProperty("test.key", "systemValue");
        assertEquals("systemValue", ConfigReader.getProperty("test.key"));
        
        // Test default value
        assertEquals("default", ConfigReader.getProperty("non.existent", "default"));
        
        // Test config.properties fallback (assuming 'url' is in the file)
        assertEquals("https://www.cielo.com.br/", ConfigReader.getProperty("url"));
    }
}
