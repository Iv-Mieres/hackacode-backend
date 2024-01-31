package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ISaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findAllByPurchaseDateBetween(LocalDateTime normalStartDay, LocalDateTime normalEndDay);
}