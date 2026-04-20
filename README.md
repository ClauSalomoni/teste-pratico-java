# Teste Prático: Gestão de Funcionários em Java
# Desafio Java Júnior - Gestão de Funcionários

![Demonstração do Projeto](./assets/demoProjetoJava.gif)

Este projeto foi desenvolvido como parte de um processo seletivo para vaga Júnior **Desenvolvedora de Software Júnior**. O objetivo é aplicar conceitos fundamentais de Programação Orientada a Objetos (POO) e manipulação de dados com a API de Streams do Java.

## 🛠️ Tecnologias e Ferramentas
- **Java 21+**: Uso de Records/Classes para modelos de dados.
- **Streams API**: Para filtragem, ordenação e agrupamento (Map).
- **IA (Gemini/ChatGPT)**: Utilizada como ferramenta de pair-programming para refinamento da lógica de renderização de tabelas e aplicação do princípio DRY.

## 🚀 O que este projeto exercita?
Apesar de ser um sistema de console, ele resolve problemas reais de lógica:
1. **Manipulação de Coleções**: Uso de `ArrayList` e `Map` para organizar funcionários por função.
2. **Cálculos Financeiros**: Uso de `BigDecimal` para garantir precisão em operações com moedas e salários mínimos.
3. **Clean Code**: Separação da lógica de negócio da lógica de exibição (Interface de linha de comando).

## 📦 Como executar
1. Clone o repositório.
2. Entre em src.
3. Compile os arquivos: 
`javac br/com/empresa/model/Pessoa.java`
`javac br/com/empresa/model/Funcionario.java`
`javac br/com/empresa/Principal.java`
4. Execute: `java br.com.empresa.Principal`

Ao rodar a classe `Principal`, o sistema exibirá os relatórios formatados no console e, ao final, gerará automaticamente o arquivo `funcionarios_exportados.csv`.

> **Nota sobre a exportação:** O sistema gera uma exportação em .csv configurada com delimitadores de ponto e vírgula e formatação numérica regional (PT-BR). Isso permite que o usuário final (RH/Contabilidade) abra o arquivo diretamente no Excel ou Google Sheets sem a necessidade de passar por assistentes de importação ou conversão de tipos de dados.
---
*Ótima opotunidade de aprendizado focado em fundamentos e organização de código, pela primeira vez em Java.*