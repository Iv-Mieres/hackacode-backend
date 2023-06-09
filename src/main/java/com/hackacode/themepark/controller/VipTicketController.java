package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.service.IVipTicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket_vip")
public class VipTicketController {

    @Autowired
    private IVipTicketService ticketVipService;

    @PostMapping()
    public void saveTicketVip(@Valid @RequestBody VipTicket vipTicket){

        ticketVipService.saveTicketVip(vipTicket);
    }

    @GetMapping("/ver_todos")
    public List<VipTicket> allVips(){
        return ticketVipService.getTicketVips();
    }



}