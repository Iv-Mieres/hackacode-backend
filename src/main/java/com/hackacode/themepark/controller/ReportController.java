package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.response.*;
import com.hackacode.themepark.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/informes")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private IGameService gameService;

    @Autowired
    private ISaleService saleService;

    @Autowired
    private ICustomUserService userService;


    // Cantidad de entradas vendidas en todos los juegos en una fecha determinada
    @GetMapping("/entradas/totales_vendidas_en")
    public ResponseEntity<ReportDTORes> getTicketsSoldByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.totalTicketsSoldOnAGivenDateOfTotalGames(date));
    }

    // Cantidad de entradas vendidas para un determinado juego, en una fecha particular
    @GetMapping("/entradas/vendidas_por_fecha_y_juego")
    public ResponseEntity<ReportDTORes> getTicketsSoldOfOneGameByDateAndGameName(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, String gameName) {
        return ResponseEntity.ok(reportService.nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate(date, gameName));
    }

    // Sumatoria total de los montos de ventas en un determinado día
    @GetMapping("/ventas/totales_por_dia")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.sumTotalAmountOfAGivenDay(date));
    }

    // Sumatoria total de los montos de ventas en un determinado mes y año
    @GetMapping("/ventas/totales_por_mes")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenMonthAndYear(@RequestParam int year, int month) throws Exception {
        return ResponseEntity.ok(reportService.sumTotalAmountOfAGivenMonth(year, month));
    }

    //Lista de empleados encargados de juegos con su juego asignado
    @GetMapping("/empleados/con_juego_asignado")
    public ResponseEntity<Page<EmployeeDTORes>> getAllEmployeesWhitGames(Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllEmployeesWithTheirAssignedGame(pageable));
    }

    //Comprador que más entradas compró en un determinado mes y año
    @GetMapping("/comprador/con_mas_entradas_vedidas_al_mes")
    public ResponseEntity<Map<String,Object>> getBuyerWithMoreNormalTicketsPerMonthAndYear(@RequestParam int year, int month) throws Exception {
        return ResponseEntity.ok(reportService.buyerWithTheMostTicketsSoldInTheMonth(year, month));
    }

    //Juego con la mayor cantidad de entradas vendidas hasta el día en que se lleve a cabo la consulta
    @GetMapping("/juego/con_mas_entradas_vendidas_hasta")
    public ResponseEntity<ReportGameDTORes> getGameWithTheMostTicketsSold(@RequestParam
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                      LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.gameWithTheHighestNumberOfTicketsSoldSoFar(date));
    }

    // Historico - Número total de entradas vendidas más sus ingresos hasta la fecha
    @GetMapping("/historico/ventas_monto")
    public ResponseEntity<ReportDTORes> totalNumberOfTicketsSoldPlusTheirRevenueToDate(@RequestParam
                                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                       LocalDate date) {
        return ResponseEntity.ok(reportService.totalNumberOfTicketsSoldPlusTheirRevenueToDate(date));
    }

    // Historico - Número total de entradas vendidas de cada juego hasta la fecha
    @GetMapping("/historico/juegos_monto")
    public ResponseEntity<List<ReportGameDTORes>> totalTicketsSoldForEachGameToDate(@RequestParam
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.totalTicketsSoldForEachGameToDate(date));
    }

}
