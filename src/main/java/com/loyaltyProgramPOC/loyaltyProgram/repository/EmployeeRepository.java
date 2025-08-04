package com.loyaltyProgramPOC.loyaltyProgram.repository;

import com.loyaltyProgramPOC.loyaltyProgram.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

