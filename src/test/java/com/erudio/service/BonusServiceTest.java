package com.erudio.service;

import com.erudio.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BonusServiceTest {
    BonusService service;

    @BeforeEach
    public void init() {
        this.service = new BonusService();
    }

    @Test
    public void shouldDenyBonusForHighSalary() {
        assertThrows(IllegalArgumentException.class,
                () -> service.calcularBonus(new Employee("filipe", LocalDate.now(), new BigDecimal(20000))));
    }

    @Test
    public void shouldCalculateBonusNormal() {
        BigDecimal bonus = service.calcularBonus(new Employee("filipe", LocalDate.now(), new BigDecimal(2500)));

        assertEquals(new BigDecimal("250.0"), bonus);
    }
}