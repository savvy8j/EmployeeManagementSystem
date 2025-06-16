package org.example.db;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class EmployeeDAO extends AbstractDAO<Employee> {
    public EmployeeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Employee> findAll() {
        return query("from Employee").list();
    }

    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public Employee saveOrUpdate(Employee employee) {
        return persist(employee);
    }

    public void delete(Long id) {
        Optional<Employee> optionalEmployee = findById(id);
        if (optionalEmployee.isPresent()) {
            currentSession().remove(optionalEmployee);
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
