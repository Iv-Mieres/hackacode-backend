package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Vip;

import java.util.List;

public interface IVipService {

    void saveTicketVip(Vip Vip);
    Vip getTicketVipById(Long TicketVipId);
    List<Vip> getTicketVips();
    void updateTicketVip(Long TicketVipId);
    void deleteTicketVip(Long TicketVipId);
}
