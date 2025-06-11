package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import org.example.core.EmployeeService;
import org.example.core.TestService;
import org.example.db.Employee;
import org.example.db.EmployeeDAO;
import org.example.resources.EmployeeResource;
import org.example.resources.TestResource;

public class EmployeeManagementApplication extends Application<EmployeeManagementConfiguration> {

    //entry point of app
    public static void main(final String[] args) throws Exception {

        new EmployeeManagementApplication().run(args);
    }

    private final HibernateBundle<EmployeeManagementConfiguration> hibernate = new HibernateBundle<EmployeeManagementConfiguration>(Employee.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(EmployeeManagementConfiguration configuration) {
            return configuration.getDatabase();
        }
    };
    @Override
    public String getName() {
        return "true";
    }

    @Override
    public void initialize(final Bootstrap<EmployeeManagementConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    //what to provide during run(dependency injection)
    public void run(final EmployeeManagementConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new TestResource(new TestService()));

        environment.jersey().register(new EmployeeResource(new EmployeeService(new EmployeeDAO(hibernate.getSessionFactory()))));




        System.out.println(configuration.getGreetingMessage());
        // TODO: implement application
    }

}
