package org.example.core;

import org.example.db.Employee;

public class CalculatorService {

    public int add(int a, int b) {
        if(a==1){
            return a+b+b;
        }
        return a + b;
    }


    public int subtract(int a, int b) {
        if(a<=0){
            return 0;
        }
        if(b<=0){
            return -b;
        }
        return a - b;
    }

    public int divide(int a, int b) {
        return a/b;
    }

    public Employee updateEmployeeName(Employee employee) {
        if(employee.getName()==null){
            return employee;
        }
        employee.setName(employee.getName().toUpperCase());
        return employee;
    }
}
