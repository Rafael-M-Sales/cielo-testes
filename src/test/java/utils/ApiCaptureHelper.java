package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

/**
 * Utilitário para captura de tráfego de rede (Network) usando Selenium ChromeDriver e Chrome DevTools Protocol (CDP).
 * <p>
 * Esta classe intercepta logs de performance do navegador para extrair requisições XHR/Fetch,
 * permitindo mapear endpoints da API chamados durante a navegação.
 * </p>
 */
public class ApiCaptureHelper {

    /**
     * Classe interna para estruturar os dados da requisição capturada.
     */
    public static class CapturedRequest {
        public String method;   // Método HTTP (GET, POST, etc.)
        public String url;      // URL completa
        public int status;      // Status Code (200, 404, etc.)
        public String mimeType; // Tipo de conteúdo (application/json, etc.)

        public CapturedRequest(String method, String url, int status, String mimeType) {
            this.method = method;
            this.url = url;
            this.status = status;
            this.mimeType = mimeType;
        }
    }

    /**
     * Navega para uma URL e captura todas as requisições de rede feitas pela página.
     * <p>
     * Utiliza o modo Headless do Chrome e acessa os logs do tipo 'PERFORMANCE'.
     * Filtra logs para correlacionar o início da requisição (requestWillBeSent) com a resposta (responseReceived).
     * </p>
     *
     * @param startUrl URL inicial para carregar.
     * @param outputJsonPath Caminho do arquivo JSON onde os dados serão salvos.
     */
    public static void capture(String startUrl, String outputJsonPath) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); // Executa sem interface gráfica
        
        // Habilita logs de performance para capturar eventos de rede
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        options.setCapability(ChromeOptions.LOGGING_PREFS, logPrefs);

        ChromeDriver driver = new ChromeDriver(options);
        List<CapturedRequest> captured = new ArrayList<>();
        Map<String, String> requestMethodMap = new ConcurrentHashMap<>();

        try {
            driver.get(startUrl);
            Thread.sleep(5000); // Aguarda carregamento inicial e requisições assíncronas

            List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
            for (LogEntry entry : entries) {
                try {
                    JsonObject message = JsonParser.parseString(entry.getMessage()).getAsJsonObject();
                    JsonObject params = message.getAsJsonObject("message").getAsJsonObject("params");
                    String method = message.getAsJsonObject("message").get("method").getAsString();

                    // Evento: Requisição iniciada
                    if ("Network.requestWillBeSent".equals(method)) {
                        String requestId = params.get("requestId").getAsString();
                        String reqMethod = params.getAsJsonObject("request").get("method").getAsString();
                        String url = params.getAsJsonObject("request").get("url").getAsString();
                        requestMethodMap.put(requestId, reqMethod + " " + url);
                    } 
                    // Evento: Resposta recebida
                    else if ("Network.responseReceived".equals(method)) {
                        String requestId = params.get("requestId").getAsString();
                        if (requestMethodMap.containsKey(requestId)) {
                            String methodUrl = requestMethodMap.get(requestId);
                            String[] parts = methodUrl.split(" ", 2);
                            String reqMethod = parts[0];
                            String url = parts[1];

                            JsonObject response = params.getAsJsonObject("response");
                            int status = response.get("status").getAsInt();
                            String mimeType = response.get("mimeType").getAsString();

                            captured.add(new CapturedRequest(reqMethod, url, status, mimeType));
                            // Não removemos do mapa imediatamente para tratar possíveis redirects
                        }
                    }
                } catch (Exception e) {
                    // Ignora erros de parse para mensagens que não interessam
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            driver.quit();
        }

        // Salva os dados capturados em arquivo JSON
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(outputJsonPath)) {
            gson.toJson(captured, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write captured requests to " + outputJsonPath, e);
        }
    }
}
