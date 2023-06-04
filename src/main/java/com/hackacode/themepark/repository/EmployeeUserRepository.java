package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.EmployeeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeUserRepository extends JpaRepository<EmployeeUser, Long> {
}