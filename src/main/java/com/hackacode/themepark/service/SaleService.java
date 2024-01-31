package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
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

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements ISaleService {

    private final ISaleRepository saleRepository;
    private final ITicketDetailRepository ticketDetailRepository;
    private final ModelMapper modelMapper;

    //CREAR UNA VENTA
    @Override
    public void saveSale(SaleDTOReq saleDTOReq) throws Exception {
        Sale sale = modelMapper.map(saleDTOReq, Sale.class);
        sale.setTotalPrice(this.calculateTotalPrice(saleDTOReq));
        saleRepository.save(sale);
    }

    //MUESTRA UNA VENTA POR ID
    @Override
    public SaleDTORes getSaleById(Long id) throws IdNotFoundException {
        return modelMapper.map(saleRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("El id " + id + " no existe")), SaleDTORes.class);
    }

    //LISTA VENTAS PAGINADAS
    @Override
    public Page<SaleDTORes> getSales(Pageable pageable) {
        var sales = saleRepository.findAll(pageable);
        var salesDTO = new ArrayList<SaleDTORes>();

        for (Sale sale : sales) {
            salesDTO.add(modelMapper.map(sale, SaleDTORes.class));
        }
        return new PageImpl<>(salesDTO, pageable, salesDTO.size());
    }

    //MODIFICA UNA LISTA POR ID
    @Override
    public void updateSale(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        if (!saleRepository.existsById(saleDTOReq.getId())) {
            throw new IdNotFoundException("El id " + saleDTOReq.getId() + " no existe");
        }
        var saleUpdate = modelMapper.map(saleDTOReq, Sale.class);
        saleUpdate.setTotalPrice(this.calculateTotalPrice(saleDTOReq));
        saleRepository.save(saleUpdate);
    }

    //ELIMINA UNA LISTA POR ID
    @Override
    public void deleteSale(Long id){
        saleRepository.deleteById(id);
    }

    //CALCULA EL PRECIO TOTAL DE LA VENTA REALIZADA
    public Double calculateTotalPrice(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        if (saleDTOReq.getTicketsDetail() == null) {
            return 0.0;
        }
        double totalPrice = 0.0;
        for (TicketDetailDTOReq ticketDetailDTOReq : saleDTOReq.getTicketsDetail()) {
            TicketDetail ticketDetail = ticketDetailRepository.findById(ticketDetailDTOReq.getId()).orElseThrow(
                    () -> new IdNotFoundException("No se encontro el ticket")
            );
            if (ticketDetail != null) {
                totalPrice += ticketDetail.getPrice();
            }
        }
        return totalPrice;
    }
}


