package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.VipTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IVipTicketRepository extends JpaRepository<VipTicket, UUID> {
    VipTicket findTopByPurchaseDateBetweenOrderByBuyer_idDesc(LocalDateTime startDay, LocalDateTime endDay);
}