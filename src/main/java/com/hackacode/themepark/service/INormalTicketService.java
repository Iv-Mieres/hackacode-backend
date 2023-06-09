package com.hackacode.themepark.service;

import com.hackacode.themepark.model.NormalTicket;

import java.util.List;

public interface INormalTicketService {

    void saveNormalTicket(NormalTicket Ticket);
    NormalTicket getNormalTicketById(Long TicketId);
    List<NormalTicket> getNormalTickets();
    void updateNormalTicket(Long TicketId);
    void deleteNormalTicket(Long TicketId);
}
