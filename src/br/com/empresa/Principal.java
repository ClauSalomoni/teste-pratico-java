package br.com.empresa;

import br.com.empresa.model.Funcionario;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
        //• informação de data exibida no formato dd/mm/aaaa;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //• informação de valor numérico exibida no formatado com separador de milhar como ponto e decimal como vírgula.
    private static final NumberFormat NF = NumberFormat.getInstance(Locale.of("pt", "BR"));
    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1212.00");

    public static void main(String[] args) {
        NF.setMinimumFractionDigits(2);
        
        System.out.println("[INFO] Inicializando sistema de folha de pagamento...");

        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.58"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 9, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Removendo João
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // 3.3 – Lista Geral sem João!
        imprimirTabelaFull("3.3 - LISTA DE FUNCIONÁRIOS", funcionarios);

        // 3.4 – Aumento de 10%
        funcionarios.forEach(f -> f.setSalario(f.getSalario().multiply(new BigDecimal("1.10"))));
        imprimirTabelaFull("3.4 - LISTA DE FUNCIONÁRIOS APÓS AUMENTO 10%", funcionarios);

        // 3.5 & 3.6 – Agrupando por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
            .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\n>>> 3.5 & 3.6 - FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO");
        funcionariosPorFuncao.forEach((funcao, lista) -> 
            imprimirTabelaFull("FUNÇÃO: " + funcao.toUpperCase(), lista)
        );

        // 3.8 – Aniversariantes (mes 10 e 12)
        imprimirTabelaFull("3.8 - ANIVERSARIANTES (OUT/DEZ)", funcionarios.stream()
            .filter(f -> List.of(10, 12).contains(f.getDataNascimento().getMonthValue())).toList());

        // 3.9 – Funcionario com a maior idade
        Funcionario mv = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = Period.between(mv.getDataNascimento(), LocalDate.now()).getYears();
        renderTable("3.9 - FUNCIONÁRIO COM A MAIOR IDADE", 20, new String[]{"Nome", "Idade"}, 
            new String[]{mv.getNome(), String.valueOf(idade)});

        // 3.10 – Listando funcionarios em ordem alfabética
        imprimirTabelaFull("3.10 - FUNCIONÁRIOS POR ORDEM ALFABÉTICA", funcionarios.stream()
            .sorted(Comparator.comparing(Funcionario::getNome)).toList());

        // 3.11 – Soma total dos salários
        BigDecimal total = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        renderTable("3.11 - TOTAL DA FOLHA", 30, new String[]{"Soma Total dos Salários"}, 
            new String[]{"R$ " + NF.format(total)});

        // 3.12 – Quantidade de salarios minimos por funcionario
        List<String[]> rowsMin = funcionarios.stream().map(f -> new String[]{
            f.getNome(), NF.format(f.getSalario().divide(SALARIO_MINIMO, 2, RoundingMode.HALF_UP))
        }).toList();
        renderTableRows("3.12 - QTD SALÁRIOS MÍNIMOS", 22, new String[]{"Nome", "Qtd Salários Minimos"}, rowsMin);

        // Criando um arquivo .csv para vc!
        exportarParaCSV(funcionarios);
        
        System.out.println("\n[SUCCESS] Processamento finalizado com sucesso.");
    }

    private static void exportarParaCSV(List<Funcionario> lista) {
        String nomeArquivo = "funcionarios_exportados.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo, StandardCharsets.UTF_8))) {
                writer.write('\ufeff');
                // Usei ponto e vírgula (;) para o Excel brasileiro abrir direto em colunas
                writer.println("Nome;Data Nascimento;Salário;Função"); 

                for (Funcionario f : lista) {
                // Formatei o salário com a vírgula para o Sheets/Excel brasileiro
                String salarioFormatado = NF.format(f.getSalario());
                
                writer.printf("%s;%s;%s;%s%n", 
                        f.getNome(), 
                        f.getDataNascimento().format(DTF), // Usando dd/MM/yyyy para ser legível na planilha
                        salarioFormatado, 
                        f.getFuncao());
                }
                System.out.println("\n[INFO] Relatório exportado com formatação regional: " + nomeArquivo);
        } catch (IOException e) {
                System.err.println("[ERRO] Falha ao exportar CSV: " + e.getMessage());
        }
        }

        //Formatação de Renderizações:
    private static void imprimirTabelaFull(String titulo, List<Funcionario> lista) {
        List<String[]> rows = lista.stream().map(f -> new String[]{
            f.getNome(), f.getDataNascimento().format(DTF), NF.format(f.getSalario()), f.getFuncao()
        }).toList();
        renderTableRows(titulo, 16, new String[]{"Nome", "Data Nascimento", "Salário", "Função"}, rows);
    }

    private static void renderTable(String titulo, int w, String[] headers, String[]... data) {
        renderTableRows(titulo, w, headers, Arrays.asList(data));
    }

    private static void renderTableRows(String titulo, int w, String[] headers, List<String[]> rows) {
        String sep = "+" + ("-".repeat(w + 2) + "+").repeat(headers.length);
       String fmt = "|" + (" %-" + w + "s |").repeat(headers.length) + "\n";

        System.out.println("\n>>> " + titulo);
        System.out.println(sep);
        System.out.printf(fmt, (Object[]) headers);
        System.out.println(sep);
        rows.forEach(row -> System.out.printf(fmt, (Object[]) row));
        System.out.println(sep);
    }
}