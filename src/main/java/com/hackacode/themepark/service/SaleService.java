package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.repository.IGameRepository;
import com.hackacode.themepark.repository.INormalTicketRepository;
import com.hackacode.themepark.repository.ISaleRepository;
import com.hackacode.themepark.repository.IVipTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleService implements ISaleService {

    private final ISaleRepository saleRepository;
    private final IGameRepository gameRepository;
    private final INormalTicketRepository normalTicketRepository;
    private final IVipTicketRepository vipTicketRepository;
    private final ModelMapper modelMapper;


    //CREA UNA VENTA
    @Override
    public void saveSale(SaleDTOReq saleDTOReq) throws Exception {
        //Mapeo del DTO de venta al modelo de venta
        var sale = modelMapper.map(saleDTOReq, Sale.class);
        //Calcular y setear precio
        sale.setTotalPrice(calculateTotalPrice(saleDTOReq));
        //Guardo la venta
        var saleDB = saleRepository.save(sale);
    }

    //MUESTRA UNA VENTA POR ID
    @Override
    public SaleDTORes getSaleById(Long saleId) throws IdNotFoundException {
        return modelMapper.map(saleRepository.findById(saleId)
                .orElseThrow(() -> new IdNotFoundException("El id " + saleId + " no existe")), SaleDTORes.class);
    }

    //LISTA DTO DE VENTAS PAGINADAS
    @Override
    public Page<SaleDTORes> getSales(Pageable pageable) {
        var sales = saleRepository.findAll(pageable);
        var salesDTO = new ArrayList<SaleDTORes>();

        for (Sale sale : sales) {
            salesDTO.add(modelMapper.map(sale, SaleDTORes.class));
        }
        return new PageImpl<>(salesDTO, pageable, salesDTO.size());
    }

    //MODIFICA UNA VENTA
    @Override
    public void updateSale(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        if(saleRepository.existsById(saleDTOReq.getId())){
            throw new IdNotFoundException("El id " + saleDTOReq.getId() + " no existe");
        }
        var saleUpdate = modelMapper.map(saleDTOReq, Sale.class);
        saleRepository.save(saleUpdate);
    }

    //ELIMINA UNA VENTA
    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }


    //CALCULA EL PRECIO TOTAL DE LA VENTA REALIZADA
    public Double calculateTotalPrice(SaleDTOReq saleDTOReq) throws IdNotFoundException {
        var normalTickets = new ArrayList<NormalTicket>();
        var vipTickets = new ArrayList<VipTicket>();

        if (saleDTOReq.getNormalTickets() != null) {
            for (NormalTicketDTOReq ticket : saleDTOReq.getNormalTickets()) {
                normalTickets.add(modelMapper.map(ticket, NormalTicket.class));
            }
        }
        if (saleDTOReq.getVipTickets() != null) {
            for (VipTicketDTOReq ticket : saleDTOReq.getVipTickets()) {
                vipTickets.add(modelMapper.map(ticket, VipTicket.class));
            }
        }
        Optional<Game> game;
        Double totalPrice = 0.0;
        Optional<NormalTicket> optNormalTicket = null;
        Optional<VipTicket> optionalVipTicket = null;
        if (normalTickets.size() != 0) {
            for (NormalTicket ticket : normalTickets) {
                optNormalTicket = Optional.ofNullable(normalTicketRepository.findById(ticket.getId())
                        .orElseThrow(() -> new IdNotFoundException("Id de normal ticket invalido")));
                game = gameRepository.findById(optNormalTicket.get().getGame().getId());
                if (game.isPresent()) {
                    totalPrice += game.get().getPrice();
                }
            }
        }
        if (vipTickets.size() != 0) {
            for (VipTicket ticket : vipTickets) {
                optionalVipTicket = Optional.ofNullable(vipTicketRepository.findById(ticket.getId())
                        .orElseThrow(() -> new IdNotFoundException("Id de vip ticket invalido")));
                totalPrice += optionalVipTicket.get().getPrice();
            }
        }

        return totalPrice;
    }

    //SUMA DE MONTO TOTAL DE UN DETERMINADO DÍA
    @Override
    public ReportDTORes sumTotalAmountOfAGivenDay(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        var salesBD = saleRepository.findAllByPurchaseDateBetween(start, end);
        return this.getReport(salesBD, "day");
    }

    //SUMA DE MONTO TOTAL DE UN DETERMINADO AÑO Y MES
    @Override
    public ReportDTORes sumTotalAmountOfAGivenMonth(int year, int month) {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        var salesBD = saleRepository.findAllByPurchaseDateBetween(start, end);
        return this.getReport(salesBD, "month");
    }

    //GENERA UN REPORT DTO
    public ReportDTORes getReport(List<Sale> salesBD, String dayOrmonth) {

        double sumTotal = 0.0;
        long totalNormalTicketsSold = 0L;
        long totalVipTicketsSold = 0L;

        //recorre la lista de ventas, suma el total de la ventas y el total de tickets vendidos
        for (Sale sale : salesBD) {
            if (sale.getNormalTickets().isEmpty() && sale.getVipTickets() != null) {
                sumTotal += sale.getTotalPrice();
                totalVipTicketsSold += sale.getVipTickets().size();
            } else if (sale.getNormalTickets() != null && sale.getVipTickets().isEmpty()) {
                sumTotal += sale.getTotalPrice();
                totalNormalTicketsSold += sale.getNormalTickets().size();
            } else {
                sumTotal += sale.getTotalPrice();
                totalNormalTicketsSold += sale.getNormalTickets().size();
                totalVipTicketsSold += sale.getVipTickets().size();
            }
        }
        switch (dayOrmonth) {
            case "day":
                //crea y retorna un DTO con los datos del dia ingresado
                return ReportDTORes.builder()
                        .totalAmountSaleDay(sumTotal)
                        .totalNormalTicketsSold(totalNormalTicketsSold)
                        .totalVipTicketsSold(totalVipTicketsSold)
                        .build();
            case "month":
                //crea y retorna un DTO con los datos del año y mes ingesados
                return ReportDTORes.builder()
                        .totalAmountSaleMonthAndYear(sumTotal)
                        .totalNormalTicketsSold(totalNormalTicketsSold)
                        .totalVipTicketsSold(totalVipTicketsSold)
                        .build();
            default: return null;
        }
    }

}
