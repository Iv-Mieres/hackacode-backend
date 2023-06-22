package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.VipTicketDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.service.IVipTicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/ticketsvip")
public class VipTicketController {

    @Autowired
    private IVipTicketService ticketVipService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveTicketVip(@Valid @RequestBody VipTicketDTOReq vipTicketDTOReq){
        ticketVipService.saveTicketVip(vipTicketDTOReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<VipTicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketVipService.getTicketVips(pageable));
    }

    @GetMapping("/{vipTicketId}")
    public ResponseEntity<VipTicketDTORes> getTicketVip(@PathVariable UUID vipTicketId) throws IdNotFoundException {
        return ResponseEntity.ok(ticketVipService.getTicketVipById(vipTicketId));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateVipTicket(@Valid @RequestBody VipTicketDTOReq vipTicketDTOReq) throws IdNotFoundException {
        ticketVipService.updateTicketVip(vipTicketDTOReq);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{vipTicketId}")
    public ResponseEntity<HttpStatus> deleteVipTicket(@PathVariable UUID vipTicketId) {
        ticketVipService.deleteTicketVip(vipTicketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
