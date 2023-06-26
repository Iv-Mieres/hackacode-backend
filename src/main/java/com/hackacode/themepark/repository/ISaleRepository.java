package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface ISaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findAllByPurchaseDateBetween(LocalDateTime normalStartDay, LocalDateTime normalEndDay);
    List<Sale> findByPurchaseDateBetween(LocalDateTime normalStartDay, LocalDateTime normalEndDay);

    Long countByPurchaseDateBetweenAndGame_name(LocalDateTime startDay, LocalDateTime endDay, String nameGame);

    Sale findTopByPurchaseDateBeforeOrderByGame_idDesc(LocalDateTime date);

    Long countSalesByGame_Id(Long id);

}