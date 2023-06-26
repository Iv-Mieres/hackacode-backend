package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ITicketRepository extends JpaRepository<Ticket, UUID> {

    Long countByPurchaseDateBetween(LocalDateTime startDay, LocalDateTime endDay);
    Long countByPurchaseDateBetweenAndGame_name(LocalDateTime startDay, LocalDateTime endDay, String nameGame);
    Ticket findTopByPurchaseDateBetweenOrderByBuyer_idDesc(LocalDateTime startDay, LocalDateTime endDay);
    Ticket findByPurchaseDate(LocalDateTime date);
    Ticket findTopByBuyer_idOrderByBuyer_idDesc(Long buyerId);

}