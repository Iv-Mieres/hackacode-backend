package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface ITicketService {

    UUID saveNormalTicket(TicketDTOReq Ticket) throws Exception;
    TicketDTORes getNormalTicketById(UUID TicketId) throws IdNotFoundException;
    Page<TicketDTORes> getNormalTickets(Pageable pageable);
    void updateNormalTicket(TicketDTOReq normalTicketDTOReq) throws IdNotFoundException;
    void deleteNormalTicket(UUID TicketId);

    ReportDTORes totalNormalTicketsSoldOnAGivenDate(LocalDate date);

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    ReportDTORes totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName);

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception;

    String lastVisit(Long buyerId);
}
