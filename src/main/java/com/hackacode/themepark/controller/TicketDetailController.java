package com.hackacode.themepark.controller;


import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ITicketDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Controlador de Detalles de Ticket")
@RestController
@RequestMapping("api/ticket-details")
@RequiredArgsConstructor
public class TicketDetailController {

    private final ITicketDetailService ticketDetailService;

    @Operation(
            summary = "Trae un ticket detail",
            description = "Busca un ticket por id y devuelve un Codigo de estado 200 y los datos del ticket"
    )
    @GetMapping("/{uuid}")
    public ResponseEntity<TicketDetailDTORes> getById(@PathVariable UUID uuid) throws IdNotFoundException {
        return ResponseEntity.ok().body(ticketDetailService.getById(uuid));
    }

    @Operation(
            summary = "Traer todos los tickets details",
            description = "Trae todos los tickets de base de datos y devuelve un Codigo de estado 200 y el listado de tickets"
    )
    @GetMapping
    public ResponseEntity<Page<TicketDetailDTORes>> getAll(Pageable pageable){
        return ResponseEntity.ok().body(ticketDetailService.getAllTciketsDetails(pageable));
    }

    @Operation(
            summary = "Guarda un ticket detail",
            description = "Guarda el ticket y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping
    public ResponseEntity<UUID> save(@Valid @RequestBody TicketDetailDTOReq request) throws IdNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDetailService.saveTicket(request));
    }

    @Operation(
            summary = "Elimina un ticket detail",
            description = "Elimina de forma logica un ticket por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        ticketDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
