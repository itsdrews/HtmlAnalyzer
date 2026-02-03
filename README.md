EASTER_EGG_URLS

# HTML Analyzer - Axur Internship Challenge

Este projeto é uma ferramenta de linha de comando desenvolvida em **Java 17** projetada para analisar a estrutura de documentos HTML a partir de uma URL e extrair o texto contido no nível mais profundo da estrutura.

## 🚀 Solução Técnica

A solução foi implementada utilizando estritamente as classes nativas do JDK, sem o uso de bibliotecas de terceiros ou pacotes de manipulação de XML/DOM, conforme as restrições técnicas do desafio.

### 1. Detecção de Formação (Malformed HTML)

A validação de integridade do documento utiliza uma **Pilha (Stack)** de Strings para garantir a correta abertura e fechamento das tags:

- **Empilhamento:** Ao identificar uma tag de abertura (ex: `<div>`), o nome da tag é adicionado ao topo da pilha.
- **Desempilhamento e Comparação:** Ao encontrar uma tag de fechamento (ex: `</div>`), o programa remove o elemento do topo da pilha e verifica se ele corresponde à tag atual.
- **Critérios de Erro:** O HTML é considerado `malformed HTML` se:
  1. Uma tag de fechamento for encontrada com a pilha vazia.
  2. A tag de fechamento não corresponder ao topo da pilha.
  3. Ao final do processamento, a pilha não estiver vazia (tags abertas sem fechamento).

### 2. Algoritmo de Busca em Profundidade (DFS)

Embora o processamento do arquivo ocorra de forma linear (linha a linha), a lógica implementada equivale a uma **Busca em Profundidade (DFS)**:

- A profundidade de cada linha é definida pelo estado atual da pilha (`stack.size()`).
- **Lógica de Seleção:** O algoritmo mantém o controle da profundidade máxima encontrada (`maxDepth`).
- **Regra de Desempate:** Foi utilizada a condição `currentDepth > maxDepth`. Isso garante que apenas o primeiro trecho encontrado na profundidade máxima seja retornado, ignorando ocorrências subsequentes no mesmo nível.

### 3. Testes e Validação

Para garantir a robustez da solução, foi desenvolvido um script de **fuzzing** em Python que automatizou o teste de múltiplos cenários:

- **Automação:** Teste sequencial dos exemplos de 1 a 35 fornecidos pelo servidor de testes da Axur.
- **Casos de Borda:** Validação de URLs inexistentes (retornando `URL connection error`) e estruturas propositalmente malformadas.
- **Encoding:** A solução utiliza `StandardCharsets.UTF_8` tanto na captura do HTTP quanto no processamento da String, garantindo que caracteres especiais e acentuações sejam preservados corretamente.

## 🛠️ Instruções de Uso

### Compilação

No diretório raiz onde se encontra o arquivo `.java`:

```bash
javac HtmlAnalyzer.java
```

Fornecer URL como argumento após a compilação:

```bash
java HtmlAnalyzer [URL]
```
