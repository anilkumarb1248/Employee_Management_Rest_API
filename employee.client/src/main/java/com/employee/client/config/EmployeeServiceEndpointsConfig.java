package com.employee.client.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Configuration
@ConfigurationProperties(prefix = "employee-service.endpoints")
public class EmployeeServiceEndpointsConfig {
    private String insertDummyDataURL;
    private String addEmployeeURL;
    private String addEmployeesListURL;
    private String updateEmployeeURL;
    private String getAllURL;
    private String getWithPaginationURL;
    private String getByIdURL;
    private String getByUsernameURL;
    private String deleteByIdURL;
    private String deleteByUsernameURL;
    private String deleteAllURL;
}
