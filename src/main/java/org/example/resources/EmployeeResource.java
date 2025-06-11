package org.example.resources;

import io.dropwizard.hibernate.UnitOfWork;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.example.core.EmployeeService;
import org.example.db.Employee;

import java.util.List;

@Slf4j
@Path("/api/employees")
public class EmployeeResource {
    private final EmployeeService employeeService;


    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @UnitOfWork
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id") Long id) {
        return employeeService.findById(id);
    }

    @UnitOfWork
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee createEmployee(Employee employee) {
        return employeeService.save(employee);
    }

    @UnitOfWork
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee updateEmployee(@PathParam("id") Long id, Employee employee) {
        employee.setId(id);
        return employeeService.save(employee);
    }
    @UnitOfWork
    @Path("/{id}")
    @DELETE
    public void deleteEmployee(@PathParam("id") Long id) {
        employeeService.delete(id);
    }






}
