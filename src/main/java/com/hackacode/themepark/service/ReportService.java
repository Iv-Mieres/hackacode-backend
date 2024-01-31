package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.dto.response.ReportGameDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Sale;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private ITicketDetailRepository ticketDetailRepository;
    @Autowired
    private ITicketRepository ticketRepository;
    @Autowired
    private IGameRepository gameRepository;
    @Autowired
    private IBuyerRepository buyerRepository;
    @Autowired
    private IEmployeeRepository employeeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ITicketDetailService ticketDetailService;


    // Cantidad de entradas vendidas en todos los juegos en una fecha determinada
    @Override
    public ReportDTORes totalTicketsSoldOnAGivenDateOfTotalGames(LocalDate date) {
        long totalTicketsSold = 0;
        var sales = saleRepository.findAll();

        if (sales.isEmpty()) {
            return ReportDTORes.builder().totalTicketsSold(totalTicketsSold).build();
        } else {
            for (Sale sale : sales) {
                if (sale.getPurchaseDate().toLocalDate().isEqual(date)) {
                    totalTicketsSold += sale.getTicketsDetail().size();
                }
            }
            return ReportDTORes.builder()
                    .totalTicketsSold(totalTicketsSold)
                    .build();
        }
    }

    // Cantidad de entradas vendidas para un determinado juego, en una fecha particular
    @Override
    public ReportDTORes nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate(LocalDate date, String gameName) {
        long totalTicketsSold = 0;
        var sales = saleRepository.findAll();
        String name = "";

        if (sales.isEmpty()) {
            return ReportDTORes.builder().totalTicketsSold(totalTicketsSold).build();
        }
        for (Sale sale : sales) {
            if (sale.getPurchaseDate().toLocalDate().isEqual(date)
                    && sale.getGame().getName().equalsIgnoreCase(gameName)) {
                totalTicketsSold += sale.getTicketsDetail().size();
                name = sale.getGame().getName();
            }
        }
        return ReportDTORes.builder()
                .totalTicketsSold(totalTicketsSold)
                .gameName(name)
                .build();
    }

    // Sumatoria total de los montos de ventas en un determinado día
    @Override
    public ReportDTORes sumTotalAmountOfAGivenDay(LocalDate date) throws Exception {
        if (date.isAfter(LocalDate.now())) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }

        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        List<Sale> sales = saleRepository.findAllByPurchaseDateBetween(start, end);
        return this.sumTotalAmountOfAGivenMonthOrDay(sales, "day");
    }

    //SUMA DE MONTO TOTAL DE UN DETERMINADO MES
    @Override
    public ReportDTORes sumTotalAmountOfAGivenMonth(int year, int month) throws Exception {
        if (year > LocalDate.now().getYear() || month > LocalDate.now().getMonthValue()) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = this.isLeapYearOrNot(year, month);

        List<Sale> salesBD = saleRepository.findAllByPurchaseDateBetween(start, end);
        return this.sumTotalAmountOfAGivenMonthOrDay(salesBD, "month");
    }

    //Genera la suma total de los montos de un mes o un dia determinado
    public ReportDTORes sumTotalAmountOfAGivenMonthOrDay(List<Sale> salesBD, String dayOrmonth) {
        double sumTotal = 0.0;

        //recorre la lista de ventas, suma el total de la ventas y el total de tickets vendidos
        for (Sale sale : salesBD) {
            sumTotal += sale.getTotalPrice();
        }
        return switch (dayOrmonth) {
            case "day" ->
                //crea y retorna un DTO con los datos del dia ingresado
                    ReportDTORes.builder()
                            .totalAmountSaleDay(sumTotal)
                            .build();
            case "month" ->
                //crea y retorna un DTO con los datos del año y mes ingesados
                    ReportDTORes.builder()
                            .totalAmountSaleMonthAndYear(sumTotal)
                            .build();
            default -> null;
        };
    }

    // Lista de empleados encargados de juegos con su juego asignado
    @Override
    public Page<EmployeeDTORes> getAllEmployeesWithTheirAssignedGame(Pageable pageable) {
        var employees = employeeRepository.findAllByIsEnable(true, pageable);
        var employeesDTO = new ArrayList<EmployeeDTORes>();

        for (Employee employee : employees) {
            if (employee.getGame() != null) employeesDTO.add(modelMapper.map(employee, EmployeeDTORes.class));
        }
        return new PageImpl<>(employeesDTO, pageable, employeesDTO.size());
    }

    //Comprador que más entradas compró en un determinado mes y año
    @Override
    public ReportDTORes buyerWithTheMostTicketsSoldInTheMonth(int year, int month) throws Exception {
        if (year > LocalDate.now().getYear() || month > LocalDate.now().getMonthValue()) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }

        String dni = "";
        long count = 0;
        var buyerDTO = new BuyerDTORes();
        var report = new ReportDTORes();
        report.setTotalTicketsSold(0L);

        Sort sort = Sort.by(Sort.Direction.ASC, "buyer");
        var ticketsDetail = ticketDetailRepository.findAll(sort);

        for (TicketDetail ticketDetail : ticketsDetail) {
            if (ticketDetail.getPurchaseDate().toLocalDate().getYear() == year
                    && ticketDetail.getPurchaseDate().toLocalDate().getMonthValue() == month) {
                //Si el dni del comprador coincide con el siguiente se hace la suma de tickets
                // y se guarda el comprador en el reportDTO
                if (dni.equals("") || ticketDetail.getBuyer().getDni().equals(dni)) {
                    dni =  ticketDetail.getBuyer().getDni();
                    buyerDTO = modelMapper.map(ticketDetail.getBuyer(), BuyerDTORes.class);
                    count += 1;
                } else {//si la cuenta de tickets es mayor a la del report se asignan los valores al report
                    if (count > report.getTotalTicketsSold()) {
                        report.setBuyer(buyerDTO);
                        report.setTotalTicketsSold(count);
                    }
                    //vuelve a hacer el recuento con los tickets vendidos y el nombre del siguiente comprador
                    count = 1;
                    dni = ticketDetail.getBuyer().getDni();
                }
            }
        }

        if (!ticketsDetail.isEmpty()) {//asigna los datos del ultimo comprador de la lista
            // si tiene mayor cantidad de tickets
            if (count > report.getTotalTicketsSold()) {
                report.setBuyer(buyerDTO);
                report.setTotalTicketsSold(count);
            }
        }
        if (report.getBuyer() == null) {
            return report;
        }
        report.getBuyer().setLastVisit(ticketDetailService.lastVisit(report.getBuyer().getId()));
        return report;
    }

    // Juego con la mayor cantidad de entradas vendidas hasta el día en que se lleve a cabo la consulta
    @Override
    public ReportGameDTORes gameWithTheHighestNumberOfTicketsSoldSoFar(LocalDate date) throws Exception {
        if (date.isAfter(LocalDate.now())) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }

        String name = "";
        long count = 0;
        var reportGame = new ReportGameDTORes();
        reportGame.setTotalTicketsSold(0L);

        Sort sort = Sort.by(Sort.Direction.ASC, "game");
        var sales = saleRepository.findAll(sort);

        for (Sale sale : sales) {
            if (sale.getPurchaseDate().toLocalDate().isBefore(date)
                    || sale.getPurchaseDate().toLocalDate().isEqual(date)) {
                //Si el nombre del juego de cada venta coincide se hace la suma de tickets
                // y se guarda el nombre del juego
                if (name.equals("") || sale.getGame().getName().equals(name)) {
                    name = sale.getGame().getName();
                    count += sale.getTicketsDetail().size();
                } else {//si la cuenta de tickets es mayor a la del reportGame se asignan las valores al report
                    if (count > reportGame.getTotalTicketsSold()) {
                        reportGame.setGame(name);
                        reportGame.setTotalTicketsSold(count);
                    }
                    //vuelve a hacer el recuento con los tickets vendidos y el nombre del siguiente juego
                    count = sale.getTicketsDetail().size();
                    name = sale.getGame().getName();
                }
            }
        }

        if (!sales.isEmpty()) {//asigna los datos del ultimo juego que contiene las ventas
            if (count > reportGame.getTotalTicketsSold()) {
                reportGame.setGame(name);
                reportGame.setTotalTicketsSold(count);
            }
        }
        return reportGame;
    }

    // EXTRAS

    // Historico - Número total de entradas vendidas más sus ingresos hasta la fecha
    @Override
    public ReportDTORes totalNumberOfTicketsSoldPlusTheirRevenueToDate(LocalDate date) throws Exception {
        if (date.isAfter(LocalDate.now())) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }

        LocalDateTime findDate = LocalDateTime.of(date, LocalTime.MAX);
        long totalTicketsSold = 0;
        double totalAmountMonthAndYear = 0.0;

        var sales = saleRepository.findAll();

        if (sales.isEmpty()) {
            return ReportDTORes.builder().totalTicketsSold(totalTicketsSold).build();
        } else {
            for (Sale sale : sales) {
                if (sale.getPurchaseDate().isBefore(findDate)
                        || sale.getPurchaseDate().toLocalDate().isEqual(date)) {
                    totalTicketsSold += sale.getTicketsDetail().size();
                    totalAmountMonthAndYear += sale.getTotalPrice();
                }
            }
            return ReportDTORes.builder()
                    .totalTicketsSold(totalTicketsSold)
                    .totalAmountSaleYear(totalAmountMonthAndYear)
                    .build();
        }
    }

    // Historico - Número total de entradas vendidas de cada juego hasta la fecha
    @Override
    public List<ReportGameDTORes> totalTicketsSoldForEachGameToDate(LocalDate date) throws Exception {
        if (date.isAfter(LocalDate.now())) {
            throw new Exception("La fecha máxima ingresada solo puede ser hasta " + LocalDate.now());
        }
        String name = "";
        long count = 0;
        var games = new ArrayList<ReportGameDTORes>();
        Sort sort = Sort.by(Sort.Direction.ASC, "game");
        var sales = saleRepository.findAll(sort);

        for (Sale sale1 : sales) {
            if (sale1.getPurchaseDate().toLocalDate().isBefore(date)
                    || sale1.getPurchaseDate().toLocalDate().isEqual(date)) {

                if (name.equals("") || sale1.getGame().getName().equals(name)) {
                    name = sale1.getGame().getName();
                    count += sale1.getTicketsDetail().size();
                } else {
                    //asigna el juego y sus tickets vendidos a la lista de juegos
                    var reportGame = new ReportGameDTORes(name, count);
                    games.add(reportGame);
                    //vuelve a hacer el recuento con los ticktes vendidos y el nombre del siguiente juego
                    count = sale1.getTicketsDetail().size();
                    name = sale1.getGame().getName();
                }
            }
        }

        if (!sales.isEmpty()) {//asigna los datos del ultimo juego que contiene las ventas
            var reportGame = new ReportGameDTORes(name, count);
            games.add(reportGame);
        }
        return games;
    }

    //Comprueba si el año ingresado es bisiesto o no
    public LocalDateTime isLeapYearOrNot(int year, int month) {
        LocalDateTime end;

        if (Year.of(year).isLeap() && Month.of(month).equals(Month.FEBRUARY)) {
            end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);
        } else if (!Year.of(year).isLeap() && Month.of(month).equals(Month.FEBRUARY)) {
            end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).minLength()), LocalTime.MAX);
        } else {
            end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);
        }
        return end;
    }


}



