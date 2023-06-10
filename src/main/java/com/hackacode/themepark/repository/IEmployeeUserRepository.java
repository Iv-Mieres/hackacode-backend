package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeUserRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUsername(String username);
}