package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.*;
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
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService{

    private final IGameRepository gameRepository;

    private final ITicketRepository ticketRepository;
    private final ITicketDetailRepository ticketDetailRepository;

    private final IBuyerRepository buyerRepository;
    private final ISaleRepository saleRepository;

    private final ModelMapper modelMapper;

    //CREA UN TICKET
    @Override
    public Long saveTicket(TicketDTOReq request) throws DescriptionExistsException {

        if (ticketRepository.existsByDescription(request.getDescription())){
            throw new DescriptionExistsException("Ya existe un ticket con la descripción ingresada. " +
                    "Ingrese una nueva descripción");
        }
        Ticket ticket = modelMapper.map(request, Ticket.class);

        //Guarda el Ticket y retorno el ID
        return ticketRepository.save(ticket).getId();
    }

    //MUESTRA UN TICKET POR ID
    @Override
    public TicketDTORes getTicketById(Long ticketId) throws IdNotFoundException {
        return modelMapper.map(ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe")), TicketDTORes.class);
    }

    //LISTA DTO DE TICKETS
    @Override
    public Page<TicketDTORes> getTickets(Pageable pageable) {
        var TicketsDb = ticketRepository.findAll(pageable);
        List<TicketDTORes> TicketsDTO = new ArrayList<>();
        for(Ticket ticket: TicketsDb) {
            TicketsDTO.add(modelMapper.map(ticket, TicketDTORes.class));
        }
        return new PageImpl<>(TicketsDTO, pageable, TicketsDTO.size());
    }

    //MODIFICA UN TICKET
    @Override
    public void updateTicket(TicketDTOReq ticketDTOReq) throws IdNotFoundException {
        var normalTicketBD = ticketRepository.findById(ticketDTOReq.getId())
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe"));

        var normalTicket = modelMapper.map(ticketDTOReq, Ticket.class);
        normalTicket.setPrice(normalTicketBD.getPrice());

        ticketRepository.save(normalTicket);
    }

    //ELIMINA UN TICKET
    @Override
    public void deleteTicket(Long ticketId) throws IdNotFoundException {
        ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe"));
        ticketRepository.deleteById(ticketId);
    }


    // CANTIDAD DE ENTRADAS VENDIDAS DE TODOS LOS JUEGOS EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalTicketsSoldOnAGivenDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalTickets = ticketDetailRepository.countByPurchaseDateBetween(start, end);
        return ReportDTORes.builder().totalTicketsSold(totalTickets).build();

    }

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName){
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalTickets = saleRepository.countByPurchaseDateBetweenAndGame_name(start, end, gameName);
        return ReportDTORes.builder()
                .totalTicketsSold(totalTickets)
                .gameName(gameName)
                .build();

    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS COMPRÓ EN UN DETERMINADO MES
    @Override
    public BuyerDTORes buyerWithTheMostTicketsSoldInTheMonth(int year, int month) throws Exception {
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        TicketDetail ticketDetail = ticketDetailRepository.findTopByPurchaseDateBetweenOrderByBuyer_IdDesc(start, end);
        if(ticketDetail == null || ticketDetail.getBuyer() == null){
            throw new Exception("La fecha ingresada no contiene ventas");
        }

        return modelMapper.map(ticketDetail.getBuyer(), BuyerDTORes.class);

    }


}
