package com.hackacode.themepark.service;

import com.hackacode.themepark.model.TicketVip;
import com.hackacode.themepark.repository.ITicketVipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketVipService implements ITicketVipService{

    @Autowired
    private ITicketVipRepository ticketVipRepository;


    @Override
    public void saveTicketVip(TicketVip ticketVip) {
        ticketVipRepository.save(ticketVip);
    }

    @Override
    public TicketVip getTicketVipById(Long TicketVipId) {
        return null;
    }

    @Override
    public List<TicketVip> getTicketVips() {
        return ticketVipRepository.findAll();
    }

    @Override
    public void updateTicketVip(Long TicketVipId) {

    }

    @Override
    public void deleteTicketVip(Long TicketVipId) {

    }
}
