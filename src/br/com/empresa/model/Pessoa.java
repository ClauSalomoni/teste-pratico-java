package br.com.empresa.model;

import java.time.LocalDate;

//1– Classe Pessoa com os atributos: nome (String) e data nascimento (LocalDate).
public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {return nome;}
    public LocalDate getDataNascimento() {return dataNascimento;}
   
}