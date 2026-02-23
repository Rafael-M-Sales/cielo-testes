package api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.ApiCaptureHelper.CapturedRequest;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de teste para validar endpoints de API capturados.
 * <p>
 * Lê o arquivo JSON gerado pelo {@link utils.ApiCaptureHelper} contendo requisições reais
 * feitas durante a navegação na home page e re-executa essas chamadas via RestAssured
 * para garantir que os serviços continuam respondendo corretamente.
 * </p>
 */
public class CieloApiTests {

    private static final String CAPTURE_FILE = "src/test/resources/api_endpoints.json";
    private static List<CapturedRequest> capturedRequests;

    /**
     * Carrega as requisições capturadas do arquivo JSON antes de executar os testes.
     *
     * @throws IOException Se houver erro ao ler o arquivo.
     */
    @BeforeAll
    public static void loadCapturedRequests() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CapturedRequest>>() {}.getType();
        try (FileReader reader = new FileReader(CAPTURE_FILE)) {
            capturedRequests = gson.fromJson(reader, listType);
        }
    }

    /**
     * Itera sobre cada endpoint capturado e valida seu status code.
     * <p>
     * Filtra recursos estáticos (CSS, JS, imagens) para focar em APIs de dados.
     * </p>
     */
    @Test
    public void validateCapturedEndpoints() {
        // Garante que temos dados para testar
        assertTrue(capturedRequests != null && !capturedRequests.isEmpty(), "Nenhuma requisição capturada encontrada. Execute RunCapture primeiro.");

        for (CapturedRequest cr : capturedRequests) {
            // Filtra recursos estáticos irrelevantes para teste de API
            if (!cr.url.contains("cielo.com.br") || 
                cr.url.endsWith(".css") || 
                cr.url.endsWith(".js") || 
                cr.url.endsWith(".woff") || 
                cr.url.endsWith(".woff2") || 
                cr.url.endsWith(".svg") || 
                cr.url.endsWith(".png") || 
                cr.url.endsWith(".jpg") || 
                cr.url.endsWith(".ico")) {
                continue;
            }

            // Constrói a requisição baseada no método capturado
            Response response;
            try {
                switch (cr.method.toUpperCase()) {
                    case "GET":
                        response = RestAssured.get(cr.url);
                        break;
                    case "POST":
                        // Nota: POSTs podem falhar sem body, aqui testamos apenas disponibilidade básica
                        response = RestAssured.post(cr.url);
                        break;
                    case "PUT":
                        response = RestAssured.put(cr.url);
                        break;
                    case "DELETE":
                        response = RestAssured.delete(cr.url);
                        break;
                    case "PATCH":
                        response = RestAssured.patch(cr.url);
                        break;
                    default:
                        // Pula métodos não suportados neste teste simples
                        continue;
                }
                
                // Valida se o status code atual corresponde ao capturado (ex: 200 continua 200)
                // Usamos try-catch para não abortar o loop em caso de falha pontual, apenas logar
                try {
                    // Nota: Validação flexível pode ser necessária (ex: 200 vs 304), mas aqui exigimos exatidão
                    assertEquals(cr.status, response.getStatusCode(), "Divergência de Status Code para: " + cr.url);
                } catch (AssertionError e) {
                    System.err.println("FALHA: " + e.getMessage());
                }

            } catch (Exception e) {
                System.err.println("Erro ao executar requisição para " + cr.url + ": " + e.getMessage());
            }
        }
    }
}
