package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ReportDTORes;
import com.hackacode.themepark.model.*;
import com.hackacode.themepark.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @InjectMocks
    private ReportService reportService;

    @Mock
    private IGameRepository gameRepository;
    @Mock
    private IBuyerRepository buyerRepository;
    @Mock
    private IEmployeeRepository employeeRepository;
    @Mock
    private ISaleRepository saleRepository;
    @Mock
    private ITicketDetailRepository ticketDetailRepository;
    @Mock
    private ITicketRepository ticketRepository;
    @Mock
    private ITicketDetailService ticketDetailService;
    @Mock
    private ModelMapper modelMapper;

    private Sale sale;
    private Sale sale2;


    @BeforeEach
    void setUp() {
        var game = new Game();
        game.setName("Montaña Rusa");

        this.sale = new Sale();
        this.sale.setId(1L);
        this.sale.setGame(game);
        this.sale.setTotalPrice(10000.0);
        this.sale.setPurchaseDate(LocalDateTime.now());
        this.sale.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));

        this.sale2 = new Sale();
        this.sale2.setGame(game);
        this.sale2.setTotalPrice(5000.0);
        this.sale2.setId(2L);
        this.sale2.setPurchaseDate(LocalDateTime.now());
        this.sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail()));
    }

    @DisplayName("comprueba la cantidad de tickets vendidos de todo los juegos")
    @Test
    void totalTicketsSoldOnAGivenDateOfTotalGames() {
        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        when(saleRepository.findAll()).thenReturn(sales);
        var reportDTO = reportService.totalTicketsSoldOnAGivenDateOfTotalGames(LocalDate.now());

        assertEquals(5, reportDTO.getTotalTicketsSold());
    }

    @Test
    void nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate() {
        LocalDate date = LocalDate.now();
        String gameName = "Montaña Rusa";

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        when(saleRepository.findAll()).thenReturn(sales);
        var reportDTORes= reportService.nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate(date, gameName);
        assertEquals(5, reportDTORes.getTotalTicketsSold());
        assertEquals(gameName, reportDTORes.getGameName());
    }

    @Test
    void sumTotalAmountOfAGivenDay() throws Exception {
        LocalDate date = LocalDate.now();
        LocalDateTime start = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        var expected = this.sale.getTotalPrice() + this.sale2.getTotalPrice();

        when(saleRepository.findAllByPurchaseDateBetween(start, end)).thenReturn(sales);
        var reportDTORes =  reportService.sumTotalAmountOfAGivenDay(date);
        assertEquals(expected, reportDTORes.getTotalAmountSaleDay());
    }

    @Test
    void sumTotalAmountOfAGivenMonth() throws Exception {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        var expected = this.sale.getTotalPrice() + this.sale2.getTotalPrice();

        when(saleRepository.findAllByPurchaseDateBetween(start, end)).thenReturn(sales);
        var reportDTORes =  reportService.sumTotalAmountOfAGivenMonth(year, month);
        assertEquals(expected, reportDTORes.getTotalAmountSaleMonthAndYear());
    }

    @Test
    void getAllEmployeesWithTheirAssignedGame(){
        int page = 0;
        int size = 2;
        var employee1 = new Employee();
        employee1.setId(1L);
        employee1.setEnable(true);
        employee1.setGame(new Game());
        var employee2 = new Employee();
        employee2.setId(2L);
        employee2.setEnable(true);
        employee2.setGame(new Game());

        var employeeDTO = new EmployeeDTORes();
        employeeDTO.setEnable(true);
        employeeDTO.setId(1L);
        employeeDTO.setGame(new GameDTORes());

        var employees = new ArrayList<Employee>();
        employees.add(employee1);
        employees.add(employee2);

        var pageable = PageRequest.of(page, size);
        when(employeeRepository.findAllByIsEnable(true, pageable))
                .thenReturn(new PageImpl<>(employees, pageable, employees.size()));
        when(modelMapper.map(employee1, EmployeeDTORes.class)).thenReturn(employeeDTO);
        var currentEmployeeDTORes = reportService.getAllEmployeesWithTheirAssignedGame(pageable);

        assertEquals(2, currentEmployeeDTORes.getTotalElements());
        assertEquals(0, currentEmployeeDTORes.getNumber());
        assertEquals(1, currentEmployeeDTORes.getTotalPages());

        verify(employeeRepository).findAllByIsEnable(true, pageable);

    }

    @Test
    void gameWithTheHighestNumberOfTicketsSoldSoFar() throws Exception {
        var game1 = new Game();
        game1.setId(2L);
        game1.setName("Zamba");

        this.sale.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        this.sale2.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        this.sale2.setGame(game1);

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        Sort sort = Sort.by(Sort.Direction.ASC, "game");
        when(saleRepository.findAll(sort)).thenReturn(sales);
        var cuerrentReportDTORes = reportService.gameWithTheHighestNumberOfTicketsSoldSoFar(LocalDate.of(1988,6,6));
        assertEquals(3, cuerrentReportDTORes.getTotalTicketsSold());
        assertEquals(this.sale.getGame().getName(), cuerrentReportDTORes.getGame());
    }


    @Test
    void totalNumberOfTicketsSoldPlusTheirRevenueToDate() throws Exception {
        this.sale.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        when(saleRepository.findAll()).thenReturn(sales);
        var currentReportDTORes = reportService.totalNumberOfTicketsSoldPlusTheirRevenueToDate(LocalDate.now());
        assertEquals(15000, currentReportDTORes.getTotalAmountSaleYear());
        assertEquals(5, currentReportDTORes.getTotalTicketsSold());
    }


    @Test
    void totalTicketsSoldForEachGameToDate() throws Exception {
        this.sale.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        this.sale2.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        Sort sort = Sort.by(Sort.Direction.ASC, "game");
        when(saleRepository.findAll(sort)).thenReturn(sales);

        var currentReportDTORes = reportService.totalTicketsSoldForEachGameToDate(LocalDate.of(1988,6,6));
        assertEquals(5, currentReportDTORes.get(0).getTotalTicketsSold());
    }

    @Test
    void totalTicketsSoldForEachGameToDateIfThereAreTwoGames() throws Exception {
        var game2 = new Game();
        game2.setId(2L);
        game2.setName("Zamba");

        this.sale.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        this.sale2.setGame(game2);
        this.sale2.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));

        var sales = new ArrayList<Sale>();
        sales.add(this.sale);
        sales.add(this.sale2);

        Sort sort = Sort.by(Sort.Direction.ASC, "game");
        when(saleRepository.findAll(sort)).thenReturn(sales);

        var currentReportDTORes = reportService.totalTicketsSoldForEachGameToDate(LocalDate.of(1988,6,6));

        assertEquals(2, currentReportDTORes.size());
        //compara datos del primer juego de la lista
        assertEquals(3, currentReportDTORes.get(0).getTotalTicketsSold());
        assertEquals(this.sale.getGame().getName(), currentReportDTORes.get(0).getGame());
        //compara datos del segundo juego de la lista
        assertEquals(2, currentReportDTORes.get(1).getTotalTicketsSold());
        assertEquals(game2.getName(), currentReportDTORes.get(1).getGame());
    }

    @Test
    void isLeapYear() {
        int year = 2020;
        int month = 2;
        var expected = LocalDateTime.of(LocalDate.of(year, month, 29), LocalTime.MAX);

        var isLeap = reportService.isLeapYearOrNot(year, month);
        assertEquals(expected, isLeap);
    }

    @Test
    void notALeapYear() {
        int year = 2019;
        int month = 2;
        var expected = LocalDateTime.of(LocalDate.of(year, month, 28 ), LocalTime.MAX);

        var notALeap = reportService.isLeapYearOrNot(year, month);
        assertEquals(expected, notALeap);
    }

    @Test
    void ifNotFebruary() {
        int year = 2019;
        int month = 5;
        var expected = LocalDateTime.of(LocalDate.of(year, month,  Month.of(month).maxLength()), LocalTime.MAX);

        var notALeap = reportService.isLeapYearOrNot(year, month);
        assertEquals(expected, notALeap);
    }

    @DisplayName("comprueba que devuelva un comprador vacio si la entrada detallada es null")
    @Test
    void ifTicketDetailIsNullReturnAnBuyerEmpty() throws Exception {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        Sort sort = Sort.by(Sort.Direction.ASC, "buyer");
        var ticketsDetail = new ArrayList<TicketDetail>();
        var expected = new ReportDTORes();
        expected.setBuyer(new BuyerDTORes());

        when(ticketDetailRepository.findAll(sort)).thenReturn(ticketsDetail);
        var currenteResult = reportService.buyerWithTheMostTicketsSoldInTheMonth(year, month);
        assertEquals(0, currenteResult.getTotalTicketsSold());
        assertEquals(null, currenteResult.getBuyer());
        verify(ticketDetailRepository).findAll(sort);
    }
}