package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.NormalTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface INormalTicketService {

    UUID saveNormalTicket(NormalTicketDTOReq Ticket) throws Exception;
    NormalTicketDTORes getNormalTicketById(UUID TicketId) throws IdNotFoundException;
    Page<NormalTicketDTORes> getNormalTickets(Pageable pageable);
    void updateNormalTicket(NormalTicketDTOReq normalTicketDTOReq) throws IdNotFoundException;
    void deleteNormalTicket(UUID TicketId);

    ReportDTORes totalNormalTicketsSoldOnAGivenDate(LocalDate date);

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    ReportDTORes totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName);

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception;
}
