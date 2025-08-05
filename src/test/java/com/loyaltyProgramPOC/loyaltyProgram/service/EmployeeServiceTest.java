package com.loyaltyProgramPOC.loyaltyProgram.service;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import com.loyaltyProgramPOC.loyaltyProgram.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  // to enable mockito in junit 5
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("Shuja", "shuja@example.com", "Engineering");
    }
    @Test
    void testCreateEmployee() {

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee); // mockito work, if someone calls repo.save, we'll return the emp obj we created above

        Employee saved = employeeService.createEmployee(employee);  // calling the method via mocked repo
        assertNotNull(saved);  // checking if saved exists
        assertEquals("Shuja", saved.getName());  // verifying if the name in the saved emp is shuja
        verify(employeeRepository, times(1)).save(any(Employee.class));  // to check if repo was hit only once
    }

    @Test
    void testGetEmployeeById_Found() {
        employee.setId(1L);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee)); //mocktio

        Employee found = employeeService.getEmployeeById(1L);
        assertEquals("Shuja", found.getName()); // checking
//        verify(employeeRepository).findById(1L); // optional to check if it was actually called inside the mocked repo
    }

}
