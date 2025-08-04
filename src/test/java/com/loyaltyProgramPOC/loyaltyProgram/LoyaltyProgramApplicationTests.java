package com.loyaltyProgramPOC.loyaltyProgram;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import com.loyaltyProgramPOC.loyaltyProgram.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
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
	private EmployeeService employeeService;

	@Test
	void testAddAndGetEmployee() {
		Employee emp = new Employee();
		emp.setName("SpringTestUser");
		emp.setDepartment("Engineering");
		emp.setEmail("springuser@example.com");

		employeeService.createEmployee(emp);

		List<Employee> employees = employeeService.getAllEmployees();
		assertEquals(1, employees.size());
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

