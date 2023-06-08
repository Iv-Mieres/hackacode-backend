package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.Vip;
import com.hackacode.themepark.service.IVipService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket_vip")
public class TicketVipController {

    @Autowired
    private IVipService ticketVipService;

    @PostMapping()
    public void saveTicketVip(@Valid @RequestBody Vip vip){

        ticketVipService.saveTicketVip(vip);
    }

    @GetMapping("/ver_todos")
    public List<Vip> allVips(){
        return ticketVipService.getTicketVips();
    }



}
