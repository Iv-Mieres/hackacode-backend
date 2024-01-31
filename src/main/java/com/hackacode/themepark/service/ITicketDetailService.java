package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITicketDetailService {


    UUID saveTicket(TicketDetailDTOReq ticket) throws IdNotFoundException;

    Page<TicketDetailDTORes> getAllTciketsDetails(Pageable pageable);

    TicketDetailDTORes getById(UUID id) throws IdNotFoundException;

    void delete(UUID id);

    String lastVisit(Long buyerId);


}
