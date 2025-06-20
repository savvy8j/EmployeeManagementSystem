package org.example.core;

import org.example.db.Employee;
import org.example.db.EmployeeDAO;
import org.example.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//adding Mockito functionality
@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDAO employeeDAO;

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        this.employeeService = new EmployeeService(employeeDAO);
    }

    @Test
    public void getAllEmployees() {
        when(employeeDAO.findAll()).thenReturn(List.of(Employee.builder().name("abc").build(),Employee.builder().name("def").build()));
        List<Employee> allEmployees = employeeService.findAll();
        System.out.println(allEmployees);
        assertEquals(2, allEmployees.size());
        assertEquals("abc", allEmployees.get(0).getName());
        assertEquals("def", allEmployees.get(1).getName());

        verify(employeeDAO).findAll();
    }

    @Test
    public void getEmployeeById() {
        when(employeeDAO.findById(eq(1L))).thenReturn(Optional.of(Employee.builder().id(1L).name("abc").build()));
        Employee employee = employeeService.findById(2L);
        Assertions.assertEquals("abc", employee.getName());
        verify(employeeDAO).findById(eq(1L));
    }

    @Test
    public void createEmployee() {
        when(employeeDAO.saveOrUpdate(any())).thenReturn(Employee.builder().id(1L).name("abc").build());
        Employee emp = employeeService.save(Employee.builder().id(1L).name("abc").build());

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeDAO).saveOrUpdate(employeeCaptor.capture());
        Employee employee = employeeCaptor.getValue();
        assertEquals("ABC", employee.getName());

    }


    @Test
    public void deleteEmployee() {
        when(employeeDAO.findById(anyLong())).thenReturn(Optional.of(Employee.builder().id(1L).name("abc").build()));
        doNothing().when(employeeDAO).delete(anyLong());
        employeeService.delete(1L);
        verify(employeeDAO).findById(anyLong());
        verify(employeeDAO).delete(anyLong());
    }

    @Test
    public void deleteEmployeeWhenEmployeeNotFound() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> employeeService.delete(1L));
        verify(employeeDAO).findById(anyLong());
        verify(employeeDAO,never()).delete(anyLong());


    }


}