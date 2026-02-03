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
        if (args.length == 0) {
            // a falta de argumento impede o inicio.
            return;
        }

        try {
            String html = fetchHtml(args[0]);
            analyze(html);
        } catch (Exception e) {
            System.out.println("URL connection error");
        }
    }

    // Classe p/ extrair HTML da URL
    private static String fetchHtml(String url) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

        if (response.statusCode() != 200) {
            throw new RuntimeException();
        }

        return response.body();
    }

    private static void analyze(String html) {
        // Utilizando scanner para ler linha a linha
        Scanner scanner = new Scanner(html);
        // Estrutura de dados pronta do Java: Stack (Pilha)
        Stack<String> stack = new Stack<>();

        String deepestContent = null;
        int maxDepth = -1;
        boolean malformed = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                continue;
            }

            if (line.startsWith("</")) {
                // Tag de fechamento
                String tagName = line.substring(2, line.length() - 1); // Pega a substring dentro da Tag
                if (stack.isEmpty() || !stack.pop().equals(tagName)) {
                    malformed = true; // Se as tags forem diferentes o html esta mal formado
                    break;
                }
            } else if (line.startsWith("<")) {
                // Tag de abertura
                String tagName = line.substring(1, line.length() - 1);
                stack.push(tagName);
            } else {
                // Trecho de texto
                int currentDepth = stack.size();
                // Lógica para retornar o "primeiro maior"
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                    deepestContent = line;
                }
            }
        }

        scanner.close();

        if (malformed || !stack.isEmpty()) {
            System.out.println("malformed HTML");
        } else if (deepestContent != null) {
            System.out.println(deepestContent);
        }
    }
}