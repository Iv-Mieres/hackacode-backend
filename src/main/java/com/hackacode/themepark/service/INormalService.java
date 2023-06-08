package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Normal;
import com.hackacode.themepark.model.Ticket;

import java.util.List;

public interface INormalService {

    void saveNormalTicket(Normal Ticket);
    Normal getNormalTicketById(Long TicketId);
    List<Normal> getNormalTickets();
    void updateNormalTicket(Long TicketId);
    void deleteNormalTicket(Long TicketId);
}
