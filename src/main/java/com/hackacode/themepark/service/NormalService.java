package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Normal;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.INormalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalService implements INormalService {

    @Autowired
    private INormalRepository ticketRepository;

    @Override
    public void saveNormalTicket(Normal ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Normal getNormalTicketById(Long TicketId) {
        return null;
    }

    @Override
    public List<Normal> getNormalTickets() {
        return null;
    }

    @Override
    public void updateNormalTicket(Long TicketId) {

    }

    @Override
    public void deleteNormalTicket(Long TicketId) {

    }
}
