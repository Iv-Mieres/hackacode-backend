package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScheduleRepository extends JpaRepository<Schedule, Long> {
}