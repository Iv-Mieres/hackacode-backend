package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.model.*;
import com.hackacode.themepark.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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


    @DisplayName("comprueba la cantidad de tickets vendidos de todo los juegos")
    @Test
    void totalTicketsSoldOnAGivenDateOfTotalGames() {


        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setPurchaseDate(LocalDateTime.now());
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));
        var sale2 = new Sale();
        sale2.setId(2L);
        sale2.setPurchaseDate(LocalDateTime.now());
        sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail()));

        var sales = new ArrayList<Sale>();
        sales.add(sale1);
        sales.add(sale2);

        when(saleRepository.findAll()).thenReturn(sales);
        var reportDTO = reportService.totalTicketsSoldOnAGivenDateOfTotalGames(LocalDate.now());

        assertEquals(5, reportDTO.getTotalTicketsSold());

    }

    @Test
    void nameOfTheGameWithTheHighestNumberOfTicketsSoldOnASpecificDate() {
        LocalDate date = LocalDate.now();
        String gameName = "Montaña Rusa";
        var game = new Game();
        game.setName(gameName);

        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setGame(game);
        sale1.setPurchaseDate(LocalDateTime.now());
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));
        var sale2 = new Sale();
        sale2.setId(2L);
        sale2.setGame(game);
        sale2.setPurchaseDate(LocalDateTime.now());
        sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail()));

        var sales = new ArrayList<Sale>();
        sales.add(sale1);
        sales.add(sale2);

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

        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setTotalPrice(10000.0);
        sale1.setPurchaseDate(LocalDateTime.now());
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));
        var sale2 = new Sale();
        sale2.setId(2L);
        sale1.setTotalPrice(5000.0);
        sale2.setPurchaseDate(LocalDateTime.now());
        sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail()));

        var sales = new ArrayList<Sale>();
        sales.add(sale1);
        sales.add(sale2);

        var expected = sale1.getTotalPrice() + sale2.getTotalPrice();

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


        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setTotalPrice(10000.0);
        sale1.setPurchaseDate(LocalDateTime.now());
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));
        var sale2 = new Sale();
        sale2.setId(2L);
        sale1.setTotalPrice(5000.0);
        sale2.setPurchaseDate(LocalDateTime.now());
        sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail()));

        var sales = new ArrayList<Sale>();
        sales.add(sale1);
        sales.add(sale2);

        var expected = sale1.getTotalPrice() + sale2.getTotalPrice();

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
    void buyerWithTheMostTicketsSoldInTheMonth() throws Exception {

        var buyer = new Buyer();
        buyer.setId(1L);
        buyer.setBanned(false);
        buyer.setBirthdate(LocalDate.of(1984, 4,20));

        var ticketDetail = new TicketDetail();
        ticketDetail.setBuyer(buyer);
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();

        var expectedBuyerDTO = new BuyerDTORes();
        expectedBuyerDTO.setId(1L);
        expectedBuyerDTO.setBanned(false);
        expectedBuyerDTO.setBirthdate(LocalDate.of(1984, 4,20));


        LocalDateTime start = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(year, month, Month.of(month).maxLength()), LocalTime.MAX);

        when(modelMapper.map(ticketDetail.getBuyer(), BuyerDTORes.class)).thenReturn(expectedBuyerDTO);
        when(ticketDetailService.lastVisit(buyer.getId())).thenReturn("39");
        when(ticketDetailRepository.findTopByPurchaseDateBetweenOrderByBuyer_IdDesc(start, end))
                .thenReturn(ticketDetail);
        var currentBuyerDTORes =  reportService.buyerWithTheMostTicketsSoldInTheMonth(year, month);

        assertEquals(expectedBuyerDTO, currentBuyerDTORes);

    }

    @Test
    void gameWithTheHighestNumberOfTicketsSoldSoFar() throws Exception {
        var game1 = new Game();
        game1.setId(1L);
        game1.setName("Montaña Rusa");

        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setTotalPrice(10000.0);
        sale1.setGame(game1);
        sale1.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));

        var sales = new ArrayList<Sale>();
        sales.add(sale1);

        when(saleRepository.findAll()).thenReturn(sales);
        when(saleRepository.findAllByGame_id(game1.getId())).thenReturn(sales);
        var cuerrentReportDTORes = reportService.gameWithTheHighestNumberOfTicketsSoldSoFar(LocalDate.of(1988,6,6));
        assertEquals(3, cuerrentReportDTORes.getTotalTicketsSold());
        assertEquals(game1.getName(), cuerrentReportDTORes.getGameName());
    }


    @Test
    void totalNumberOfTicketsSoldPlusTheirRevenueToDate(){
        var sale1 = new Sale();
        sale1.setId(1L);
        sale1.setTotalPrice(10000.0);
        sale1.setPurchaseDate(LocalDateTime.of(LocalDate.of(1988,6,6), LocalTime.now()));
        sale1.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));


        var sale2 = new Sale();
        sale2.setId(2L);
        sale2.setTotalPrice(10000.0);
        sale2.setPurchaseDate(LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        sale2.setTicketsDetail(List.of(new TicketDetail(), new TicketDetail(), new TicketDetail()));
        var sales = new ArrayList<Sale>();
        sales.add(sale1);
        sales.add(sale2);

        when(saleRepository.findAll()).thenReturn(sales);
        var currentReportDTORes = reportService.totalNumberOfTicketsSoldPlusTheirRevenueToDate(LocalDate.now());
        assertEquals(20000, currentReportDTORes.getTotalAmountSaleYear());
        assertEquals(6, currentReportDTORes.getTotalTicketsSold());
    }







}