package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITicketDetailRepository extends JpaRepository<TicketDetail, UUID> {
    TicketDetail findTopByBuyer_idOrderByBuyer_idDesc(Long buyerId);
}
