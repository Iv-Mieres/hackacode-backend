package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeUserRepository extends JpaRepository<Employee, Long> {
}