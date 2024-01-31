package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.TicketDetail;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITicketDetailRepository extends JpaRepository<TicketDetail, UUID> {
    List<TicketDetail> findAllByBuyer_id(Sort sort, Long buyerId);
}
