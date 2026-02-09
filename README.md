EASTER_EGG_URLS

# HTML Analyzer - Axur Internship Challenge

Ferramenta de linha de comando desenvolvida em **Java 17** para analisar a profundidade de estruturas HTML e extrair conteúdo textual, conforme especificações do teste técnico.

## 🚀 Destaques da Solução

O projeto foi implementado utilizando estritamente classes nativas do JDK (`java.base`), sem bibliotecas externas ou parsers de DOM/XML, focando em performance e baixo overhead.

### 1. Validação de HTML (Bônus)
A solução implementa a funcionalidade opcional de detecção de HTML malformado utilizando uma **Pilha (Stack)**:
- Garante o balanceamento correto entre tags de abertura e fechamento.
- Identifica erros como: tags cruzadas, fechamento sem abertura prévia e tags residuais na pilha.
- Retorna `malformed HTML` priorizando a validação estrutural.

### 2. Lógica de Profundidade
O algoritmo processa o arquivo de forma linear (Stream/Scanner), mantendo o estado da profundidade atual:
- **Critério:** Se `currentDepth > maxDepth`, o conteúdo é capturado.
- **Desempate:** A utilização do operador estrito (`>`) assegura que, em caso de empate na profundidade máxima, apenas a **primeira ocorrência** seja preservada, conforme requisito funcional.

### 3. Robustez e Encoding
- **Compatibilidade:** Todo o I/O utiliza `StandardCharsets.UTF_8` para garantir a correta manipulação de acentuação e caracteres especiais.
- **Tratamento de Erros:** Captura falhas de conexão HTTP retornando a mensagem padronizada `URL connection error`.

## 🛠️ Instruções de Compilação e Execução

Pré-requisito: JDK 17 instalado.

**1. Compilar:**
```bash
javac HtmlAnalyzer.java