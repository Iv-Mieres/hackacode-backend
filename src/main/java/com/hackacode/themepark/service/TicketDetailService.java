package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDetailDTOReq;
import com.hackacode.themepark.dto.response.TicketDetailDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
import com.hackacode.themepark.repository.ITicketRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketDetailService implements ITicketDetailService{


    private final ITicketDetailRepository repository;
    private final IBuyerRepository buyerRepository;
    private final ITicketRepository ticketRepository;
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
            var ticketDTO = modelMapper.map(ticketDetail, TicketDetailDTORes.class);
            ticketsDTO.add(ticketDTO);
        }
        return new PageImpl<>(ticketsDTO, pageable, ticketsDTO.size());
    }

    @Override
    public TicketDetailDTORes getById(UUID id) throws IdNotFoundException {
        var ticketDetail =  repository.findById(id).orElseThrow(
                ()-> new IdNotFoundException("El ticket con el id ingresado no se encuentra registado"));
        return modelMapper.map(ticketDetail, TicketDetailDTORes.class);
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
