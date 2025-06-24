package org.example.resources;

import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.EmployeeManagementApplication;
import org.example.EmployeeManagementConfiguration;
import org.example.api.LoginDTO;
import org.example.api.LoginResponse;
import org.example.api.PostDTO;
import org.example.db.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(DropwizardExtensionsSupport.class)
class EmployeeResourceTest {
    private static DropwizardAppExtension<EmployeeManagementConfiguration> EXT = new DropwizardAppExtension<>(
            EmployeeManagementApplication.class, "config.yml");


    @Test
    public void getAllEmployees() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);


        Response response = client.target(String.format("http://localhost:%d/api/employees", EXT.getLocalPort()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .get();

        Assertions.assertEquals(200, response.getStatus());

        List<Employee> employees = response.readEntity(new GenericType<List<Employee>>() {
        });
        Assertions.assertEquals(2, employees.size());

    }

    @Test
    public void getEmployeeById() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);
        Response response = client.target(String.format("http://localhost:%d/api/employees/1", EXT.getLocalPort()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .get();
        Assertions.assertEquals(200, response.getStatus());
        Employee employee = response.readEntity(Employee.class);
        Assertions.assertEquals(1, employee.getId());


    }

    @Test
    public void getEmployeeByIdWhenIdNotFound() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);
        Response response = client.target(String.format("http://localhost:%d/api/employees/99", EXT.getLocalPort()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .get();
        Assertions.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());



    }

    @Test
    public void createEmployee() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);
        Employee employee = new Employee();
        employee.setName("EFG");
        employee.setAge(43);
        employee.setSalary(20000.00);
        Response post = client.target(String.format("http://localhost:%d/api/employees", EXT.getLocalPort()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .post(Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE));
        Assertions.assertEquals(201, post.getStatus());
        Employee employee1 = post.readEntity(Employee.class);
        Assertions.assertNotNull(employee1.getId());//if id exists
        Response response = client.target(String.format("http://localhost:%d/api/employees/%d", EXT.getLocalPort(), employee1.getId()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .get();


        Assertions.assertEquals(200, response.getStatus());
        Employee dbemployee = response.readEntity(Employee.class);
        Assertions.assertEquals(dbemployee.getId(), employee1.getId());
        Assertions.assertEquals(dbemployee.getName(), employee1.getName());

    }


    @Test
    public void updateEmployee() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);

        Employee employee = new Employee();
        employee.setName("HIJ");

        Response put = client.target(String.format("http://localhost:%d/api/employees/3", EXT.getLocalPort()))
                .request()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .put(Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(200, put.getStatus());
        Employee employeedb = put.readEntity(Employee.class);
        Assertions.assertEquals("HIJ", employeedb.getName());


    }

    @Test
    public void deleteEmployee() {
        Client client = EXT.client();
        LoginResponse loginResponse = getLoginResponse(client);
        Response deleteResponse = client.target(String.format("http://localhost:%d/api/employees/3", EXT.getLocalPort()))
                .request()
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .delete();


        Assertions.assertEquals(204, deleteResponse.getStatus());

        Response getResponse = client.target(String.format("http://localhost:%d/api/employees/3", EXT.getLocalPort()))
                .request()
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .get();
        Assertions.assertEquals(404, getResponse.getStatus());
    }
    private static LoginResponse getLoginResponse(Client client) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("admin");
        loginDTO.setPassword("admin");

        LoginResponse loginResponse = client.target(String.format("http://localhost:%d/api/login", EXT.getLocalPort()))
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity(loginDTO, MediaType.APPLICATION_JSON_TYPE), LoginResponse.class);
        return loginResponse;
    }


}