package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.TicketDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Ticket;
import com.hackacode.themepark.repository.IBuyerRepository;
import com.hackacode.themepark.repository.IGameRepository;
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
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final IGameRepository gameRepository;

    private final ITicketRepository ticketRepository;

    private final IBuyerRepository buyerRepository;

    private final ModelMapper modelMapper;

    //CREA UN TICKET NORMAL
//    @Override
//    public UUID saveNormalTicket(TicketDTOReq ticket) throws Exception {
//        var gameBD = gameRepository.findById(ticket.getGame().getId())
//                .orElseThrow(() -> new IdNotFoundException("El juego ingresado no existe"));
//
//        var buyerBD = buyerRepository.findById(ticket.getBuyer().getId())
//                .orElseThrow(() -> new IdNotFoundException("El comprador ingresado no existe"));
//
//        if (!gameBD.validateAge(buyerBD.getBirthdate())){
//            throw new Exception("Acción cancelada : No poseé la edad requerida para ingresar a este juego");
//        }
//        //settea el purchaseDate con LocalDateTime.now()
//        var normalTicket = modelMapper.map(ticket, Ticket.class);
//        normalTicket.setPurchaseDate(LocalDateTime.now());
//        normalTicket.setPrice(gameBD.getPrice());
//        //Guarda el NormalTicket
//        ticketRepository.save(normalTicket);
//        //Busca el ticket guardado usando el normalTicket.getPurchaseDate() y retorno el UUID
//        return ticketRepository.findByPurchaseDate(normalTicket.getPurchaseDate()).getId();
//    }
//
//    //MUESTRA UN TICKET POR ID
//    @Override
//    public TicketDTORes getNormalTicketById(UUID ticketId) throws IdNotFoundException {
//        return modelMapper.map(ticketRepository.findById(ticketId)
//                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe")), TicketDTORes.class);
//    }
//
//    //LISTA DTO DE TICKETS
//    @Override
//    public Page<TicketDTORes> getNormalTickets(Pageable pageable) {
//        var normalTicketsDb = ticketRepository.findAll(pageable);
//        var normalTicketsDTO = new ArrayList<TicketDTORes>();
//        for(Ticket ticket: normalTicketsDb) {
//            normalTicketsDTO.add(modelMapper.map(ticket, TicketDTORes.class));
//        }
//        return new PageImpl<>(normalTicketsDTO, pageable, normalTicketsDTO.size());
//    }
//
//    //MODIFICA UN TICKET
//    @Override
//    public void updateNormalTicket(TicketDTOReq ticketDTOReq) throws IdNotFoundException {
//        var normalTicketBD = ticketRepository.findById(ticketDTOReq.getId())
//                .orElseThrow(() -> new IdNotFoundException("El id ingresado no existe"));
//
//        var normalTicket = modelMapper.map(ticketDTOReq, Ticket.class);
//        normalTicket.setPrice(normalTicketBD.getPrice());
//
//        ticketRepository.save(normalTicket);
//    }
//
//    //ELIMINA UN TICKET
//    @Override
//    public void deleteNormalTicket(UUID ticketId) {
//        ticketRepository.deleteById(ticketId);
//    }
//
//
//    // CANTIDAD DE ENTRADAS VENDIDAS DE TODOS LOS JUEGOS EN UNA FECHA DETERMINADA
//    @Override
//    public ReportDTORes totalNormalTicketsSoldOnAGivenDate(LocalDate date) {
//        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
//
//        Long totalnormalTickets = ticketRepository.countByPurchaseDateBetween(start, end);
//        var reportDTORes = ReportDTORes.builder().totalNormalTicketsSold(totalnormalTickets).build();
//
//        return reportDTORes;
//    }
//
//    // CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO EN UNA FECHA DETERMINADA
//    @Override
//    public ReportDTORes totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate date, String gameName){
//        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
//
//        Long totalNormalTickets = ticketRepository.countByPurchaseDateBetweenAndGame_name(start, end, gameName);
//        var reportDTOres = ReportDTORes.builder()
//                .totalNormalTicketsSold(totalNormalTickets)
//                .gameName(gameName)
//                .build();
//
//        return reportDTOres;
//    }
//
//    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES
//    @Override
//    public BuyerDTORes buyerWithTheMostNormalTicketsSoldInTheMonth(int year, int month) throws Exception {
//        LocalDateTime start = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MIN);
//        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);
//
//        var normaTicketBD = ticketRepository.findTopByPurchaseDateBetweenOrderByBuyer_idDesc(start, end);
//        if(normaTicketBD.getBuyer() == null){
//            throw new Exception("La fecha ingresada no contiene ventas");
//        }
//        return modelMapper.map(normaTicketBD.getBuyer(), BuyerDTORes.class);
//    }
//
//    @Override
//    public String lastVisit(Long buyerId){
//
//      var normalTicket = ticketRepository.findTopByBuyer_idOrderByBuyer_idDesc(buyerId);
//      if (normalTicket == null){
//          return "Sin visitas";
//      }
//      else {
//         return normalTicket.getPurchaseDate().toLocalDate().toString();
//      }
//    }

}
