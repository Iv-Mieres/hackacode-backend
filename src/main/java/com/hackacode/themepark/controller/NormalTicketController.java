package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.service.INormalTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class NormalTicketController {

    private INormalTicketService ticketService;

    @PostMapping("/")
    public void saveTicket(@RequestBody NormalTicketDTOReq ticket){
        ticketService.saveNormalTicket(ticket);
    }

    @GetMapping("/ver_todos")
    public ResponseEntity<Page<NormalTicketDTORes>> getTicketsVip(Pageable pageable){
        return ResponseEntity.ok(ticketService.getNormalTickets(pageable));
    }


}
