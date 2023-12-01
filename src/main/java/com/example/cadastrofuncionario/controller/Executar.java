package com.example.cadastrofuncionario.controller;

import com.example.cadastrofuncionario.model.Funcionario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/executar")
public class Executar {

    @GetMapping("")
    public String teste(){

        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 - Inserir funcionários
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.getNome() + " - " +
                    funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " +
                    funcionario.getSalario()); // Formate conforme necessário
        }

        // 3.4 - Aumentar salários em 10%
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        });

        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.getNome() + " - " +
                    funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " +
                    funcionario.getSalario()); // Formate conforme necessário
        }

        // 3.5 - Agrupar por função
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir funcionários agrupados por função
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            for (Funcionario funcionario : lista) {
                System.out.println(funcionario.getNome() + " - " + funcionario.getSalario());
            }
        });

        // 3.8 - Imprimir funcionários que fazem aniversário no mês 10 e 12
        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12)
                .collect(Collectors.toList());

        System.out.println("Aniversariantes de outubro e dezembro:");
        for (Funcionario funcionario : aniversariantes) {
            System.out.println(funcionario.getNome() + " - " + funcionario.getDataNascimento());
        }

        // 3.9 - Imprimir funcionário com maior idade
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(funcionario -> funcionario.getDataNascimento()))
                .orElse(null);

        System.out.println("Funcionário mais velho: " + maisVelho.getNome() + " - " +
                calcularIdade(maisVelho.getDataNascimento()) + " anos");

        // 3.10 - Imprimir lista de funcionários por ordem alfabética
        funcionarios.sort(Comparator.comparing(Funcionario::getNome));

        System.out.println("Lista de funcionários por ordem alfabética:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario.getNome());
        }

        // 3.11 - Imprimir total dos salários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total dos salários: " + totalSalarios);

        return "Teste";
    }

    private static int calcularIdade(LocalDate dataNascimento) {
        LocalDate hoje = LocalDate.now();
        return hoje.getYear() - dataNascimento.getYear();
    }


}
