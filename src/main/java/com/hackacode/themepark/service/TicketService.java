package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {

    private final ITicketRepository ticketRepository;
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
        var ticketBD = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe"));
        if (ticketBD.isDelete()){
            throw new EntityNotFoundException("El ticket selecionado ha sido eliminado");
        }
        return modelMapper.map(ticketBD, TicketDTORes.class);
    }

    //LISTA DTO DE TICKETS
    @Override
    public Page<TicketDTORes> getTickets(Pageable pageable) {
        var TicketsDb = ticketRepository.findAllByIsDelete(pageable, false);
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
        ticketRepository.save(modelMapper.map(ticketDTOReq, Ticket.class));
    }

    //ELIMINA UN TICKET
    @Override
    public void deleteTicket(Long ticketId) throws IdNotFoundException {
        var ticketBD = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("El id " + ticketId + " no existe"));
        ticketBD.setDescription("");
        ticketBD.setDelete(true);
        ticketRepository.save(ticketBD);
    }
}
