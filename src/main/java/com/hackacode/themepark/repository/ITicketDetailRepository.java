package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.model.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ITicketDetailRepository extends JpaRepository<TicketDetail, UUID> {


    TicketDetail findTopByPurchaseDateBetweenOrderByBuyer_IdDesc(LocalDateTime startDay, LocalDateTime endDay);
    List<TicketDetail> findAllByPurchaseDateBetweenOrderByBuyer_IdDesc(LocalDateTime startDay, LocalDateTime endDay);
    TicketDetail findTopByBuyer_idOrderByBuyer_idDesc(Long buyerId);

    Long countByPurchaseDateBetween(LocalDateTime startDay, LocalDateTime endDay);


}
