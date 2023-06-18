package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICustomUserRepository extends JpaRepository<CustomUser, Long> {
    boolean existsByUsername(String username);
    Optional<CustomUser> findByUsername(String username);

    Optional<CustomUser> findByEmployee_Id(Long id);

    boolean existsByEmployee_Id(Long id);
}
