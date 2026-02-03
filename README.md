EASTER_EGG_URLS

HTML Analyzer - Axur Internship Challenge
Este projeto é uma ferramenta de linha de comando desenvolvida em Java 17 para analisar estruturas HTML a partir de uma URL. O objetivo principal é identificar e retornar o conteúdo textual localizado no nível mais profundo da árvore do documento.

🚀 Solução Técnica
A solução foi construída utilizando apenas bibliotecas nativas do JDK, respeitando a restrição de não utilizar parsers de XML/HTML externos ou nativos (como javax.xml).

1. Detecção de Formação (Malformed HTML)
   Para garantir que o HTML seja válido e garantir os pontos bônus, foi implementada uma validação sintática baseada em uma estrutura de dados de Pilha (Stack).

Tags de Abertura: Sempre que o analisador encontra uma tag de abertura, ela é empilhada.

Tags de Fechamento: Ao encontrar uma tag de fechamento, o programa verifica se a pilha está vazia ou se o topo da pilha corresponde à tag que está sendo fechada.

Consistência Final: Se ao final da leitura a pilha não estiver vazia (tags abertas sem fechamento) ou se ocorrer um erro de correspondência durante o processo, o programa interrompe a execução e retorna malformed HTML.

2. Busca em Profundidade (DFS)
   O algoritmo utiliza o conceito de Busca em Profundidade (Depth-First Search) de forma iterativa.

A "profundidade" de qualquer trecho de texto é determinada pelo tamanho atual da Stack no momento em que a linha de texto é lida.

Como o HTML é processado linearmente (linha a linha), a pilha simula a descida nos ramos da árvore.

Lógica de Seleção: O programa armazena o texto e sua profundidade. Se encontrar um novo texto com profundidade estritamente maior (currentDepth > maxDepth), ele substitui o anterior. Isso garante que, em caso de empate na profundidade máxima, o primeiro trecho encontrado seja o preservado, conforme exigido.

3. Testes de Múltiplos Exemplos
   Para validar a solução, foi utilizado um script de automação (Fuzzer) em Python para iterar sobre uma lista de parâmetros (IDs de 1 a 35).

O script monta URLs dinâmicas: http://hiring.axreng.com/internship/example{id}.html.

Os resultados de cada execução são capturados e consolidados em um arquivo .txt para conferência em massa.

Este método permitiu validar o comportamento do analisador diante de casos de sucesso, páginas malformadas e erros de conexão (como páginas inexistentes).

🛠️ Como Executar
Pré-requisitos
JDK 17 instalado e configurado no PATH.

Compilação
No diretório raiz do projeto, execute:

Bash
javac HtmlAnalyzer.java
Execução
Para analisar uma URL específica:

Bash
java HtmlAnalyzer http://hiring.axreng.com/internship/example1.html
📋 Especificações de Saída
Sucesso: Retorna apenas o texto do nível mais profundo.

HTML Inválido: Retorna malformed HTML.

Erro de Rede: Retorna URL connection error.

Este projeto foi desenvolvido como parte do processo de seleção para o programa de estágio da Axur.
