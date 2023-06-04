package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}