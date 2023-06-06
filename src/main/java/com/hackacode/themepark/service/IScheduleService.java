package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Schedule;

import java.util.List;

public interface IScheduleService {

    void saveSchedule(Schedule schedule);
    Schedule getScheduleById(Long scheduleId) throws Exception;
    List<Schedule> getSchedules();
    void updateSchedule(Long scheduleId);
    void deleteSchedule(Long scheduleId);
}
