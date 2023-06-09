package com.hackacode.themepark.service;

import com.hackacode.themepark.model.VipTicket;

import java.util.List;

public interface IVipTicketService {

    void saveTicketVip(VipTicket VipTicket);
    VipTicket getTicketVipById(Long TicketVipId);
    List<VipTicket> getTicketVips();
    void updateTicketVip(Long TicketVipId);
    void deleteTicketVip(Long TicketVipId);
}
