package com.hackacode.themepark.service;

import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.repository.IVipTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipTicketService implements IVipTicketService {

    @Autowired
    private IVipTicketRepository ticketVipRepository;


    @Override
    public void saveTicketVip(VipTicket vipTicket) {
        ticketVipRepository.save(vipTicket);
    }

    @Override
    public VipTicket getTicketVipById(Long TicketVipId) {
        return null;
    }

    @Override
    public List<VipTicket> getTicketVips() {
        return ticketVipRepository.findAll();
    }

    @Override
    public void updateTicketVip(Long TicketVipId) {

    }

    @Override
    public void deleteTicketVip(Long TicketVipId) {

    }
}
