package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.model.NormalTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface INormalTicketService {

    void saveNormalTicket(NormalTicketDTOReq Ticket);
    NormalTicketDTORes getNormalTicketById(UUID TicketId);
    Page<NormalTicketDTORes> getNormalTickets(Pageable pageable);
    void updateNormalTicket(NormalTicketDTOReq normalTicketDTOReq);
    void deleteNormalTicket(UUID TicketId);
}
