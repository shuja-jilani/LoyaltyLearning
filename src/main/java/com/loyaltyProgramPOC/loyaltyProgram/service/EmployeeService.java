package com.loyaltyProgramPOC.loyaltyProgram.service;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import com.loyaltyProgramPOC.loyaltyProgram.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisService redisService;

    @Autowired
    private JedisService jedisService;

    @Value("${external.hr.api.base-url}") //to fetch the base url for calling external apis
    private String hrApiBaseUrl;


    public Employee createEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Cacheable(value = "loyalty", key = "#id")
    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

//    public Employee getEmployeeByEmail(String email) {
//        // 1. Try to get from Redis
//        Employee cached = redisService.get(email, Employee.class);
//        if (cached != null) {
//            return cached;
//        }
//
//        // 2. If not in cache, get from DB and cache it
//        Employee employee = repository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Employee not found"));
//
//        redisService.set(email, employee,300L);
//        return employee;
//    }
//public Employee getEmployeeByEmail(String email) {
//    // 1. Try to get from Redis
//    Employee cachedEmployee = jedisService.getValue(email, Employee.class);
//    if (cachedEmployee != null) {
//        return cachedEmployee;
//    }
//
//    // 2. If not in cache, fetch from DB
//    Employee employee = repository.findByEmail(email)
//            .orElseThrow(() -> new RuntimeException("Employee not found for email: " + email));
//
//    // 3. Cache the result in Redis
//    jedisService.setValueWithExpiry(email, employee, 300); // TTL: 300 seconds
//
//
//    return employee;
//}
    @Cacheable(value = "loyalty", key = "#email")
    public Employee getEmployeeByEmail(String email) {
    return repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
}

    @CachePut(value="loyalty", key="#id") //only if you run get emp by id the cache is updated, but not for get emp by email, because key is id here
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = getEmployeeById(id);
        existing.setName(updated.getName());
        existing.setDepartment(updated.getDepartment());
        existing.setEmail(updated.getEmail());
        return repository.save(existing);
    }

    @CacheEvict(value ="loyalty", key="#id")
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public String fetchExternalEmployeeDetails(Long id) {
        String url = hrApiBaseUrl + "/hr/employees/" + id;
        return restTemplate.getForObject(url, String.class);
    }
}
//@Caching(
//    put = {
//        @CachePut(value = "loyalty", key = "#id"),
//        @CachePut(value = "loyalty", key = "#employee.email")
//    }
//) ,
// we can use this annotation to update both the caches that we are using to store and retrieve the emp details