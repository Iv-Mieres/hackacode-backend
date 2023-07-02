package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.TicketDTORes;
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
        return repository.save(modelMapper.map(request,TicketDetail.class)).getId();
    }

    @Override
    public Page<TicketDetailDTORes> getAllTciketsDetails(Pageable pageable) {
        var ticketsDetails = repository.findAll(pageable);
        var ticketsDTO = new ArrayList<TicketDetailDTORes>();
        for(TicketDetail ticketDetail: ticketsDetails) {
            var ticketDTO = new TicketDetailDTORes();
            ticketDTO.setId(ticketDetail.getId());
            ticketDTO.setPurchaseDate(ticketDetail.getPurchaseDate());
            ticketDTO.setBuyer( modelMapper.map(ticketDetail.getBuyer(), BuyerDTORes.class));
            ticketDTO.setTicket( modelMapper.map(ticketDetail.getTicket(), TicketDTORes.class));
            ticketsDTO.add(ticketDTO);
        }
        return new PageImpl<>(ticketsDTO, pageable, ticketsDTO.size());
    }

    @Override
    public TicketDetailDTORes getById(UUID id) throws IdNotFoundException {
        var ticketDetail =  repository.findById(id).orElseThrow(
                ()-> new IdNotFoundException("El ticket con el id ingresado no se encuentra registado"));
        var ticketDetailDTO = new TicketDetailDTORes();
        ticketDetailDTO.setId(ticketDetail.getId());
        ticketDetailDTO.setPurchaseDate(ticketDetail.getPurchaseDate());
        ticketDetailDTO.setBuyer( modelMapper.map(ticketDetail.getBuyer(), BuyerDTORes.class));
        ticketDetailDTO.setTicket( modelMapper.map(ticketDetail.getTicket(), TicketDTORes.class));
        return ticketDetailDTO;
    }

    @Override
    public void delete(UUID id){
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
