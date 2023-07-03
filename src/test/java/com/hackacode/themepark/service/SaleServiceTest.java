package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.ISaleRepository;
import com.hackacode.themepark.repository.IScheduleRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @InjectMocks
    private SaleService saleService;

    @Mock
    private ISaleRepository saleRepository;
    @Mock
    private ITicketDetailRepository ticketDetailRepository;
    @Mock
    private ModelMapper modelMapper;

    private Sale sale;
    private SaleDTOReq saleDTOReq;
    private SaleDTORes saleDTORes;
    private TicketDetail ticketDetail;

    @BeforeEach
    void setUp() {
        this.sale = new Sale(1L, 0.0, LocalDateTime.now(), null, null);
        this.saleDTOReq = new SaleDTOReq();
    }

    @DisplayName("comprueba que se guarda una venta")
    @Test
    void saveSale() throws Exception {
        var uuid = UUID.randomUUID();

        var ticketDTO = new TicketDTOReq();
        ticketDTO.setId(1L);
        ticketDTO.setPrice(3000.0);

        var ticketDTO2 = new TicketDTOReq();
        ticketDTO2.setId(1L);
        ticketDTO2.setPrice(7000.0);

        var ticketDetailReq = new TicketDetailDTOReq();
        ticketDetailReq.setId(uuid);
        ticketDetailReq.setTicket(ticketDTO);

        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(3000.0);

        this.ticketDetail = new TicketDetail();
        ticketDetail.setId(uuid);
        ticketDetail.setTicket(ticket);

        this.sale.setTicketsDetail(List.of(ticketDetail));

        this.saleDTOReq.setId(1L);
        this.saleDTOReq.setTicketsDetail(List.of(ticketDetailReq));

        when(ticketDetailRepository.findById(uuid)).thenReturn(Optional.ofNullable(this.ticketDetail));
        when(modelMapper.map(this.saleDTOReq, Sale.class)).thenReturn(this.sale);

        saleService.saveSale(this.saleDTOReq);
        verify(saleRepository).save(this.sale);
    }

    @DisplayName("comprueba que devuelve una venta por id")
    @Test
    void getSaleById() throws IdNotFoundException {
        when(saleRepository.findById(1L)).thenReturn(Optional.ofNullable(this.sale));
        when(modelMapper.map(this.sale, SaleDTORes.class)).thenReturn(this.saleDTORes);
        var currentSale = saleService.getSaleById(1L);
        assertEquals(this.saleDTORes, currentSale);
        verify(saleRepository).findById(1L);
    }

    @DisplayName("comprueba que devuelva una lista de ventas paginada")
    @Test
    void getSales() {
        int page =0;
        int size = 1;
        var pageable = PageRequest.of(page, size);

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(new Sale());


        when(saleRepository.findAll(pageable)).thenReturn(new PageImpl<>(sales, pageable, sales.size()));
        when(modelMapper.map(this.sale, SaleDTORes.class)).thenReturn(this.saleDTORes);
        var currentSales = saleService.getSales(pageable);

        assertEquals(2, currentSales.getTotalElements());
        assertEquals(0, currentSales.getNumber());
        assertEquals(2, currentSales.getTotalPages());
        verify(saleRepository).findAll(pageable);


    }

    @DisplayName("comprueba que se modifique una venta")
    @Test
    void updateSale() throws Exception {
        var uuid = UUID.randomUUID();

        var ticketDTO = new TicketDTOReq();
        ticketDTO.setId(1L);
        ticketDTO.setPrice(3000.0);

        var ticketDTO2 = new TicketDTOReq();
        ticketDTO2.setId(1L);
        ticketDTO2.setPrice(7000.0);

        var ticketDetailReq = new TicketDetailDTOReq();
        ticketDetailReq.setId(uuid);
        ticketDetailReq.setTicket(ticketDTO);

        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(3000.0);

        this.ticketDetail = new TicketDetail();
        ticketDetail.setId(uuid);
        ticketDetail.setTicket(ticket);

        this.sale.setTicketsDetail(List.of(ticketDetail));

        this.saleDTOReq.setId(1L);
        this.saleDTOReq.setTicketsDetail(List.of(ticketDetailReq));

        when(saleRepository.existsById(this.saleDTOReq.getId())).thenReturn(true);
        when(ticketDetailRepository.findById(uuid)).thenReturn(Optional.ofNullable(this.ticketDetail));
        when(modelMapper.map(this.saleDTOReq, Sale.class)).thenReturn(this.sale);

        saleService.updateSale(this.saleDTOReq);
        verify(saleRepository).save(this.sale);

    }

    @Test
    void deleteSale() {
        doNothing().when(saleRepository).deleteById(1L);
        saleService.deleteSale(1L);
        verify(saleRepository).deleteById(1L);
    }

    @Test
    void calculateTotalPrice() {
    }
}