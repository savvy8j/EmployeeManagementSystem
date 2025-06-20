package org.example.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Object> query;

    private EmployeeDAO employeeDAO;

    @BeforeEach
    void setUp() {
        this.employeeDAO = new EmployeeDAO(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    public void testCanFindAllEmployees() {
       when(session.createQuery(anyString(),any())).thenReturn(query);
       when(query.list()).thenReturn(List.of(Employee.builder().name("abc").age(2).build()));
        List<Employee> allEmployees = employeeDAO.findAll();
        assertNotNull(allEmployees);
        assertEquals(1, allEmployees.size());
        assertEquals("abc", allEmployees.get(0).getName());
    }

    @Test
    public void testCanFindEmployeeById() {
        when(session.get(Employee.class,1L)).thenReturn(Employee.builder().name("abc").id(1L).build());
        Optional<Employee> byId = employeeDAO.findById(1L);
        assertNotNull(byId);
        assertTrue(byId.isPresent());
        assertEquals("abc", byId.get().getName());
    }

    @Test
    public void testCanCreateEmployee() {
       doNothing().when(session).saveOrUpdate(any(Employee.class));
       employeeDAO.saveOrUpdate(Employee.builder().name("abc").id(1L).build());
       assertNotNull(employeeDAO.findById(1L));
      verify(session).saveOrUpdate(any(Employee.class));
    }

    @Test
    public void testCanDeleteEmployee() {
        doNothing().when(session).remove(any(Employee.class));
        when(session.get(Employee.class,1L)).thenReturn(Employee.builder().name("abc").id(1L).build());
        employeeDAO.delete(1L);
        verify(session).get(Employee.class,1L);
        verify(session).remove(any(Employee.class));

    }





}