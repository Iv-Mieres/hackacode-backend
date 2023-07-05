package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRole(String role);
}