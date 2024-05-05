package com.employee.client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    public static final String REST_TEMPLATE_BEAN = "RestTemplateBean";

    @Bean(REST_TEMPLATE_BEAN)
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
