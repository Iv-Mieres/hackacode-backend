package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.NormalTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface INormalTicketRepository extends JpaRepository<NormalTicket, UUID> {

    Long countByPurchaseDateBetween(LocalDateTime startDay, LocalDateTime endDay);
    Long countByPurchaseDateBetweenAndGame_name(LocalDateTime startDay, LocalDateTime endDay, String nameGame);
    NormalTicket findTopByPurchaseDateBetweenOrderByBuyer_idDesc(LocalDateTime startDay, LocalDateTime endDay);

}