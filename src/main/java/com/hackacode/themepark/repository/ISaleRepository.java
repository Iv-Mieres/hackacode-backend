package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISaleRepository extends JpaRepository<Sale, Long> {
}