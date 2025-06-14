package org.example.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.User;
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
    public List<Employee> getAllEmployees(@Auth User user) {
        return employeeService.findAll();
    }

    @RolesAllowed({"USER,ADMIN"})
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
    public Employee createEmployee(@Auth Employee employee) {
        return employeeService.save(employee);
    }

    @RolesAllowed("ADMIN")
    @UnitOfWork
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee updateEmployee(@PathParam("id") Long id, Employee employee) {
        employee.setId(id);
        return employeeService.save(employee);
    }
    @RolesAllowed("ADMIN")
    @UnitOfWork
    @Path("/{id}")
    @DELETE
    public void deleteEmployee(@PathParam("id") Long id) {
        employeeService.delete(id);
    }



    @UnitOfWork
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> searchEmployees(@Auth User user, @QueryParam("name") String name,
                                          @QueryParam("age") Integer age,
                                          @QueryParam("salary") Double salary) {
        return employeeService.search(name, age, salary);
    }




}
