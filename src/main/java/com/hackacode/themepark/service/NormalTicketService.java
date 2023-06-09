package com.hackacode.themepark.service;

import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.repository.INormalTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalTicketService implements INormalTicketService {

    @Autowired
    private INormalTicketRepository ticketRepository;

    @Override
    public void saveNormalTicket(NormalTicket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public NormalTicket getNormalTicketById(Long TicketId) {
        return null;
    }

    @Override
    public List<NormalTicket> getNormalTickets() {
        return null;
    }

    @Override
    public void updateNormalTicket(Long TicketId) {

    }

    @Override
    public void deleteNormalTicket(Long TicketId) {

    }
}
