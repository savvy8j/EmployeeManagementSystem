package org.example.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.User;
import org.example.core.EmployeeService;
import org.example.db.Employee;

import java.util.List;

@Slf4j
@Path("/api/employees")
@Tag(name ="EmployeeResource")
public class EmployeeResource {
    private final EmployeeService employeeService;


    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @UnitOfWork
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getAllEmployees(@Auth @Parameter(hidden=true) User user) {
        return employeeService.findAll();
    }


    @UnitOfWork
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@Auth  @Parameter(hidden=true) User user,@PathParam("id") Long id) {
        return employeeService.findById(id);
    }

    @UnitOfWork
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(@Auth  @Parameter(hidden=true) Employee employee) {
        return Response.status(Response.Status.CREATED).
                entity(employeeService.save(employee)).
                build();
    }


    @UnitOfWork
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Employee updateEmployee(@Auth @Parameter(hidden=true) Employee employee,@PathParam("id") Long id) {
        employee.setId(id);
        return employeeService.save(employee);
    }

    @UnitOfWork
    @Path("/{id}")
    @DELETE
    public void deleteEmployee(@Auth  @Parameter(hidden=true) Employee employee,@PathParam("id") Long id) {
        employeeService.delete(id);
    }



    @UnitOfWork
    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> searchEmployees(@Auth  @Parameter(hidden=true) Employee user, @QueryParam("name") String name,
                                          @QueryParam("age") Integer age,
                                          @QueryParam("salary") Double salary) {
        return employeeService.search(name, age, salary);
    }




}
