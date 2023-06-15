package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.VipTicketDTORes;
import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.service.IVipTicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ticket_vip")
public class VipTicketController {

    @Autowired
    private IVipTicketService ticketVipService;

    @PostMapping()
    public void saveTicketVip(@Valid @RequestBody VipTicketDTOReq vipTicketDTOReq){

        ticketVipService.saveTicketVip(vipTicketDTOReq);
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<VipTicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketVipService.getTicketVips(pageable));
    }

    @GetMapping("/")
    public ResponseEntity<VipTicketDTORes> getTicketVip(@PathVariable UUID vipTicketId) {
        return ResponseEntity.ok(ticketVipService.getTicketVipById(vipTicketId));
    }
}
