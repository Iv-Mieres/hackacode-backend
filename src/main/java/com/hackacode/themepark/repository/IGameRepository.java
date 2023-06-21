package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface IGameRepository extends JpaRepository<Game, Long> {

    boolean existsByName(String name);
    boolean existsBySchedule_id(Long scheduleId);
    List<Game> findAllByNormalTickets_purchaseDateBetween(LocalDateTime start, LocalDateTime end);


}