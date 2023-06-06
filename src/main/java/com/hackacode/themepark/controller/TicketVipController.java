package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.TicketVip;
import com.hackacode.themepark.service.ITicketVipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket_vip")
public class TicketVipController {

    @Autowired
    private ITicketVipService ticketVipService;

    @PostMapping()
    public void saveTicketVip(@Valid @RequestBody TicketVip ticketVip){

        ticketVipService.saveTicketVip(ticketVip);
    }

    @GetMapping("/ver_todos")
    public List<TicketVip> allVips(){
        return ticketVipService.getTicketVips();
    }



}
