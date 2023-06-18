package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    private BuyerDTOReq buyerDTOReq;

    private BuyerDTORes buyerDTORes;

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        this.buyer = new Buyer(1L, "40948585", "Diego", "Martinez",
                LocalDate.of(1988,06,14), false);

        this.buyerDTOReq = new BuyerDTOReq();
        buyerDTOReq.setName("Diego");
        buyerDTOReq.setSurname("Martinez");
        buyerDTOReq.setBirthdate(LocalDate.of(1988,06,14));
        buyerDTOReq.setDni("40948585");

        this.buyerDTORes = new BuyerDTORes();
        buyerDTORes.setId(1L);
        buyerDTORes.setName("Diego");
        buyerDTORes.setSurname("Martinez");
        buyerDTORes.setBirthdate(LocalDate.of(1988,06,14));
        buyerDTORes.setDni("40948585");
    }

    @Test
    void saveBuyerIfDniDoesNotExistInTheDataBase() throws Exception {
        when(buyerRepository.existsByDni(this.buyerDTOReq.getDni())).thenReturn(false);
        when(modelMapper.map(this.buyerDTOReq, Buyer.class)).thenReturn(this.buyer);
        buyerService.saveBuyer(this.buyerDTOReq);
        verify(buyerRepository).save(this.buyer);
    }

    @Test
    void throwAnExceptionIfTheDniExistsWhenSavingTheBuyer() throws Exception {
        when(buyerRepository.existsByDni(this.buyerDTOReq.getDni())).thenReturn(true);
        assertThrows(Exception.class, () -> buyerService.saveBuyer(this.buyerDTOReq));

    }

    @Test
    void updateBuyerIfDniDoesNotExistInTheDataBase() throws Exception {
        when(buyerRepository.findById(1L)).thenReturn(Optional.ofNullable(this.buyer));
        buyerService.updateBuyer(this.buyerDTORes);
        verify(buyerRepository).save(modelMapper.map(this.buyerDTORes, Buyer.class));
    }

    @Test
    void throwAnExceptionIfTheDniExistsWhenUpdatingTheBuyer() throws Exception {
        String dniDTO = "23456778";
        String dniBD = "34459845";
        String espectedMjError = "El dni " + dniDTO + " ya existe. Ingrese un nuevo dni";

        when(buyerRepository.existsByDni(dniDTO)).thenReturn(true);
        Exception currentMjError = assertThrows(Exception.class,
                () -> buyerService.validateIfExistsByDni(dniDTO, dniBD));

        assertEquals(espectedMjError, currentMjError.getMessage());
    }

    @Test
    void findBuyerById() throws Exception {
        Long id = 1L;
        when(buyerRepository.findById(id)).thenReturn(Optional.ofNullable(this.buyer));
        //verificar que el mapeador modelMapper convierta correctamente el buyer a DTO
        when(modelMapper.map(this.buyer, BuyerDTORes.class)).thenReturn(this.buyerDTORes);
        buyerService.getBuyerById(id);

        verify(buyerRepository).findById(id);
        //comprobar que el buyerDTORes sea igual al retornado por el método
        assertEquals(buyerDTORes, buyerService.getBuyerById(id));

    }

    @Test
    void ifBuyerExistsByDniThenThrowAnException() throws Exception {
            when(buyerRepository.existsByDni("12345678")).thenReturn(true);
            assertThrows(Exception.class, () -> buyerService.validateIfExistsByDni("12345678", "22345678"));
    }

    @Test
    void deleteBuyerById() throws Exception {
        this.buyer.setBanned(true);

        when(buyerRepository.findById(1L)).thenReturn(Optional.ofNullable(this.buyer));
        buyerService.deleteBuyer(1L);

        verify(buyerRepository).save(this.buyer);
    }

    @Test
    void findAllBuyersPageable(){
        int page = 0;
        int size = 3;

        var buyers = new ArrayList<Buyer>();
        buyers.add(this.buyer);
        buyers.add(new Buyer(2L, "41948585", "Martin", "Martinez",
                LocalDate.of(1990,4,17), false));
        buyers.add(new Buyer(3L, "42948585", "Analia", "Martinez",
                LocalDate.of(1991,3,13), false));

        Pageable pageable = PageRequest.of(page, size);
        when(modelMapper.map(this.buyer, BuyerDTORes.class)).thenReturn(this.buyerDTORes);
        when(buyerRepository.findAll(pageable)).thenReturn(new PageImpl<>(buyers, pageable, buyers.size()));

        // Llama al service
        Page<BuyerDTORes> result = buyerService.getAllBuyers(pageable);

        assertEquals(this.buyerDTORes, result.getContent().get(0));

        assertEquals(buyers.size(), result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(1, result.getTotalPages());

        verify(buyerRepository).findAll(pageable);

    }

}