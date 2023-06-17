package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    Page<Employee> findAllByIsEnable(boolean isEnable, Pageable pageable);
    boolean existsByGame_id(Long gameId);
    boolean existsByDni(String dni);
}