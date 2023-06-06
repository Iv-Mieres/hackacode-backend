package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{

    private ITicketRepository ticketRepository;

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(Long TicketId) {
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        return null;
    }

    @Override
    public void updateTicket(Long TicketId) {

    }

    @Override
    public void deleteTicket(Long TicketId) {

    }
}
