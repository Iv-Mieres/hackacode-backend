package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Vip;
import com.hackacode.themepark.repository.IVipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipService implements IVipService {

    @Autowired
    private IVipRepository ticketVipRepository;


    @Override
    public void saveTicketVip(Vip vip) {
        ticketVipRepository.save(vip);
    }

    @Override
    public Vip getTicketVipById(Long TicketVipId) {
        return null;
    }

    @Override
    public List<Vip> getTicketVips() {
        return ticketVipRepository.findAll();
    }

    @Override
    public void updateTicketVip(Long TicketVipId) {

    }

    @Override
    public void deleteTicketVip(Long TicketVipId) {

    }
}
