package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketDetailServiceTest {

    @InjectMocks
    private TicketDetailService ticketDetailService;

    @Mock
    private ITicketDetailRepository ticketDetailRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private IBuyerRepository buyerRepository;
    @Mock
    private ITicketRepository ticketRepository;

    private TicketDetail ticketDetail;
    private TicketDetailDTOReq ticketDetailDTOReq;
    private TicketDetailDTORes ticketDetailDTORes;
    private Ticket ticket;
    private Buyer buyer;

    @BeforeEach
    void setUp() {
        this.ticket = new Ticket();
        this.ticket.setId(1L);
        this.ticket.setPrice(3000);
        this.ticket.setDescription("General");
        this.ticket.setVip(false);

        this.buyer = new Buyer();
        this.buyer.setId(1L);

        this.ticketDetail = new TicketDetail();
        this.ticketDetail.setTicket(ticket);
        this.ticketDetail.setBuyer(this.buyer);
        this.ticketDetail.setPurchaseDate(LocalDateTime.now());

        var buyerDTOReq = BuyerDTOReq.builder().id(1L).build();
        var ticketDTOReq = new TicketDTOReq();
        ticketDTOReq.setId(1L);

        this.ticketDetailDTOReq = new TicketDetailDTOReq();
        this.ticketDetailDTOReq.setTicket(ticketDTOReq);
        this.ticketDetailDTOReq.setBuyer(buyerDTOReq);

    }


    @DisplayName("comprueba que lance un excepción si el comprador no existe al guardar una entrada detallada")
    @Test
    void ThrowAnExceptionIfTheBuyerDoesNotExist() throws IdNotFoundException {
        String expectedEx = "El comprador ingresado no se encuentra registrado";

        when(buyerRepository.existsById(this.buyer.getId())).thenReturn(false);
        Exception currentEx = assertThrows(IdNotFoundException.class,
                () -> ticketDetailService.saveTicket(this.ticketDetailDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba que lance un excepción si la entrada no existe al guardar una entrada detallada")
    @Test
    void ThrowAnExceptionIfTheTicketDoesNotExist() throws IdNotFoundException {
        String expectedEx = "El id " + this.ticket.getId() + " no existe";

        when(buyerRepository.existsById(this.buyer.getId())).thenReturn(true);
        Exception currentEx = assertThrows(IdNotFoundException.class,
                () -> ticketDetailService.saveTicket(this.ticketDetailDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("Comprueba que muestre una lista de entradas detalladas paginada")
    @Test
    void getAll() {
        int page = 0;
        int size = 1;
        var pageable = PageRequest.of(page, size);

        var ticketsDetail = new ArrayList<TicketDetail>();
        ticketsDetail.add(this.ticketDetail);
        ticketsDetail.add(new TicketDetail());

        when(ticketDetailRepository.findAll(pageable)).thenReturn(new PageImpl<>(ticketsDetail, pageable, ticketsDetail.size()));
        when(modelMapper.map(this.ticketDetail, TicketDetailDTORes.class)).thenReturn(this.ticketDetailDTORes);
        var currentResults =  ticketDetailService.getAllTciketsDetails(pageable);

        assertEquals(2, currentResults.getTotalElements());
        assertEquals(0, currentResults.getNumber());
        assertEquals(2, currentResults.getTotalPages());

        verify(ticketDetailRepository).findAll(pageable);
    }

    @DisplayName("Comprueba que muestre una entrada detallada por id")
    @Test
    void getById() throws IdNotFoundException {
        var uuid = UUID.randomUUID();
        when(ticketDetailRepository.findById(uuid)).thenReturn(Optional.ofNullable(this.ticketDetail));
        when(modelMapper.map(this.ticketDetail, TicketDetailDTORes.class)).thenReturn(this.ticketDetailDTORes);
        var currentTD = ticketDetailService.getById(uuid);
        verify(ticketDetailRepository).findById(uuid);
        assertEquals(this.ticketDetailDTORes, currentTD);
    }

    @DisplayName("Comprueba que lance una excepción si el id no se encuentra registrado")
    @Test
    void ThrowAnExceptionIfIdExistsAtGetByIdMethod(){
        var uuid = UUID.randomUUID();
        String expectedEx = "El ticket con el id ingresado no se encuentra registado";

        when(ticketDetailRepository.findById(uuid)).thenReturn(Optional.empty());
        Exception currentEx = assertThrows(IdNotFoundException.class, () -> ticketDetailService.getById(uuid));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba que la entrada detallada sea eliminada")
    @Test
    void delete() throws IdNotFoundException {
        var uuid = UUID.randomUUID();
        doNothing().when(ticketDetailRepository).deleteById(uuid);
        ticketDetailService.delete(uuid);
        verify(ticketDetailRepository).deleteById(uuid);

    }

    @DisplayName("comprueba que retorne una fecha")
    @Test
    void lastVisit() {
        when(ticketDetailRepository.findTopByBuyer_idOrderByBuyer_idDesc(this.buyer.getId()))
                .thenReturn(this.ticketDetail);
        var currentPurchaseDate = ticketDetailService.lastVisit(this.buyer.getId());
        assertEquals(this.ticketDetail.getPurchaseDate().toLocalDate().toString(), currentPurchaseDate);
    }

    @DisplayName("comprueba que retorne la excepción 'Sin vistas' si la entrada detalla es nula")
    @Test
    void ifTicketDetailsIsNullReturnsWithoutVisits() {
        when(ticketDetailRepository.findTopByBuyer_idOrderByBuyer_idDesc(this.buyer.getId()))
                .thenReturn(null);
        var currentPurchaseDate = ticketDetailService.lastVisit(this.buyer.getId());
        assertEquals("Sin visitas", currentPurchaseDate);
    }
}