package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface IScheduleRepository extends JpaRepository<Schedule, Long> {
    boolean existsByEndTime(LocalTime endTime);
    boolean existsByStartTime(LocalTime starTime);
}