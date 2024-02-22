package com.erudio.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Employee {

    private String nome;
    private LocalDate dataAdmissao;
    private BigDecimal salario;

    public Employee(String nome, LocalDate dataAdmissao, BigDecimal salario) {
        this.nome = nome;
        this.dataAdmissao = dataAdmissao;
        this.salario = salario;
    }

    public void reajustarSalario(BigDecimal taxa) {
        this.salario = salario.add(salario
                .multiply(taxa)
                .setScale(1, RoundingMode.HALF_UP)
        );
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

}
