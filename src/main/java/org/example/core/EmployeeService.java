package org.example.core;

import org.example.db.Employee;
import org.example.db.EmployeeDAO;

import java.util.List;

public class EmployeeService {
    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public List<Employee> findAll() {
        return employeeDAO.findAll();
    }

    public Employee findById(Long id) {
        return employeeDAO.findById(id);
    }

    public Employee save(Employee employee) {
        return employeeDAO.saveOrUpdate(employee);
    }

    public void delete(Long id) {
        employeeDAO.delete(id);
    }

    public List<Employee> search(String name, Integer age, Double salary) {
        return employeeDAO.search(name, age, salary);
    }
}
