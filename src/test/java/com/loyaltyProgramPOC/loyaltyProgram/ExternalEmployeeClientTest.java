package com.loyaltyProgramPOC.loyaltyProgram;

import com.loyaltyProgramPOC.loyaltyProgram.service.ExternalEmployeeClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@SpringBootTest(properties = {
        "external.hr.api.base-url=http://localhost:8089"  // WireMock will run on this port
})
@WireMockTest(httpPort = 8089)
class ExternalEmployeeClientTest {

    @Autowired
    private ExternalEmployeeClient client;

    @Test
    void shouldReturnMockedEmployee() {
        stubFor(get(urlEqualTo("/api/employees/1"))
                .willReturn(okJson("{ \"name\": \"MockUser\" }")));

        String response = client.getEmployeeDetails(); // Assuming this hits /api/employees/1

        assertEquals("{ \"name\": \"MockUser\" }", response);
    }
}
