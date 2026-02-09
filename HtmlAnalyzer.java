import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Scanner;
import java.util.Stack;

public class HtmlAnalyzer {

    public static void main(String[] args) {
        // Se nenhum argumento for passado, encerra silenciosamente
        if (args.length == 0) {
            return;
        }

        try {
            String html = fetchHtml(args[0]);
            analyze(html);
        } catch (Exception e) {
            // Requisito 5c: Em caso de falha de conexão
            System.out.println("URL connection error");
        }
    }

    // Método para buscar o HTML usando HttpClient
    private static String fetchHtml(String url) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        // Garante que a resposta seja interpretada como UTF-8
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new RuntimeException("HTTP Error: " + response.statusCode());
        }

        return response.body();
    }

    // Método principal de análise lógica
    private static void analyze(String html) {
        Scanner scanner = new Scanner(html);
        Stack<String> stack = new Stack<>();

        String deepestContent = null;
        int maxDepth = -1;
        boolean malformed = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            // Verifica se é tag de fechamento (</tag>)
            if (line.startsWith("</")) {
                String tagName = line.substring(2, line.length() - 1);
                
                // Validação de HTML Malformado
                if (stack.isEmpty() || !stack.peek().equals(tagName)) {
                    malformed = true;
                    break;
                }
                stack.pop();
            
            // Verifica se é tag de abertura <tag>
            } else if (line.startsWith("<")) {
                String tagName = line.substring(1, line.length() - 1);
                stack.push(tagName);
            
            // Se não é tag, é texto
            } else {
                int currentDepth = stack.size();
                // Apenas atualiza se for ESTRITAMENTE maior (pega o primeiro encontrado)
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestContent = line;
                }
            }
        }
        
        scanner.close();

        // Configura saída para UTF-8 explícito para evitar problemas de acentuação no console
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        // HTML Malformado (tags desencontradas ou sobrando na pilha)
        if (malformed || !stack.isEmpty()) {
            out.println("malformed HTML");
        } 
        // Exibe o trecho mais profundo
        else if (deepestContent != null) {
            out.println(deepestContent);
        }
    }
}