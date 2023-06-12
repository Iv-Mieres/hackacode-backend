package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService implements IScheduleService {

    @Autowired
    private IScheduleRepository scheduleRepository;

    @Autowired
    private ModelMapper modelMapper;

    //CREA UN HORARIO
    @Override
    public void saveSchedule(ScheduleDTOReq scheduleDTO) throws Exception {
        if(scheduleRepository.existsByStartTime(scheduleDTO.getStartTime())
         && scheduleRepository.existsByEndTime(scheduleDTO.getEndTime())){
            throw new Exception("Los horarios ingresados ya existen. Ingrese nuevos horarios");
        }
        scheduleRepository.save(modelMapper.map(scheduleDTO, Schedule.class));
    }

    //MUESTRA UN HORARIO POR ID
    @Override
    public ScheduleDTORes getScheduleById(Long scheduleId) throws Exception {
        var scheduleBD = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new Exception("El id ingresado no se encuentra en la base de datos"));
        return modelMapper.map(scheduleBD, ScheduleDTORes.class);
    }

    //LISTA DTO DE HORARIOS PAGINADOS
    @Override
    public Page<ScheduleDTORes> getSchedules(Pageable pageable) {
        var schedulesBD = scheduleRepository.findAll(pageable);
        var schedulesDTO = new ArrayList<ScheduleDTORes>();
        //recorre la lista schedulesBD, convierte los objetos de BD a DTO y lo guarda en schedulesDTO
        for (Schedule schedule: schedulesBD) {
            schedulesDTO.add(modelMapper.map(schedule, ScheduleDTORes.class));
        }
        return new PageImpl<>(schedulesDTO, pageable, schedulesDTO.size());
    }

    //MODIFICA UN HORARIO
    @Override
    public void updateSchedule(ScheduleDTORes scheduleDTO) throws Exception {
        var scheduleBD = this.getScheduleById(scheduleDTO.getId());
        //valida que ambos horarios ingresados no existan en la BD
        // y si existen que pertenezcan al horario encontrado
        this.validateIfEndAndStartTimeDoesNotExistInBD(scheduleDTO.getStartTime(), scheduleBD.getStartTime(),
                                                       scheduleDTO.getEndTime(), scheduleBD.getEndTime());
        scheduleRepository.save(modelMapper.map(scheduleBD, Schedule.class));
    }

    //ELIMINA UN HORARIO
    @Override
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    //VALIDA DATOS AL HACER UNA MODIFICACIÓN
    public void validateIfEndAndStartTimeDoesNotExistInBD(LocalTime startTimeDTO, LocalTime startTimeBD,
                                                          LocalTime endTimeDTO, LocalTime endTimeBD) throws Exception {
        if((!startTimeDTO.equals(startTimeBD) && scheduleRepository.existsByStartTime(startTimeDTO))
                && (!endTimeDTO.equals(endTimeBD) && scheduleRepository.existsByEndTime(startTimeDTO))){
            throw new Exception("Los horarios ingresados ya existen. Ingrese nuevos horarios");
        }
    }

}
