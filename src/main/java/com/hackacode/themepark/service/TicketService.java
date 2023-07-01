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
public class TicketService implements ITicketService {

    private final IGameRepository gameRepository;

    private final ITicketRepository ticketRepository;
    private final ITicketDetailRepository ticketDetailRepository;

    private final IBuyerRepository buyerRepository;
    private final ISaleRepository saleRepository;

    private final ModelMapper modelMapper;

    //CREA UN TICKET
    @Override
    public void saveTicket(TicketDTOReq request) throws DescriptionExistsException {

        if (ticketRepository.existsByDescription(request.getDescription())) {
            throw new DescriptionExistsException("Ya existe un ticket con la descripción ingresada. " +
                    "Ingrese una nueva descripción");
        }
        ticketRepository.save(modelMapper.map(request, Ticket.class));
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
        for (Ticket ticket : TicketsDb) {
            TicketsDTO.add(modelMapper.map(ticket, TicketDTORes.class));
        }
        return new PageImpl<>(TicketsDTO, pageable, TicketsDTO.size());
    }

    //MODIFICA UN TICKET
    @Override
    public void updateTicket(TicketDTOReq ticketDTOReq) throws IdNotFoundException, DescriptionExistsException {
        var ticketBD = ticketRepository.findById(ticketDTOReq.getId())
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe"));

        if (!ticketDTOReq.getDescription().equals(ticketBD.getDescription())
                && ticketRepository.existsByDescription(ticketDTOReq.getDescription())) {
            throw new DescriptionExistsException("Ya existe un ticket con la descripción ingresada. " +
                    "Ingrese una nueva descripción");
        }
        var ticket = modelMapper.map(ticketDTOReq, Ticket.class);
        ticket.setPrice(ticketBD.getPrice());
        ticketRepository.save(ticket);
    }
    //ELIMINA UN TICKET
    @Override
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
