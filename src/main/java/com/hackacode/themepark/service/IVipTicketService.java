package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.VipTicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.VipTicketDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.VipTicket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IVipTicketService {

    void saveTicketVip(VipTicketDTOReq vipTicketDTOReq);
    VipTicketDTORes getTicketVipById(UUID ticketVipId) throws IdNotFoundException;
    Page<VipTicketDTORes> getTicketVips(Pageable pageable);
    void updateTicketVip(VipTicketDTOReq vipTicketDTOReq) throws IdNotFoundException;
    void deleteTicketVip(UUID TicketVipId);

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception;
}
