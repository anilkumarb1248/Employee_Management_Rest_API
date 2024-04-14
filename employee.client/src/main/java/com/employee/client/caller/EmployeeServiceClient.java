package com.employee.client.caller;

import com.employee.client.config.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class EmployeeServiceClient {

    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceClient.class);

    private final RestTemplate restTemplate;

    //private static final String EMPLOYEE_MANAGEMENT_HOST_URL = "http://localhost:2025/EmployeeManagement";
    private final String employeeManagementHostURL;

    @Autowired
    public EmployeeServiceClient(@Qualifier(ServiceConfig.REST_TEMPLATE_BEAN) RestTemplate restTemplate,
                                 @Value(value = "${employee-service.host}") String employeeManagementHostURL){
        this.restTemplate = restTemplate;
        this.employeeManagementHostURL = employeeManagementHostURL;
    }

    private HttpHeaders buildHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }


    // ParameterizedTypeReference response type methods --> Start
    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod,
                                                               ParameterizedTypeReference<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(buildHeaders()), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod,
                                                               HttpHeaders httpHeaders, ParameterizedTypeReference<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(httpHeaders), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod, Object body,
                                                               ParameterizedTypeReference<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(body, buildHeaders()), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod, Object body,
                                                               HttpHeaders httpHeaders, ParameterizedTypeReference<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(body, httpHeaders), responseType);
    }

    // ParameterizedTypeReference response type methods --> End

    // Class response type methods --> Start
    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod,
                                                               Class<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(buildHeaders()), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod,
                                                               HttpHeaders httpHeaders, Class<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(httpHeaders), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod, Object body,
                                                               Class<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(body, buildHeaders()), responseType);
    }

    public <T> ResponseEntity<T> callEmployeeManagementService(String resourcePath, HttpMethod httpMethod, Object body,
                                                               HttpHeaders httpHeaders, Class<T> responseType){
        LOGGER.info("Calling Employee Management service, resource: {}", resourcePath);
        return restTemplate.exchange(employeeManagementHostURL + resourcePath, httpMethod, new HttpEntity<>(body, httpHeaders), responseType);
    }

    // Class response type methods --> End
}
