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
    private TicketDetailDTOReq ticketDetailDTOReq;

    @BeforeEach
    void setUp() {
        var uuid = UUID.randomUUID();

        var ticketDTO = new TicketDTOReq();
        ticketDTO.setId(1L);
        ticketDTO.setPrice(3000.0);

        this.ticketDetailDTOReq = new TicketDetailDTOReq();
        this.ticketDetailDTOReq.setId(uuid);
        this.ticketDetailDTOReq.setTicket(ticketDTO);

        var ticket = new Ticket();
        ticket.setId(1L);
        ticket.setPrice(3000.0);

        this.ticketDetail = new TicketDetail();
        ticketDetail.setId(uuid);
        ticketDetail.setTicket(ticket);

        this.sale = new Sale(1L, 0.0, LocalDateTime.now(), List.of(this.ticketDetail), null);

        this.saleDTORes = new SaleDTORes();

        this.saleDTOReq = new SaleDTOReq();
        this.saleDTOReq.setId(1L);
        this.saleDTOReq.setTicketsDetail(List.of(this.ticketDetailDTOReq));


    }

    @DisplayName("comprueba que se guarda una venta")
    @Test
    void saveSale() throws Exception {
        when(ticketDetailRepository.findById(this.ticketDetailDTOReq.getId()))
                .thenReturn(Optional.ofNullable(this.ticketDetail));
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
        assertEquals(this.sale.getTotalPrice(), currentSale.getTotalPrice());
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
        when(saleRepository.existsById(this.saleDTOReq.getId())).thenReturn(true);
        when(ticketDetailRepository.findById(this.ticketDetailDTOReq.getId())).thenReturn(Optional.ofNullable(this.ticketDetail));
        when(modelMapper.map(this.saleDTOReq, Sale.class)).thenReturn(this.sale);

        saleService.updateSale(this.saleDTOReq);
        verify(saleRepository).save(this.sale);
    }

    @DisplayName("comprueba que se elimine una venta")
    @Test
    void deleteSale() {
        doNothing().when(saleRepository).deleteById(1L);
        saleService.deleteSale(1L);
        verify(saleRepository).deleteById(1L);
    }

    @DisplayName("comprueba que la cuenta sea correcta")
    @Test
    void calculateTotalPrice() throws IdNotFoundException {
        this.ticketDetail.setPrice(3000.0);
        when(ticketDetailRepository.findById(this.ticketDetail.getId())).thenReturn(Optional.ofNullable(this.ticketDetail));
        var currentTotal = saleService.calculateTotalPrice(this.saleDTOReq);
        assertEquals(3000.0, currentTotal);
    }
}