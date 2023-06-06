package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService implements IScheduleService {


    private IScheduleRepository scheduleRepository;

    @Override
    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public Schedule getScheduleById(Long scheduleId) throws Exception {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new Exception("El id ingresado no se encuentra en la base de datos"));
    }

    @Override
    public List<Schedule> getSchedules() {
        return null;
    }

    @Override
    public void updateSchedule(Long ScheduleId) {

    }

    @Override
    public void deleteSchedule(Long ScheduleId) {

    }
}
