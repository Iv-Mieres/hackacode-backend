package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.VipTicketDTORes;
import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.repository.IVipTicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VipTicketService implements IVipTicketService {

    private IVipTicketRepository ticketVipRepository;
    private ModelMapper modelMapper;

    @Override
    public void saveTicketVip(VipTicketDTOReq vipTicketDTOReq) {

        ticketVipRepository.save(modelMapper.map(vipTicketDTOReq, VipTicket.class));
    }

    @Override
    public VipTicketDTORes getTicketVipById(UUID ticketVipId) {

        return modelMapper.map(ticketVipRepository.findById(ticketVipId), VipTicketDTORes.class);
    }

    @Override
    public Page<VipTicketDTORes> getTicketVips(Pageable pageable) {
        var ticketsVipDb = ticketVipRepository.findAll();
        var ticketsVipDTO = new ArrayList<VipTicketDTORes>();
        for (VipTicket ticket: ticketsVipDb) {
            ticketsVipDTO.add(modelMapper.map(ticket, VipTicketDTORes.class));
        }
        return new PageImpl<>(ticketsVipDTO, pageable, ticketsVipDTO.size());
    }

    @Override
    public void updateTicketVip(Long TicketVipId) {

    }

    @Override
    public void deleteTicketVip(Long TicketVipId) {

    }
}
