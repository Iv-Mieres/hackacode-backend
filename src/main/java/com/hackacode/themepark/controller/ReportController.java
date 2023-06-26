package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/informes")
public class ReportController {

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private IGameService gameService;

    @Autowired
    private ISaleService saleService;

    @Autowired
    private ICustomUserService userService;


    //VER CANTIDAD DE ENTRADAS VENDIDAS DE TODOS LOS JUEGOS POR UNA FECHA DETERMINADA
    @GetMapping("/entradas/totales_vendidas_en")
    public ResponseEntity<ReportDTORes> getTicketsSoldByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ticketService.totalNormalTicketsSoldOnAGivenDate(date));
    }

    //VER CANTIDAD DE ENTRADAS VENDIDAS DE UN JUEGO POR UNA FECHA DETERMINADA
    @GetMapping("/entradas/vendidas_por_fecha_y_juego")
    public ResponseEntity<ReportDTORes> getTicketsSoldOfOneGameByDateAndGameName(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, String gameName) {
        return ResponseEntity.ok(ticketService.totalNormalTicketsSoldOfOneGameOnAGivenDate(date, gameName));
    }

    //MONTO TOTAL DE VENTAS POR UN DETERMINADO DÍA
    @GetMapping("/ventas/totales_por_dia")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(saleService.sumTotalAmountOfAGivenDay(date));
    }

    //MONTO TOTAL DE VENTAS POR UN DETERMINADO MES Y AÑO
    @GetMapping("/ventas/totales_por_mes")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenMonthAndYear(@RequestParam int year, int month) {
        return ResponseEntity.ok(saleService.sumTotalAmountOfAGivenMonth(year, month));
    }

    //LISTA DE EMPLEADOS CON SU RESPECTIVO ROLE Y JUEGO ASIGNADO
    @GetMapping("/empleados/con_role/{role}")
    public ResponseEntity<Page<UserDTORes>> getAllEmployeesByRole(Pageable pageable, @RequestParam String role) throws Exception {
        return ResponseEntity.ok(userService.getAllUsersPerRole(pageable, role));
    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS NORMALES COMPRÓ EN UN DETERMINADO MES Y AÑO
    @GetMapping("/comprador_normal/con_mas_entradas_vedidas_al_mes")
    public ResponseEntity<BuyerDTORes> getBuyerWithMoreNormalTicketsPerMonthAndYear(@RequestParam int year, int month) throws Exception {
        return ResponseEntity.ok(ticketService.buyerWithTheMostNormalTicketsSoldInTheMonth(year, month));
    }

    //MUESTRA AL COMPRADOR QUE MÁS ENTRADAS VIPS COMPRÓ EN UN DETERMINADO MES Y AÑO
//    @GetMapping("/comprador_vip/con_mas_entradas_vedidas_al_mes")
//    public ResponseEntity<BuyerDTORes> getBuyerWithMoreVipTicketsPerMonthAndYear(@RequestParam int year, int month) throws Exception {
//        return ResponseEntity.ok(vipTicketService.buyerWithTheMostNormalTicketsSoldInTheMonth(year, month));
//    }

    //VER JUEGO CON LA MAYOR CANTIDAD DE ENTRADAS VENDIDAS HASTA LA FECHA SELECCIONADA
    @GetMapping("/juego/con_mas_entradas_vendidas_hasta")
    public ResponseEntity<ReportDTORes> getGameWithTheMostTicketsSold(@RequestParam
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date) throws Exception {
        return ResponseEntity.ok(gameService.gameWithTheMostTicketsSold(date));
    }


}
