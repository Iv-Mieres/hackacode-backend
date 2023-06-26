package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping()
    public ResponseEntity<UUID> saveTicket(@Valid @RequestBody TicketDTOReq ticket) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.saveNormalTicket(ticket));
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<TicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketService.getNormalTickets(pageable));
    }

    @GetMapping("/{normalTicketId}")
    public ResponseEntity<TicketDTORes> getNormalTicket(@PathVariable UUID normalTicketId) throws IdNotFoundException {
        return ResponseEntity.ok(ticketService.getNormalTicketById(normalTicketId));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateNormalTicket(@Valid @RequestBody TicketDTOReq normalTicketDTOReq) throws IdNotFoundException {
        ticketService.updateNormalTicket(normalTicketDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{normalTicketId}")
    public ResponseEntity<HttpStatus> deleteVipTicket(@PathVariable UUID normalTicketId) {
        ticketService.deleteNormalTicket(normalTicketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
