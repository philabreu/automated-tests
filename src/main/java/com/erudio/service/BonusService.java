package com.erudio.service;

import java.math.BigDecimal;

import com.erudio.model.Employee;

public class BonusService {

    public BigDecimal calcularBonus(Employee employee) {
        BigDecimal valor = employee.getSalario()
                .multiply(new BigDecimal("0.1"));

        if (valor.compareTo(new BigDecimal("1000")) > 0) {
           throw new IllegalArgumentException("employee com este salário não pode receber bonus.");
        }

        return valor;
    }
}