package com.loyaltyProgramPOC.loyaltyProgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ExternalEmployeeClient {

    private final RestClient restClient;

    public ExternalEmployeeClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8089") // wiremock base URL
                .build();
    }

    public String getEmployeeDetails() {
        return restClient.get()
                .uri("/api/employees/1")
                .retrieve()
                .body(String.class);
    }
}
