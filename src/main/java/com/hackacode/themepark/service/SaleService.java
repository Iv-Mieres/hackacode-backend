package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements ISaleService {

    private final ISaleRepository saleRepository;
    private final ITicketDetailRepository ticketDetailRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveSale(SaleDTOReq request) throws Exception {
        Sale sale = modelMapper.map(request, Sale.class);
        sale.setTotalPrice(calculateTotalPrice(request));

        saleRepository.save(sale);

    }

    @Override
    public SaleDTORes getSaleById(Long id) throws IdNotFoundException {
        return modelMapper.map(saleRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("El id " + id + " no existe")), SaleDTORes.class);
    }

    @Override
    public Page<SaleDTORes> getSales(Pageable pageable) {
        Page<Sale> sales = saleRepository.findAll(pageable);
        List<SaleDTORes> salesDTO = new ArrayList<>();

        for (Sale sale : sales) {
            salesDTO.add(modelMapper.map(sale, SaleDTORes.class));
        }
        return new PageImpl<>(salesDTO, pageable, salesDTO.size());
    }

    @Override
    public void updateSale(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        if (saleRepository.existsById(saleDTOReq.getId())) {
            throw new IdNotFoundException("El id " + saleDTOReq.getId() + " no existe");
        }
        var saleUpdate = modelMapper.map(saleDTOReq, Sale.class);
        saleRepository.save(saleUpdate);
    }

    @Override
    public void deleteSale(Long id) throws IdNotFoundException {
        Sale sale = saleRepository.findById(id).orElseThrow(
                () -> new IdNotFoundException("La venta con el id ingresado no existe"));
        saleRepository.deleteById(id);
    }

    //CALCULA EL PRECIO TOTAL DE LA VENTA REALIZADA
    public Double calculateTotalPrice(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        if (saleDTOReq.getTicketsDetail() == null) {
            return 0.0;
        }
        List<TicketDetailDTOReq> ticketsDetail = saleDTOReq.getTicketsDetail();
        double totalPrice = 0.0;
        for (TicketDetailDTOReq dtoReq : ticketsDetail) {
            UUID ticketDetailID = dtoReq.getId();
            TicketDetail ticket = ticketDetailRepository.findById(ticketDetailID).orElseThrow(
                    () -> new IdNotFoundException("No se encontro el ticket")
            );
            if (ticket != null) {
                totalPrice += ticket.getTicket().getPrice();
            }

        }

        return totalPrice;
    }
}


