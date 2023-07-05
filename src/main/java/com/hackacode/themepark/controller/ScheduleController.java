package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.ScheduleDTOReq;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.service.IScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Horarios de Juegos")
@RestController
@RequestMapping("api/horarios")
public class ScheduleController {

    @Autowired
    private IScheduleService scheduleService;

    @Operation(
            summary = "Guarda un horario",
            description = "Guarda el horario y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveSchedule(@Valid @RequestBody ScheduleDTOReq scheduleDTO) throws Exception {
        scheduleService.saveSchedule(scheduleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un horario",
            description = "Busca un horario por id y devuelve un Codigo de estado 200 y los datos del horario"
    )
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDTORes> getSchedule(@PathVariable Long scheduleId) throws Exception {
       return ResponseEntity.ok(scheduleService.getScheduleById(scheduleId));
    }

    @Operation(
            summary = "Traer todos los horarios",
            description = "Trae todos los horarios de base de datos y devuelve un Codigo de estado 200 y el listado de horarios"
    )
    @GetMapping()
    public ResponseEntity<Page<ScheduleDTORes>> getAllSchedules(Pageable pageable){
        return ResponseEntity.ok(scheduleService.getSchedules(pageable));
    }

    @Operation(
            summary = "Actualiza un horario",
            description = "Busca un horario por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateSchedule(@Valid @RequestBody ScheduleDTORes scheduleDTO) throws Exception {
        scheduleService.updateSchedule(scheduleDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un horario",
            description = "Elimina de forma logica un horario por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<HttpStatus> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.deleteSchedule(scheduleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
