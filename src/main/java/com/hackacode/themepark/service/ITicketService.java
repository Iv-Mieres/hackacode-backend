package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ITicketService {

    void saveTicket(TicketDTOReq Ticket) throws DescriptionExistsException;
    TicketDTORes getTicketById(Long TicketId) throws IdNotFoundException;
    Page<TicketDTORes> getTickets(Pageable pageable);
    void updateTicket(TicketDTOReq normalTicketDTOReq) throws IdNotFoundException, DescriptionExistsException;
    void deleteTicket(Long TicketId);

}
