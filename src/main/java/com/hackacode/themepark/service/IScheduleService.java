package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.exception.HoursExistsException;
import com.hackacode.themepark.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IScheduleService {

    void saveSchedule(ScheduleDTOReq schedule) throws HoursExistsException;
    ScheduleDTORes getScheduleById(Long scheduleId) throws Exception;
    Page<ScheduleDTORes> getSchedules(Pageable pageable);
    void updateSchedule(ScheduleDTORes scheduleId) throws Exception;
    void deleteSchedule(Long scheduleId);
}
