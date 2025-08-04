package com.loyaltyProgramPOC.loyaltyProgram.service;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import com.loyaltyProgramPOC.loyaltyProgram.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private RestTemplate restTemplate;


    @Value("${external.hr.api.base-url}") //to fetch the base url for calling external apis
    private String hrApiBaseUrl;


    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getEmployeeById(id);
        existing.setName(updated.getName());
        existing.setDepartment(updated.getDepartment());
        existing.setEmail(updated.getEmail());
        return repository.save(existing);
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
    public String fetchExternalEmployeeDetails(Long id) {
        String url = hrApiBaseUrl + "/hr/employees/" + id;
        return restTemplate.getForObject(url, String.class);
    }
}
