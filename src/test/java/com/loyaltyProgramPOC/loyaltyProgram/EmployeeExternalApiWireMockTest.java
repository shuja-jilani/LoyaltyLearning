package com.loyaltyProgramPOC.loyaltyProgram;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.loyaltyProgramPOC.loyaltyProgram.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = {
        "external.hr.api.base-url=http://localhost:9090"  //setting the property only for the testing
})
@WireMockTest(httpPort = 9090)
public class EmployeeExternalApiWireMockTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void testFetchExternalEmployeeDetails() {
        stubFor(get(urlEqualTo("/hr/employees/101"))    //when a get req is made to this endpoint , we return a fake json
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":101,\"name\":\"WireMockUser\",\"email\":\"mock@hr.com\"}")));

        String result = employeeService.fetchExternalEmployeeDetails(101L);

        assertEquals("{\"id\":101,\"name\":\"WireMockUser\",\"email\":\"mock@hr.com\"}", result);
    }
}
