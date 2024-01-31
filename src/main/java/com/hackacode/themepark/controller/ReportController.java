package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.response.*;
import com.hackacode.themepark.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Controlador de Reportes")
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

    @Operation(
            summary = "Tickets vendidos por dia",
            description = "Trae la cantidad de entradas vendidas en todos los juegos en una fecha determinada"
    )
    @GetMapping("/entradas/totales_vendidas_en")
    public ResponseEntity<ReportDTORes> getTicketsSoldByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(reportService.totalTicketsSoldOnAGivenDateOfTotalGames(date));
    }

    @Operation(
            summary = "Entradas vendidas por fecha y juego",
            description = "Trae la cantidad de entradas vendidas para un determinado juego, en una fecha particular"
    )
    @GetMapping("/entradas/vendidas_por_fecha_y_juego")
    public ResponseEntity<ReportDTORes> getTicketsSoldOfOneGameByDateAndGameName(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, String gameName) {
        return ResponseEntity.ok(reportService.nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate(date, gameName));
    }

    @Operation(
            summary = "Ventas totales por dia",
            description = "Devuelve la sumatoria total de los montos de ventas en un determinado día"
    )
    @GetMapping("/ventas/totales_por_dia")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenDay(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.sumTotalAmountOfAGivenDay(date));
    }

    @Operation(
            summary = "Ventas totales por mes",
            description = "Devuelve la sumatoria total de los montos de ventas en un determinado mes y año"
    )
    @GetMapping("/ventas/totales_por_mes")
    public ResponseEntity<ReportDTORes> getTotalAmountOfAGivenMonthAndYear(@RequestParam int year, int month) throws Exception {
        return ResponseEntity.ok(reportService.sumTotalAmountOfAGivenMonth(year, month));
    }

    @Operation(
            summary = "Empleados con juegos asignados",
            description = "Devuelve la lista de empleados encargados de juegos con su juego asignado"
    )
    @GetMapping("/empleados/con_juego_asignado")
    public ResponseEntity<Page<EmployeeDTORes>> getAllEmployeesWhitGames(Pageable pageable) {
        return ResponseEntity.ok(reportService.getAllEmployeesWithTheirAssignedGame(pageable));
    }

    @Operation(
            summary = "comprador con mas entradas compradas en el mes",
            description = "Devuelve el comprador que más entradas compró en un determinado mes y año"
    )
    @GetMapping("/comprador/con_mas_entradas_vedidas_al_mes")
    public ResponseEntity<ReportDTORes> getBuyerWithMoreNormalTicketsPerMonthAndYear(@RequestParam int year, int month) throws Exception {
        return ResponseEntity.ok(reportService.buyerWithTheMostTicketsSoldInTheMonth(year, month));
    }

    @Operation(
            summary = "Juego con mas entradas vendidas",
            description = "Devuelve el Juego con la mayor cantidad de entradas vendidas hasta el día en que se lleve a cabo la consulta"
    )
    @GetMapping("/juego/con_mas_entradas_vendidas_hasta")
    public ResponseEntity<ReportGameDTORes> getGameWithTheMostTicketsSold(@RequestParam
                                                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                      LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.gameWithTheHighestNumberOfTicketsSoldSoFar(date));
    }

    @Operation(
            summary = "Historico de ventas de ticket y monto",
            description = "Devuelve el número total de entradas vendidas más sus ingresos hasta la fecha"
    )
    @GetMapping("/historico/ventas_monto")
    public ResponseEntity<ReportDTORes> totalNumberOfTicketsSoldPlusTheirRevenueToDate(@RequestParam
                                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                       LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.totalNumberOfTicketsSoldPlusTheirRevenueToDate(date));
    }

    @Operation(
            summary = "Historico de ventas de ticket por juego",
            description = "Devuelve el número total de entradas vendidas de cada juego hasta la fecha"
    )
    @GetMapping("/historico/juegos_monto")
    public ResponseEntity<List<ReportGameDTORes>> totalTicketsSoldForEachGameToDate(@RequestParam
                                                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                                LocalDate date) throws Exception {
        return ResponseEntity.ok(reportService.totalTicketsSoldForEachGameToDate(date));
    }

}
