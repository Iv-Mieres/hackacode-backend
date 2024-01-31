package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.exception.HoursExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IScheduleService {

    void saveSchedule(ScheduleDTOReq schedule) throws HoursExistsException;
    ScheduleDTORes getScheduleById(Long scheduleId) throws Exception;
    Page<ScheduleDTORes> getSchedules(Pageable pageable);
    void updateSchedule(ScheduleDTORes scheduleId) throws Exception;
    void deleteSchedule(Long scheduleId);
}
