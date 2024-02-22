package com.erudio.service;

import com.erudio.model.Performance;
import com.erudio.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReadjustmentServiceTest {
    private ReadjustmentService service;

    private Employee employee;

    @BeforeEach
    public void init() {
        this.service = new ReadjustmentService();
        this.employee = new Employee("filipe", LocalDate.now(), new BigDecimal("100"));
    }

    @Test
    public void shouldReadjustIfPerformancePoor() {
        service.calculateAdjustment(employee, Performance.A_DESEJAR);

        assertEquals(new BigDecimal("103.0"), employee.getSalario());
    }

    @Test
    public void shouldReadjustIfPerformanceGood() {
        service.calculateAdjustment(employee, Performance.BOM);

        assertEquals(new BigDecimal("115.0"), employee.getSalario());
    }

    @Test
    public void shouldReadjustIfPerformanceExcellent() {
        service.calculateAdjustment(employee, Performance.OTIMO);

        assertEquals(new BigDecimal("120.0"), employee.getSalario());
    }
}