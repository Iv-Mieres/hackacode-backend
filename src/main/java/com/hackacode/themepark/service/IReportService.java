package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.ReportGameDTORes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface IReportService {

    // Cantidad de entradas vendidas en todos los juegos en una fecha determinada
    ReportDTORes totalTicketsSoldOnAGivenDateOfTotalGames(LocalDate date);

    // Cantidad de entradas vendidas para un determinado juego, en una fecha particular
    ReportDTORes nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate(LocalDate date, String gameName);

    // Lista de empleados encargados de juegos con su juego asignado
    Page<EmployeeDTORes> getAllEmployeesWithTheirAssignedGame(Pageable pageable);

    //Comprador que más entradas compró en un determinado mes y año
    BuyerDTORes buyerWithTheMostTicketsSoldInTheMonth(int year, int month) throws Exception;

    // Juego con la mayor cantidad de entradas vendidas hasta el día en que se lleve a cabo la consulta
    ReportGameDTORes gameWithTheHighestNumberOfTicketsSoldSoFar(LocalDate date) throws Exception;

    // Número total de entradas vendidas más sus ingresos hasta la fecha
    ReportDTORes  totalNumberOfTicketsSoldPlusTheirRevenueToDate(LocalDate date);

    // Sumatoria total de los montos de ventas en un determinado día
    ReportDTORes sumTotalAmountOfAGivenDay(LocalDate date) throws Exception;

    //SUMA DE MONTO TOTAL DE UN DETERMINADO MES
    ReportDTORes sumTotalAmountOfAGivenMonth(int year, int month) throws Exception;


    // Historico -  Número total de entradas vendidas de cada juego hasta la fecha
    List<ReportGameDTORes> totalTicketsSoldForEachGameToDate(LocalDate date) throws Exception;
}
