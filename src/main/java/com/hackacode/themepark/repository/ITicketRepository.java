package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByDescription(String description);
    Page<Ticket> findAllByIsDelete(Pageable pageable, boolean delete);
}