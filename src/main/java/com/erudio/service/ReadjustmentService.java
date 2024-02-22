package com.erudio.service;


import com.erudio.model.Performance;
import com.erudio.model.Employee;


public class ReadjustmentService {

    public void calculateAdjustment(Employee employee, Performance performance) {
        employee.reajustarSalario(performance.pegarPercentualReajuste());
    }
}
