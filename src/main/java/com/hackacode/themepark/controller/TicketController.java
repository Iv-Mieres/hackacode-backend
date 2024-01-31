package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Ticket")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @Operation(
            summary = "Guarda un ticket",
            description = "Guarda el ticket y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveTicket(@Valid @RequestBody TicketDTOReq ticket) throws DescriptionExistsException {
        ticketService.saveTicket(ticket);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Traer todos los tickets",
            description = "Trae todos los tickets de base de datos y devuelve un Codigo de estado 200 y el listado de tickets"
    )
    @GetMapping
    public ResponseEntity<Page<TicketDTORes>> getAllTickets(Pageable pageable){
        return ResponseEntity.ok(ticketService.getTickets(pageable));
    }

    @Operation(
            summary = "Trae un ticket",
            description = "Busca un ticket por id y devuelve un Codigo de estado 200 y los datos del ticket"
    )
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketDTORes> getTicketById(@PathVariable Long ticketId) throws IdNotFoundException {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @Operation(
            summary = "Actualiza un ticket",
            description = "Busca un ticket por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateTicket(@Valid @RequestBody TicketDTOReq ticketDTOReq)
            throws IdNotFoundException, DescriptionExistsException {
        ticketService.updateTicket(ticketDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un ticket",
            description = "Elimina de forma logica un ticket por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTicket(@PathVariable Long id) throws IdNotFoundException {
        ticketService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
