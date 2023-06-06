package com.hackacode.themepark.service;

import com.hackacode.themepark.model.TicketVip;

import java.util.List;

public interface ITicketVipService {

    void saveTicketVip(TicketVip TicketVip);
    TicketVip getTicketVipById(Long TicketVipId);
    List<TicketVip> getTicketVips();
    void updateTicketVip(Long TicketVipId);
    void deleteTicketVip(Long TicketVipId);
}
