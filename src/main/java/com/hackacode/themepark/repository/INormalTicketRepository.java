package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.NormalTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface INormalTicketRepository extends JpaRepository<NormalTicket, UUID> {

}