package org.example.core;

import org.example.db.Employee;
import org.example.db.EmployeeDAO;
import org.example.exception.EmployeeNotFoundException;
import org.example.exception.IllegalArgumentException;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> findAll() {
        List<Employee> all = employeeDAO.findAll();
         return employeeDAO.findAll();
    }

    public Employee findById(Long id) {
        if(id == null || id<=0){
            throw new IllegalArgumentException("Invalid ID "+id);
        }
        return employeeDAO.findById(id)
                .orElseThrow(() ->new EmployeeNotFoundException("Employee with id "+id+" not found"));
    }

    public Employee save(Employee employee) {
        System.out.println(employee.getName().toUpperCase());
        return employeeDAO.saveOrUpdate(employee);
    }

    public void delete(Long id) {
        findById(id);
        employeeDAO.delete(id);
    }

    public List<Employee> search(String name, Integer age, Double salary) {
        return employeeDAO.search(name, age, salary);
    }
}
