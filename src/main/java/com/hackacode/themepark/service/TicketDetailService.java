package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.repository.ISaleRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
import com.hackacode.themepark.repository.ITicketRepository;
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
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketDetailService implements ITicketDetailService{


    private final ITicketDetailRepository repository;
    private final IBuyerRepository buyerRepository;
    private final ITicketRepository ticketRepository;
    private final ISaleRepository saleRepository;
    private final ModelMapper modelMapper;

    @Override
    public UUID saveTicket(TicketDetailDTOReq request) throws IdNotFoundException {
        if (!buyerRepository.existsById(request.getBuyer().getId())){
           throw  new IdNotFoundException("El comprador ingresado no se encuentra registrado");
        }
        if (!ticketRepository.existsById(request.getTicket().getId())){
            throw new IdNotFoundException("El ticket ingresado no se encuentra registrado");
        }
        var ticketDetail = modelMapper.map(request,TicketDetail.class);
        ticketDetail.setId(UUID.randomUUID());
        return repository.save(ticketDetail).getId();
    }

    @Override
    public Page<TicketDetailDTORes> getAll(Pageable pageable) {
        Page<TicketDetail> tickets = repository.findAll(pageable);
        List<TicketDetailDTORes> ticketsDTO = new ArrayList<>();
        for(TicketDetail ticket: tickets) {
            ticketsDTO.add(modelMapper.map(ticket, TicketDetailDTORes.class));
        }
        return new PageImpl<>(ticketsDTO, pageable, ticketsDTO.size());
    }

    @Override
    public TicketDetailDTORes getById(UUID id) throws IdNotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(
                ()-> new IdNotFoundException("El ticket con el id ingresado no se encuentra registado")),TicketDetailDTORes.class);
    }

    @Override
    public void delete(UUID id) throws IdNotFoundException {
        repository.deleteById(id);
    }

    @Override
    public String lastVisit(Long buyerId) {
        var ticketDetail = repository.findTopByBuyer_idOrderByBuyer_idDesc(buyerId);
        if (ticketDetail == null){
            return "Sin visitas";
        }
        else {
            return ticketDetail.getPurchaseDate().toLocalDate().toString();
        }
    }


}
