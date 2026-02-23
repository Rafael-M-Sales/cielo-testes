package utils;

import org.junit.jupiter.api.Test;

/**
 * Teste JUnit para validar e executar a captura de API.
 * <p>
 * Este teste serve como facilitador para executar a ferramenta de captura 
 * diretamente através da interface de execução de testes da IDE.
 * </p>
 */
public class ApiCaptureTest {
    
    /**
     * Executa a captura na home da Cielo e gera o arquivo api_endpoints.json.
     */
    @Test
    public void captureApis() {
        // Define a URL base e o caminho de saída
        String url = "https://www.cielo.com.br";
        String outputPath = "src/test/resources/api_endpoints.json";
        
        // Invoca o helper para realizar a captura
        ApiCaptureHelper.capture(url, outputPath);
    }
}
