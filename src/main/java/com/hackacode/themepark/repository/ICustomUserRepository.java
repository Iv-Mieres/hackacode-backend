package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomUserRepository extends JpaRepository<CustomUser, Long> {
    boolean existsByUsername(String username);
    boolean existsByRoles_id(Long roleId);
    boolean existsByEmployee_id(Long employeeId);
    Optional<CustomUser> findByUsername(String username);
}
