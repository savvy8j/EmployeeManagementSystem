package org.example.core;

import org.example.db.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {

    private CalculatorService calculatorService = new CalculatorService();

    @Test
    public void testCanAdd() {
        int result = calculatorService.add(1, 2);
        assertEquals(5, result);
    }

    @Test
    public void testCanAddIfaNot1() {
        int result = calculatorService.add(2, 2);
        assertEquals(4, result);
    }

    @Test
    public void testCanSubtract() {
        int result =calculatorService.subtract(3, 2);
        assertEquals(1, result);
    }

    @Test
    public void testCanSubtractIfaLessThanEqualTo0() {
        int result =calculatorService.subtract(0, 2);
        assertEquals(0, result);
    }

    @Test
    public void testCanSubtractIfbLessThanEqualTo0() {
        int result =calculatorService.subtract(1, -6);
        assertEquals(6, result);
    }

    @Test
    public void testCanDivide() {
        int result =calculatorService.divide(5, 0);
        assertEquals(1, result);
    }

    @Test
    public void testCanFailForDivideByZero() {

        assertThrows(ArithmeticException.class, () -> calculatorService.divide(2, 0));
    }

    @Test
    public void testForUpdateEmployeeName(){
        Employee employee = new Employee();
        employee.setName("John");
        Employee employee1 = calculatorService.updateEmployeeName(employee);
        assertNotNull(employee1);
        assertEquals("JOHN", employee1.getName());

    }

    @Test
    public void testForUpdateEmployeeNameWhenNameIsNull(){
        Employee employee = new Employee();

        Employee employee1 = calculatorService.updateEmployeeName(employee);
        assertNotNull(employee1);
        assertNull(employee1.getName());

    }





}