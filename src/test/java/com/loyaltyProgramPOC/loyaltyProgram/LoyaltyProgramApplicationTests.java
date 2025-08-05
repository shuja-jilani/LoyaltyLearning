package com.loyaltyProgramPOC.loyaltyProgram;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import com.loyaltyProgramPOC.loyaltyProgram.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoyaltyProgramApplicationTests {

	@Container
	@ServiceConnection // used instead of dynamic properties for setting up url username and pswd
	static PostgreSQLContainer<?> postgresContainer =
			new PostgreSQLContainer<>("postgres:17")
					.withDatabaseName("testdb")
					.withUsername("testuser")
					.withPassword("testpass");

	@Container
	static GenericContainer<?> redisContainer =
			new GenericContainer<>("redis:7.2.4") // latest stable
					.withExposedPorts(6379);
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private EmployeeService employeeService;

	@LocalServerPort
	private int port;

	private Employee employee;
	@BeforeEach
	void setUp() {
		employee = new Employee("Shuja", "shuja@example.com", "Engineering");
	}

	@DirtiesContext
	@Test
	void testCreateEmployeeViaApi() {
		// 1. Create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		// 2. Create request entity with body and headers
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);

		// 3. Make POST call to /employees
		ResponseEntity<Employee> response = testRestTemplate.postForEntity(
				"/employees", request, Employee.class);

		// 4. Assertions on response
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getName()).isEqualTo("Shuja");
	}

	@Test
	void testAddAndGetEmployee() {

		employeeService.createEmployee(employee);

		List<Employee> employees = employeeService.getAllEmployees();
		assertEquals(2, employees.size());
	}
	@Test
	void testGenericContainerIsRunning() {
		String address = redisContainer.getHost();
		Integer port = redisContainer.getMappedPort(6379);
		System.out.println("Redis running at: " + address + ":" + port);

		// Just an example assertion
		org.junit.jupiter.api.Assertions.assertTrue(redisContainer.isRunning());
	}
}

