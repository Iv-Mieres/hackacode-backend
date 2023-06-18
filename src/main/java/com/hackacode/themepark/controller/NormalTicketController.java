package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.service.INormalTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class NormalTicketController {
    private final INormalTicketService ticketService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveTicket(@RequestBody NormalTicketDTOReq ticket){
        ticketService.saveNormalTicket(ticket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<NormalTicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketService.getNormalTickets(pageable));
    }

    @GetMapping("/{normalTicketId}")
    public ResponseEntity<NormalTicketDTORes> getNormalTicket(@PathVariable UUID normalTicketId) {
        return ResponseEntity.ok(ticketService.getNormalTicketById(normalTicketId));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateNormalTicket(@RequestBody NormalTicketDTOReq normalTicketDTOReq) {
        ticketService.updateNormalTicket(normalTicketDTOReq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{normalTicketId}")
    public ResponseEntity<HttpStatus> deleteVipTicket(@PathVariable UUID normalTicketId) {
        ticketService.deleteNormalTicket(normalTicketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
