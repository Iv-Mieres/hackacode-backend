package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.repository.INormalTicketRepository;
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
public class NormalTicketService implements INormalTicketService {
    private final INormalTicketRepository ticketRepository;

    private final ModelMapper modelMapper;

    @Override
    public void saveNormalTicket(NormalTicketDTOReq ticket) {
        ticketRepository.save(modelMapper.map(ticket, NormalTicket.class));
    }

    @Override
    public NormalTicketDTORes getNormalTicketById(UUID ticketId) {
        return modelMapper.map(ticketRepository.findById(ticketId), NormalTicketDTORes.class);
    }

    @Override
    public Page<NormalTicketDTORes> getNormalTickets(Pageable pageable) {
        var normalTicketsDb = ticketRepository.findAll(pageable);
        var normalTicketsDTO = new ArrayList<NormalTicketDTORes>();
        for(NormalTicket ticket: normalTicketsDb) {
            normalTicketsDTO.add(modelMapper.map(ticket, NormalTicketDTORes.class));
        }
        return new PageImpl<>(normalTicketsDTO, pageable, normalTicketsDTO.size());
    }

    @Override
    public void updateNormalTicket(NormalTicketDTOReq ticketDTOReq) {
    ticketRepository.save(modelMapper.map(ticketDTOReq, NormalTicket.class));
    }

    @Override
    public void deleteNormalTicket(UUID ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
