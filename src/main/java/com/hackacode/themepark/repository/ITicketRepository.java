package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByDescription(String description);

}