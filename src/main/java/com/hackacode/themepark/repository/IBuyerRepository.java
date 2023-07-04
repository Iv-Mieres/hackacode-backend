package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuyerRepository extends JpaRepository<Buyer, Long> {
    boolean existsByDni(String dni);
}