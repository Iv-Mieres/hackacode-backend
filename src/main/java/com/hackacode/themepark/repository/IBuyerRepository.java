package com.hackacode.themepark.repository;

import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.model.Buyer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuyerRepository extends JpaRepository<Buyer, Long> {
    Buyer findByIsBanned(boolean isBanned);
    boolean existsByDni(String dni);
}