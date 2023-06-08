package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.Normal;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.service.INormalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private INormalService ticketService;

    @PostMapping("/")
    public void saveTicket(@RequestBody Normal ticket){
        ticketService.saveNormalTicket(ticket);
    }

}
