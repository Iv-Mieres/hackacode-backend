package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.service.INormalTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class NormalTicketController {

    private INormalTicketService ticketService;

    @PostMapping("/")
    public void saveTicket(@RequestBody NormalTicket ticket){
        ticketService.saveNormalTicket(ticket);
    }

}
