package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.EmployeeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeUserRepository extends JpaRepository<EmployeeUser, Long> {
}