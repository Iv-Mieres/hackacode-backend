package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.TicketVip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITicketVipRepository extends JpaRepository<TicketVip, UUID> {
}