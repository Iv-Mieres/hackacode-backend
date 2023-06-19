package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.service.IScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/horarios")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveSchedule(@Valid @RequestBody ScheduleDTOReq scheduleDTO) throws Exception {
        scheduleService.saveSchedule(scheduleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTORes> getSchedule(@PathVariable Long scheduleId) throws Exception {
       return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }

    @GetMapping()
    public ResponseEntity<Page<ScheduleDTORes>> getAllSchedules(Pageable pageable){
        return ResponseEntity.ok(scheduleService.getSchedules(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateSchedule(@Valid @RequestBody ScheduleDTORes scheduleDTO) throws Exception {
        scheduleService.updateSchedule(scheduleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long scheduleId) throws Exception {
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
