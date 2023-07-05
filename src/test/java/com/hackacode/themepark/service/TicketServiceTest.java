package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.exception.DescriptionExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.ITicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {


    @InjectMocks
    private TicketService ticketService;
    @Mock
    private ITicketRepository ticketRepository;
    @Mock
    private ModelMapper modelMapper;

    private Ticket ticket;
    private TicketDTOReq ticketDTOReq;
    private TicketDTORes ticketDTORes;

    @BeforeEach
    void setUp() {

        this.ticket = new Ticket(1L, 3000.0, "General", false, false);
        this.ticketDTOReq = new TicketDTOReq(1L, 3000.0, "General", false);

    }

    @DisplayName("Comprueba que se guarde una entrada")
    @Test
    void saveTicket() throws DescriptionExistsException {

        when(ticketRepository.existsByDescription(this.ticket.getDescription())).thenReturn(false);
        when(modelMapper.map(this.ticketDTOReq, Ticket.class)).thenReturn(this.ticket);
        ticketService.saveTicket(this.ticketDTOReq);
        verify(ticketRepository).save(this.ticket);
    }

    @DisplayName("Comprueba que se lance una excepción si la descripción de la entrada ya existe")
    @Test
    void throwsAnExceptionIfTheEntryDescriptionAlreadyExists() throws DescriptionExistsException {
        String excpectedEx = "Ya existe un ticket con la descripción ingresada. " +
                "Ingrese una nueva descripción";

        when(ticketRepository.existsByDescription(this.ticket.getDescription())).thenReturn(true);
        Exception currentEx = assertThrows(DescriptionExistsException.class, () -> ticketService.saveTicket(this.ticketDTOReq));
        assertEquals(excpectedEx, currentEx.getMessage());
    }

    @DisplayName("Comprueba que muestre un ticket por id")
    @Test
    void getTicketById() throws DescriptionExistsException, IdNotFoundException {

        when(ticketRepository.findById(this.ticket.getId())).thenReturn(Optional.ofNullable(this.ticket));
        when(modelMapper.map(this.ticket, TicketDTORes.class)).thenReturn(this.ticketDTORes);
        var currentTicketDTORes = ticketService.getTicketById(this.ticket.getId());
        assertEquals(this.ticketDTORes, currentTicketDTORes);

    }

    @DisplayName("Comprueba que muestre una lista de entradas paginada")
    @Test
    void getAllTickets() throws DescriptionExistsException, IdNotFoundException {
        int page = 0;
        int size = 1;

        var tickets = new ArrayList<Ticket>();
        tickets.add(this.ticket);
        tickets.add(new Ticket());

        var pageable = PageRequest.of(page, size);

        when(ticketRepository.findAllByIsDelete(pageable, false)).thenReturn(new PageImpl<>(tickets, pageable, tickets.size()));
        when(modelMapper.map(this.ticket, TicketDTORes.class)).thenReturn(this.ticketDTORes);
        var currentTicketsDTORes = ticketService.getTickets(pageable);

        assertEquals(1, currentTicketsDTORes.getSize());
        assertEquals(2, currentTicketsDTORes.getTotalElements());
        assertEquals(0, currentTicketsDTORes.getNumber());
        assertEquals(2, currentTicketsDTORes.getTotalPages());

        verify(ticketRepository).findAllByIsDelete(pageable, false);

    }

    @DisplayName("Comprueba que se modifique una entrada")
    @Test
    void updateTicket() throws DescriptionExistsException, IdNotFoundException {

        when(ticketRepository.findById(this.ticketDTOReq.getId())).thenReturn(Optional.ofNullable(this.ticket));
        when(modelMapper.map(this.ticketDTOReq, Ticket.class)).thenReturn(this.ticket);
        ticketService.updateTicket(this.ticketDTOReq);
        verify(ticketRepository).save(this.ticket);
    }

    @DisplayName("Comprueba que se lance una excepción si la descripción de la entrada ya existe")
    @Test
    void throwsAnExceptionIfTheEntryDescriptionAlreadyExistsWhenYouUpdate() throws IdNotFoundException, DescriptionExistsException {
        String excpectedEx = "Ya existe un ticket con la descripción ingresada. " +
                "Ingrese una nueva descripción";

        this.ticketDTOReq.setDescription("VIP");

        when(ticketRepository.findById(this.ticketDTOReq.getId())).thenReturn(Optional.ofNullable(this.ticket));
        when(ticketRepository.existsByDescription(this.ticketDTOReq.getDescription())).thenReturn(true);
        Exception currentEx = assertThrows(Exception.class,
                () -> ticketService.updateTicket(this.ticketDTOReq));
        assertEquals(excpectedEx, currentEx.getMessage());
    }

    @DisplayName("Comprueba que se elimine una entrada")
    @Test
    void deleteTicket() throws IdNotFoundException {

        when(ticketRepository.findById(this.ticket.getId())).thenReturn(Optional.ofNullable(this.ticket));
        ticketService.deleteTicket(this.ticketDTOReq.getId());
        verify(ticketRepository).save(this.ticket);
    }

}