package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.ISaleRepository;
import com.hackacode.themepark.repository.IScheduleRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        this.ticketDetail = new TicketDetail();
        this.sale = new Sale(1L, 5000.0, LocalDateTime.now(), List.of(ticketDetail), null);
        this.saleDTOReq = new SaleDTOReq();

    }

//    @Test
//    void saveSale() throws Exception {
//        var uuid = UUID.randomUUID();
//
//        var ticketDTO = new TicketDTOReq();
//        ticketDTO.setId(1L);
//        ticketDTO.setPrice(3000.0);
//
//        var ticketDTO2 = new TicketDTOReq();
//        ticketDTO2.setId(1L);
//        ticketDTO2.setPrice(7000.0);
//
//        var ticketDetailReq = new TicketDetailDTOReq();
//        ticketDetailReq.setId(uuid);
//        ticketDetailReq.setTicket(ticketDTO);
//
//        var ticketDetailReq2 = new TicketDetailDTOReq();
//        ticketDetailReq2.setId(uuid);
//        ticketDetailReq2.setTicket(ticketDTO2);
//
//        this.saleDTOReq.setId(1L);
//        this.saleDTOReq.setTicketsDetail(List.of(ticketDetailReq, ticketDetailReq2));
//
//
//        when(modelMapper.map(this.saleDTOReq, Sale.class)).thenReturn(this.sale);
//        when(ticketDetailRepository.findById(uuid)).thenReturn(Optional.ofNullable(this.ticketDetail));
//        saleService.saveSale(this.saleDTOReq);
//        verify(saleRepository).save(this.sale);
//    }

    @Test
    void getSaleById() {
    }

    @Test
    void getSales() {
    }

    @Test
    void updateSale() {
    }

    @Test
    void deleteSale() {
    }

    @Test
    void calculateTotalPrice() {
    }
}