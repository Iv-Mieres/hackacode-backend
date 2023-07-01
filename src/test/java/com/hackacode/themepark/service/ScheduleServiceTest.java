package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IScheduleRepository;
import org.aspectj.asm.IModelFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ScheduleServiceTest {

    @InjectMocks
    private ScheduleService scheduleService;

    @Mock
    private IScheduleRepository scheduleRepository;

    @Mock
    private ModelMapper modelMapper;

    private Schedule schedule;

    private ScheduleDTOReq scheduleDTOReq;

    private ScheduleDTORes scheduleDTORes;

    @BeforeEach
    void setUp() {
        this.schedule = new Schedule();
        this.scheduleDTOReq = new ScheduleDTOReq();
        this.scheduleDTORes = new ScheduleDTORes();
    }

    @DisplayName("comprueba que se guarden horarios")
    @Test
    void saveSchedule() throws Exception {

        when(modelMapper.map(this.scheduleDTOReq, Schedule.class)).thenReturn(this.schedule);
        scheduleService.saveSchedule(this.scheduleDTOReq);
        verify(scheduleRepository).save(this.schedule);
    }

    @DisplayName("comprueba que se lance una excepción si los horarios ya existen al hacer un guardado")
    @Test
    void ifStartTimeAndEndTimeExistsThenThrowAnException() throws Exception {

        String espectedMjError = "Los horarios ingresados ya existen. Ingrese nuevos horarios";
        when(scheduleRepository.existsByStartTime(this.scheduleDTOReq.getStartTime())).thenReturn(true);
        when(scheduleRepository.existsByEndTime(this.scheduleDTOReq.getEndTime())).thenReturn(true);

        Exception currentMjError = assertThrows(Exception.class,
                () ->  scheduleService.saveSchedule(this.scheduleDTOReq));
        assertEquals(espectedMjError, currentMjError.getMessage());
    }

    @DisplayName("comprueba que se muestre un horario por id")
    @Test
    void getScheduleById() throws Exception {

        this.scheduleDTORes = new ScheduleDTORes();
        scheduleDTORes.setStartTime(LocalTime.of(2,30));
        scheduleDTORes.setEndTime(LocalTime.of(23, 20));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(this.schedule));
        when(modelMapper.map(this.schedule, ScheduleDTORes.class)).thenReturn(this.scheduleDTORes);
        var result =  scheduleService.getScheduleById(1L);
        verify(scheduleRepository).findById(1L);

        assertEquals(this.scheduleDTORes, result);
    }

    @DisplayName("comprueba que se elimine un horario")
    @Test
    void deleteScheduleById(){
        doNothing().when(scheduleRepository).deleteById(1L);
        scheduleService.deleteSchedule(1L);
        verify(scheduleRepository).deleteById(1L);
    }

    @DisplayName("comprueba el método que lanza una exception si los horarios ya existen")
    @Test
    void validateIfEndAndStartTimeDoesNotExistInBD() throws Exception {

        this.scheduleDTOReq = new ScheduleDTOReq();
        scheduleDTOReq.setStartTime(LocalTime.of(1, 30));
        scheduleDTOReq.setEndTime(LocalTime.of(3,20));
        String espectedMjError = "Los horarios ingresados ya existen. Ingrese nuevos horarios";

        when(scheduleRepository.existsByStartTime(this.scheduleDTOReq.getStartTime())).thenReturn(true);
        when(scheduleRepository.existsByEndTime(this.scheduleDTOReq.getEndTime())).thenReturn(true);

        Exception cuerrentMjError = assertThrows(Exception.class,
                () -> scheduleService.validateIfEndAndStartTimeDoesNotExistInBD(
                        this.scheduleDTOReq.getStartTime(),
                        this.schedule.getStartTime(),
                        this.scheduleDTOReq.getEndTime(),
                        this.schedule.getEndTime()));

        assertEquals(espectedMjError, cuerrentMjError.getMessage());
    }

    @DisplayName("comprueba qu se actualice un horario")
    @Test
    void updateSchedule() throws Exception {

        this.scheduleDTORes.setId(1L);
        this.scheduleDTORes.setStartTime(LocalTime.of(2,30));
        this.scheduleDTORes.setEndTime(LocalTime.of(23, 20));

        this.schedule.setId(1L);
        this.schedule.setStartTime(LocalTime.of(2,30));
        this.schedule.setEndTime(LocalTime.of(23, 20));

        when(scheduleRepository.findById(1L)).thenReturn(Optional.ofNullable(this.schedule));
        when(scheduleService.getScheduleById(1L)).thenReturn(this.scheduleDTORes);
        when(modelMapper.map(this.scheduleDTORes, Schedule.class)).thenReturn(this.schedule);
        scheduleService.updateSchedule(this.scheduleDTORes);
        verify(scheduleRepository).save(this.schedule);
    }


    @DisplayName("comprueba que se muestre una lista de horarios paginada")
    @Test
    void getAllSchedulesPageable(){

        int page = 0;
        int size = 2;

        //settear datos en los objetos Schedule
        this.scheduleDTORes.setId(1L);
        this.schedule.setId(1L);
        var schedule2 = new Schedule();
        schedule2.setId(2L);
        var schedule3 = new Schedule();
        schedule3.setId(3L);

        //guardarlos en una List
        var schedules = new ArrayList<Schedule>();
        schedules.add(this.schedule);
        schedules.add(schedule2);
        schedules.add(schedule3);

        var pageable = PageRequest.of(page, size);
        when(scheduleRepository.findAll(pageable)).thenReturn(new PageImpl<>(schedules, pageable, schedules.size()));
        when(modelMapper.map(this.schedule, ScheduleDTORes.class)).thenReturn(this.scheduleDTORes);

        var result = scheduleService.getSchedules(pageable);

        //compara el objeto devuelto
        assertEquals(this.scheduleDTORes, result.getContent().get(0));

        //verifica que el paginado devuelva la cantidad de elementos y el total de paginas correctas
        assertEquals(schedules.size(), result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(2, result.getTotalPages());

    }
}