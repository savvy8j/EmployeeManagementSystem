package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class EmployeeDAO extends AbstractDAO<Employee> {
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Employee> findAll() {
        return query("from Employee").list();
    }

    public Employee findById(Long id) {
        return currentSession().get(Employee.class, id);
    }

    public Employee saveOrUpdate(Employee employee) {
        return persist(employee);
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        if (employee != null) {
            currentSession().remove(employee);
        }
    }
}
