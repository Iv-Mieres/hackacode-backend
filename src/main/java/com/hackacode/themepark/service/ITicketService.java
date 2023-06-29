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

    Long saveTicket(TicketDTOReq Ticket) throws DescriptionExistsException;
    TicketDTORes getTicketById(Long TicketId) throws IdNotFoundException;
    Page<TicketDTORes> getTickets(Pageable pageable);
    void updateTicket(TicketDTOReq normalTicketDTOReq) throws IdNotFoundException;
    void deleteTicket(Long TicketId) throws IdNotFoundException;

    ReportDTORes totalTicketsSoldOnAGivenDate(LocalDate date);

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    ReportDTORes totalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName);

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    BuyerDTORes buyerWithTheMostTicketsSoldInTheMonth(int year, int month) throws Exception;

}
