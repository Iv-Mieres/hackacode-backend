package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.repository.IGameRepository;
import com.hackacode.themepark.repository.INormalTicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class NormalTicketService implements INormalTicketService {

    private final IGameRepository gameRepository;

    private final INormalTicketRepository ticketRepository;

    private final ModelMapper modelMapper;

    //CREA UN TICKET NORMAL
    @Override
    public void saveNormalTicket(NormalTicketDTOReq ticket) {
        ticketRepository.save(modelMapper.map(ticket, NormalTicket.class));
    }

    //MUESTRA UN TICKET POR ID
    @Override
    public NormalTicketDTORes getNormalTicketById(UUID ticketId) {
        return modelMapper.map(ticketRepository.findById(ticketId), NormalTicketDTORes.class);
    }

    //LISTA DTO DE TICKETS
    @Override
    public Page<NormalTicketDTORes> getNormalTickets(Pageable pageable) {
        var normalTicketsDb = ticketRepository.findAll(pageable);
        var normalTicketsDTO = new ArrayList<NormalTicketDTORes>();
        for(NormalTicket ticket: normalTicketsDb) {
            normalTicketsDTO.add(modelMapper.map(ticket, NormalTicketDTORes.class));
        }
        return new PageImpl<>(normalTicketsDTO, pageable, normalTicketsDTO.size());
    }

    //MODIFICA UN TICKET
    @Override
    public void updateNormalTicket(NormalTicketDTOReq ticketDTOReq) {
    ticketRepository.save(modelMapper.map(ticketDTOReq, NormalTicket.class));
    }

    //ELIMINA UN TICKET
    @Override
    public void deleteNormalTicket(UUID ticketId) {
        ticketRepository.deleteById(ticketId);
    }


    // CANTIDAD DE ENTRADAS VENDIDAS DE TODOS LOS JUEGOS EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalNormalTicketsSoldOnAGivenDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalnormalTickets = ticketRepository.countByPurchaseDateBetween(start, end);
        var reportDTORes = ReportDTORes.builder().totalNormalTicketsSold(totalnormalTickets).build();

        return reportDTORes;
    }

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName){
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalNormalTickets = ticketRepository.countByPurchaseDateBetweenAndGame_name(start, end, gameName);
        var reportDTOres = ReportDTORes.builder()
                .totalNormalTicketsSold(totalNormalTickets)
                .gameName(gameName)
                .build();

        return reportDTOres;
    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    @Override
    public BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        var normaTicketBD = ticketRepository.findTopByPurchaseDateBetweenOrderByBuyer_idDesc(start, end);
        if(normaTicketBD.getBuyer() == null){
            throw new Exception("La fecha ingresada no contiene ventas");
        }
        return modelMapper.map(normaTicketBD.getBuyer(), BuyerDTORes.class);
    }

}
