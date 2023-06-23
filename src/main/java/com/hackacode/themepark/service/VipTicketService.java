package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.VipTicketDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.VipTicket;
import com.hackacode.themepark.repository.IVipTicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VipTicketService implements IVipTicketService {

    private final IVipTicketRepository ticketVipRepository;
    private final ModelMapper modelMapper;

    //CREA UN TICKET VIP
    @Override
    public UUID saveTicketVip(VipTicketDTOReq vipTicketDTOReq) {
        var vipTicket = modelMapper.map(vipTicketDTOReq, VipTicket.class);
        vipTicket.setPurchaseDate(LocalDateTime.now());
        ticketVipRepository.save(vipTicket);
        return ticketVipRepository.findByPurchaseDate(vipTicket.getPurchaseDate()).getId();
    }

    //MUESTRA UN TICKET VIP
    @Override
    public VipTicketDTORes getTicketVipById(UUID ticketVipId) throws IdNotFoundException {
        return modelMapper.map(ticketVipRepository.findById(ticketVipId)
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe")), VipTicketDTORes.class);
    }

    //LISTA DTO DE TICKETS VIP
    @Override
    public Page<VipTicketDTORes> getTicketVips(Pageable pageable) {
        var ticketsVipDb = ticketVipRepository.findAll();
        var ticketsVipDTO = new ArrayList<VipTicketDTORes>();
        for (VipTicket ticket: ticketsVipDb) {
            ticketsVipDTO.add(modelMapper.map(ticket, VipTicketDTORes.class));
        }
        return new PageImpl<>(ticketsVipDTO, pageable, ticketsVipDTO.size());
    }

    //MODIFICA UN TICKET VIP
    @Override
    public void updateTicketVip(VipTicketDTOReq vipTicketDTOReq) throws IdNotFoundException {
        if (ticketVipRepository.existsById(vipTicketDTOReq.getId())){
            throw new IdNotFoundException("El id ingresado no existe");
        }
        ticketVipRepository.save(modelMapper.map(vipTicketDTOReq, VipTicket.class));
    }

    //ELIMINA UN TICKET VIP
    @Override
    public void deleteTicketVip(UUID ticketVipId) {
        ticketVipRepository.deleteById(ticketVipId);
    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    @Override
    public BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        var vipTicketBD = ticketVipRepository.findTopByPurchaseDateBetweenOrderByBuyer_idDesc(start, end);
        if(vipTicketBD.getBuyer() == null){
            throw new Exception("La fecha ingresada no contiene ventas");
        }
        return modelMapper.map(vipTicketBD.getBuyer(), BuyerDTORes.class);
    }
}
