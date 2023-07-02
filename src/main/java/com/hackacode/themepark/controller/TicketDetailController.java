package com.hackacode.themepark.controller;


import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.ITicketDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/ticket-details")
@RequiredArgsConstructor
public class TicketDetailController {

    private final ITicketDetailService ticketDetailService;

    @GetMapping("/{uuid}")
    public ResponseEntity<TicketDetailDTORes> getById(@PathVariable UUID uuid) throws IdNotFoundException {
        return ResponseEntity.ok().body(ticketDetailService.getById(uuid));
    }

    @GetMapping
    public ResponseEntity<Page<TicketDetailDTORes>> getAll(Pageable pageable){
        return ResponseEntity.ok().body(ticketDetailService.getAllTciketsDetails(pageable));
    }

    @PostMapping
    public ResponseEntity<UUID> save(@Valid @RequestBody TicketDetailDTOReq request) throws IdNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketDetailService.saveTicket(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        ticketDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}