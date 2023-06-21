package com.hackacode.themepark.service;


import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.request.SaleDTOReq;
import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.SaleDTORes;
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

    @Override
    public void saveSale(SaleDTOReq saleDTOReq) throws Exception {
        //Mapeo del DTO de venta al modelo de venta
        var sale = modelMapper.map(saleDTOReq, Sale.class);
        //Calcular y setear precio
        sale.setTotalPrice(calculateTotalPrice(saleDTOReq));
        //Guardo la venta
        var saleDB = saleRepository.save(sale);
    }

    @Override
    public SaleDTORes getSaleById(Long saleId) {
        var saleDB = saleRepository.findById(saleId).orElse(null);
        return modelMapper.map(saleDB, SaleDTORes.class);
    }

    @Override
    public Page<SaleDTORes> getSales(Pageable pageable) {
        var sales = saleRepository.findAll(pageable);
        var salesDTO = new ArrayList<SaleDTORes>();

        for (Sale sale : sales) {
            salesDTO.add(modelMapper.map(sale, SaleDTORes.class));
        }
        return new PageImpl<>(salesDTO, pageable, salesDTO.size());
    }

    @Override
    public void updateSale(SaleDTOReq saleDTOReq) throws Exception {
        var saleUpdate = modelMapper.map(saleDTOReq, Sale.class);
        saleRepository.save(saleUpdate);
    }

    @Override
    public void deleteSale(Long saleId) {
        saleRepository.deleteById(saleId);
    }

    public Double calculateTotalPrice(SaleDTOReq saleDTOReq) throws Exception {
        List<NormalTicket> normalTickets = new ArrayList<>();
        List<VipTicket> vipTickets = new ArrayList<>();
        System.out.println(saleDTOReq.getNormalTickets());
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
                optNormalTicket = Optional.ofNullable(normalTicketRepository.findById(ticket.getId()).orElseThrow(() -> new Exception("Id de normal ticket invalido")));
                game = gameRepository.findById(optNormalTicket.get().getGame().getId());
                if (game.isPresent()) {
                    totalPrice += game.get().getPrice();
                }
            }
        }
        if (vipTickets.size() != 0) {
            for (VipTicket ticket : vipTickets) {
                optionalVipTicket = Optional.ofNullable(vipTicketRepository.findById(ticket.getId()).orElseThrow(() -> new Exception("Id de vip ticket invalido")));
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

    public ReportDTORes getReport(List<Sale> salesBD, String dayOrmonth) {

        double sumTotal = 0.0;
        long totalNormalTicketsSold = 0L;
        long totalVipTicketsSold = 0L;

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
        if (dayOrmonth.equals("day")) {
            return ReportDTORes.builder()
                    .totalAmountSaleDay(sumTotal)
                    .totalNormalTicketsSold(totalNormalTicketsSold)
                    .totalVipTicketsSold(totalVipTicketsSold)
                    .build();
        } else {
            return ReportDTORes.builder()
                    .totalAmountSaleMonthAndYear(sumTotal)
                    .totalNormalTicketsSold(totalNormalTicketsSold)
                    .totalVipTicketsSold(totalVipTicketsSold)
                    .build();
        }
    }

}
