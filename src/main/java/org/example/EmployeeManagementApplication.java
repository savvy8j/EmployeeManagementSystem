package org.example;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import jakarta.ws.rs.client.Client;
import org.example.auth.*;
import org.example.client.PostClient;
import org.example.core.EmployeeService;
import org.example.core.TestService;
import org.example.db.Employee;
import org.example.db.EmployeeDAO;
import org.example.exception.EmployeeNotFoundExceptionMapper;
import org.example.exception.GlobalExceptionMapper;
import org.example.exception.IllegalArgumentExceptionMapper;
import org.example.resources.AuthenticationResource;
import org.example.resources.EmployeeResource;
import org.example.resources.PostResource;
import org.example.resources.TestResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

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
        bootstrap.addBundle(new SwaggerBundle<EmployeeManagementConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(EmployeeManagementConfiguration configuration) {
                return configuration.getSwagger();
            }
        });


    }

    @Override
    //what to provide during run(dependency injection)
    public void run(final EmployeeManagementConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new TestResource(new TestService()));

        environment.jersey().register(new EmployeeResource(new EmployeeService(new EmployeeDAO(hibernate.getSessionFactory()))));
//
        //**** register this resource for basic auth****
//        environment.jersey().register(new AuthDynamicFeature(
//                new BasicCredentialAuthFilter.Builder<User>()
//                        .setAuthenticator(new BasicAuthenticator())
//                        .setAuthorizer(new RoleAuthorizer())
//                        .setRealm("SUPER SECRET STUFF")
//                        .buildAuthFilter()));
//        environment.jersey().register(RolesAllowedDynamicFeature.class);
//        //If you want to use @Auth to inject a custom Principal type into your resource
//        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
//    }
        //**** this resource registered for auth using JWT ****
        environment.jersey().register(new AuthDynamicFeature(new JwtAuthFilter.Builder<User>()
                .setAuthenticator(new JwtAuthenticator()).setRealm("realm-123").buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        environment.jersey().register(new AuthenticationResource());

        environment.jersey().register(new EmployeeNotFoundExceptionMapper());

        environment.jersey().register(new IllegalArgumentExceptionMapper());
        environment.jersey().register(new GlobalExceptionMapper());

        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClient())
                .build(getName());

        environment.jersey().register(new PostResource(new PostClient(client,configuration.getPostsUrl())));
//
//        System.out.println(configuration.getGreetingMessage());
        // TODO: implement application
    }
}



