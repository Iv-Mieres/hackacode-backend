package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.model.TicketDetail;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private IBuyerRepository buyerRepository;

    @Mock
    private ITicketDetailService ticketDetailService;

    @Mock
    private IWordsConverter wordsConverter;

    @Mock
    private ModelMapper modelMapper;

    private BuyerDTOReq buyerDTOReq;

    @Mock
    private BuyerDTORes buyerDTORes;

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        this.buyer = new Buyer(1L, "40948585", "Diego", "Martinez",
                LocalDate.of(1988,06,14), false);

        this.buyerDTOReq = new BuyerDTOReq();
        buyerDTOReq.setId(1L);
        buyerDTOReq.setName("diego");
        buyerDTOReq.setSurname("martinez");
        buyerDTOReq.setBirthdate(LocalDate.of(1988,06,14));
        buyerDTOReq.setDni("40948585");

        this.buyerDTORes = new BuyerDTORes();
        buyerDTORes.setId(1L);
        buyerDTORes.setName("Diego");
        buyerDTORes.setSurname("Martinez");
        buyerDTORes.setBirthdate(LocalDate.of(1988,06,14));
        buyerDTORes.setDni("40948585");
        buyerDTORes.setAge(Period.between(buyerDTORes.getBirthdate(), LocalDate.now()).getYears());
        buyerDTORes.setLastVisit("2023-06-29");
        buyerDTORes.setTicketDetail(new TicketDetail());

    }

    @DisplayName("Comprueba que se guarde un comprador")
    @Test
    void saveBuyerIfDniDoesNotExistInTheDataBase() throws Exception {
        when(buyerRepository.existsByDni(this.buyerDTOReq.getDni())).thenReturn(false);
        when(wordsConverter.capitalizeWords(this.buyerDTOReq.getName())).thenReturn("Diego");
        when(modelMapper.map(this.buyerDTOReq, Buyer.class)).thenReturn(this.buyer);
        buyerService.saveBuyer(this.buyerDTOReq);
        verify(buyerRepository).save(this.buyer);
    }

    @DisplayName("Si al hacer el guardado el dni del comprador ya está registrado, lanza una excepción")
    @Test
    void throwAnExceptionIfTheDniExistsWhenSavingTheBuyer() throws Exception {
        when(buyerRepository.existsByDni(this.buyerDTOReq.getDni())).thenReturn(true);
        assertThrows(Exception.class, () -> buyerService.saveBuyer(this.buyerDTOReq));

    }
    @DisplayName("Comprueba que se edite un comprador")
    @Test
    void updateBuyerIfDniDoesNotExistInTheDataBase() throws Exception {
        when(buyerRepository.findById(this.buyerDTOReq.getId())).thenReturn(Optional.ofNullable(this.buyer));
        when(wordsConverter.capitalizeWords(this.buyerDTOReq.getName())).thenReturn("Diego");
        when(modelMapper.map(this.buyerDTOReq, Buyer.class)).thenReturn(this.buyer);
        buyerService.updateBuyer(this.buyerDTOReq);
        verify(buyerRepository).save(this.buyer);
    }

    @DisplayName("comprueba excepción al validar dni en la modificación del comprador")
    @Test
    void throwAnExceptionIfTheDniExistsWhenUpdatingTheBuyer() throws Exception {
        String dniDTO = "23456778";
        String dniBD = "34459845";
        String espected = "El dni " + dniDTO + " ya existe. Ingrese un nuevo dni";

        when(buyerRepository.existsByDni(dniDTO)).thenReturn(true);
        Exception currentMjError = assertThrows(Exception.class,
                () -> buyerService.validateIfExistsByDni(dniDTO, dniBD));

        assertEquals(espected, currentMjError.getMessage());
    }

    @DisplayName("comprueba la busqueda del comprador por id")
    @Test
    void findBuyerById() throws Exception {
        Long id = 1L;
        var ticketDetail = new TicketDetail();
        ticketDetail.setPurchaseDate(LocalDateTime.now());
        String lastVisit = ticketDetail.getPurchaseDate().toLocalDate().toString();

        when(buyerRepository.findById(id)).thenReturn(Optional.ofNullable(this.buyer));
        //verificar que el mapeador modelMapper convierta correctamente el buyer a DTO
        when(modelMapper.map(this.buyer, BuyerDTORes.class)).thenReturn(this.buyerDTORes);
        when(ticketDetailService.lastVisit(id)).thenReturn(lastVisit);

        buyerService.getBuyerById(id);

        verify(buyerRepository).findById(id);
        //comprobar que el buyerDTORes sea igual al retornado por el método
        assertEquals(buyerDTORes, buyerService.getBuyerById(id));

    }

    @DisplayName("comprueba eliminación lógica del comprador")
    @Test
    void deleteBuyerById() throws Exception {
//        this.buyer.setBanned(true);

        when(buyerRepository.findById(1L)).thenReturn(Optional.ofNullable(this.buyer));
        buyerService.deleteBuyer(1L);
        assertTrue(this.buyer.isBanned());
        verify(buyerRepository).save(this.buyer);
    }

    @DisplayName("comprueba excepción si el id del comprador no está registrado")
    @Test
    void deleteNonExistingBuyerById() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.empty());
        String expected = "El id 1 no existe";

       Exception currentError = assertThrows(IdNotFoundException.class, () -> buyerService.deleteBuyer(1L));
       assertEquals(expected, currentError.getMessage());
       verify(buyerRepository, never()).save(this.buyer);
    }

    @DisplayName("Comprueba la cantidad de elementos paginados, el page y el size de la lista devueta")
    @Test
    void findAllBuyersPageable(){
        int page = 0;
        int size = 3;

        var buyers = new ArrayList<Buyer>();
        buyers.add(new Buyer());
        buyers.add(new Buyer());

        Pageable pageable = PageRequest.of(page, size);
        when(buyerRepository.findAll(pageable)).thenReturn(new PageImpl<>(buyers, pageable, buyers.size()));
        when(modelMapper.map(any(Buyer.class), eq(BuyerDTORes.class))).thenReturn(this.buyerDTORes);

        // Llama al service
        Page<BuyerDTORes> result = buyerService.getAllBuyers(pageable);

        assertEquals(this.buyerDTORes, result.getContent().get(0));
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(1, result.getTotalPages());

        verify(buyerRepository).findAll(pageable);

    }

}