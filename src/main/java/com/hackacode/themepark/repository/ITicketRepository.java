package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByDescription(String description);
}