package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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

    public List<Employee> search(String name,Integer age,Double salary) {
        Query query = currentSession().createQuery("from Employee e where (:name is null or e.name like :name) " +
                "and (:age is null or e.age =:age)" +
                "and (:salary is null or e.salary =:salary)"
        );
        query.setParameter("name", "%"+name+"%");
        query.setParameter("age", age);
        query.setParameter("salary", salary);
        return query.list();
    }
}
