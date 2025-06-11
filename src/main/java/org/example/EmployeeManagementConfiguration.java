package org.example;

import io.dropwizard.core.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeManagementConfiguration extends Configuration {
    // TODO: implement service configuration
    private String greetingMessage;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();



}
