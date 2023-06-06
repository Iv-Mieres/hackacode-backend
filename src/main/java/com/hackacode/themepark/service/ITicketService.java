package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Ticket;

import java.util.List;

public interface ITicketService {

    void saveTicket(Ticket Ticket);
    Ticket getTicketById(Long TicketId);
    List<Ticket> getTickets();
    void updateTicket(Long TicketId);
    void deleteTicket(Long TicketId);
}
