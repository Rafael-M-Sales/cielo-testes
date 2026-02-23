package utils;

/**
 * Classe executável principal para rodar a captura de API manualmente.
 * <p>
 * Serve como um ponto de entrada alternativo ao JUnit para disparar a captura 
 * de tráfego de rede e gerar o arquivo de endpoints JSON.
 * </p>
 */
public class RunCapture {
    /**
     * Método main para execução via linha de comando ou IDE.
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        System.out.println("Starting capture...");
        // Inicia a captura na home da Cielo e salva em src/test/resources
        ApiCaptureHelper.capture("https://www.cielo.com.br", "src/test/resources/api_endpoints.json");
        System.out.println("Capture finished.");
    }
}
