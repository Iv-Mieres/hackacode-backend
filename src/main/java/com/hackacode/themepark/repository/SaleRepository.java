package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}