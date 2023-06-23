package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.NormalTicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.NormalTicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.repository.IGameRepository;
import com.hackacode.themepark.repository.INormalTicketRepository;
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
public class NormalTicketService implements INormalTicketService {

    private final IGameRepository gameRepository;

    private final INormalTicketRepository ticketRepository;

    private final IBuyerRepository buyerRepository;

    private final ModelMapper modelMapper;

    //CREA UN TICKET NORMAL
    @Override
    public UUID saveNormalTicket(NormalTicketDTOReq ticket) throws Exception {
        var gameBD = gameRepository.findById(ticket.getGame().getId())
                .orElseThrow(() -> new IdNotFoundException("El juego ingresado no existe"));

        var buyerBD = buyerRepository.findById(ticket.getBuyer().getId())
                .orElseThrow(() -> new IdNotFoundException("El comprador ingresado no existe"));

        if (!gameBD.validateAge(buyerBD.getBirthdate())){
            throw new Exception("Acción cancelada : No poseé la edad requerida para ingresar a este juego");
        }
        //settea el purchaseDate con LocalDateTime.now()
        var normalTicket = modelMapper.map(ticket, NormalTicket.class);
        normalTicket.setPurchaseDate(LocalDateTime.now());
        //Guarda el NormalTicket
        ticketRepository.save(normalTicket);
        //Busca el ticket guardado usando el normalTicket.getPurchaseDate() y retorno el UUID
        return ticketRepository.findByPurchaseDate(normalTicket.getPurchaseDate()).getId();
    }

    //MUESTRA UN TICKET POR ID
    @Override
    public NormalTicketDTORes getNormalTicketById(UUID ticketId) throws IdNotFoundException {
        return modelMapper.map(ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe")), NormalTicketDTORes.class);
    }

    //LISTA DTO DE TICKETS
    @Override
    public Page<NormalTicketDTORes> getNormalTickets(Pageable pageable) {
        var normalTicketsDb = ticketRepository.findAll(pageable);
        var normalTicketsDTO = new ArrayList<NormalTicketDTORes>();
        for(NormalTicket ticket: normalTicketsDb) {
            normalTicketsDTO.add(modelMapper.map(ticket, NormalTicketDTORes.class));
        }
        return new PageImpl<>(normalTicketsDTO, pageable, normalTicketsDTO.size());
    }

    //MODIFICA UN TICKET
    @Override
    public void updateNormalTicket(NormalTicketDTOReq ticketDTOReq) throws IdNotFoundException {
        if (ticketRepository.existsById(ticketDTOReq.getId())){
            throw new IdNotFoundException("El id ingresado no existe");
        }
    ticketRepository.save(modelMapper.map(ticketDTOReq, NormalTicket.class));
    }

    //ELIMINA UN TICKET
    @Override
    public void deleteNormalTicket(UUID ticketId) {
        ticketRepository.deleteById(ticketId);
    }


    // CANTIDAD DE ENTRADAS VENDIDAS DE TODOS LOS JUEGOS EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalNormalTicketsSoldOnAGivenDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalnormalTickets = ticketRepository.countByPurchaseDateBetween(start, end);
        var reportDTORes = ReportDTORes.builder().totalNormalTicketsSold(totalnormalTickets).build();

        return reportDTORes;
    }

    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
    @Override
    public ReportDTORes totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName){
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        Long totalNormalTickets = ticketRepository.countByPurchaseDateBetweenAndGame_name(start, end, gameName);
        var reportDTOres = ReportDTORes.builder()
                .totalNormalTicketsSold(totalNormalTickets)
                .gameName(gameName)
                .build();

        return reportDTOres;
    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
    @Override
    public BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception {
        LocalDateTime start = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        var normaTicketBD = ticketRepository.findTopByPurchaseDateBetweenOrderByBuyer_idDesc(start, end);
        if(normaTicketBD.getBuyer() == null){
            throw new Exception("La fecha ingresada no contiene ventas");
        }
        return modelMapper.map(normaTicketBD.getBuyer(), BuyerDTORes.class);
    }

}
