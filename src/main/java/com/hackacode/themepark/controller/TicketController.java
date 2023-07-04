package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ITicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final ITicketService ticketService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveTicket(@Valid @RequestBody TicketDTOReq ticket) throws DescriptionExistsException {
        ticketService.saveTicket(ticket);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<TicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketService.getTickets(pageable));
    }

    @GetMapping("/{normalTicketId}")
    public ResponseEntity<TicketDTORes> getNormalTicket(@PathVariable Long ticketId) throws IdNotFoundException {
        return ResponseEntity.ok(ticketService.getTicketById(ticketId));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateNormalTicket(@Valid @RequestBody TicketDTOReq ticketDTOReq)
            throws IdNotFoundException, DescriptionExistsException {
        ticketService.updateTicket(ticketDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{normalTicketId}")
    public ResponseEntity<HttpStatus> deleteVipTicket(@PathVariable Long ticketId) {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
