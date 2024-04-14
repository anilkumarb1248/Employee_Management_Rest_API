package com.employee.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    public static final String REST_TEMPLATE_BEAN = "RestTemplateBean";

    @Bean(REST_TEMPLATE_BEAN)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
