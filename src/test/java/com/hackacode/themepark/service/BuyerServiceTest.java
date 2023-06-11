package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.hibernate.loader.ast.internal.SingleIdArrayLoadPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private IBuyerRepository buyerRepository;

    @Mock
    private ModelMapper modelMapper;

    private BuyerDTOReq buyerDTOReq;

    private BuyerDTORes buyerDTORes;

    private Buyer buyer;

    @BeforeEach
    void setUp() {
        this.buyer = new Buyer();
        buyer.setId(1L);
        buyer.setName("Diego");
        buyer.setSurname("Martinez");
        buyer.setBirthdate(LocalDate.of(1988,06,14));
        buyer.setDni("40948585");

        this.buyerDTOReq = new BuyerDTOReq();
        buyerDTOReq.setName("Diego");
        buyerDTOReq.setSurname("Martinez");
        buyerDTOReq.setBirthdate(LocalDate.of(1988,06,14));
        buyerDTOReq.setDni("40948585");

        this.buyerDTORes = new BuyerDTORes();
        buyerDTORes.setBuyerId(1L);
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
        when(buyerRepository.existsByDni(this.buyerDTORes.getDni())).thenReturn(false);
        when(buyerRepository.findById(1L)).thenReturn(Optional.ofNullable(this.buyer));

        buyerService.updateBuyer(this.buyerDTORes);
        verify(buyerRepository).save(modelMapper.map(this.buyerDTORes, Buyer.class));
    }

    @Test
    void throwAnExceptionIfTheDniExistsWhenUpdatingTheBuyer() throws Exception {
        when(buyerRepository.existsByDni(this.buyerDTORes.getDni())).thenReturn(true);
        assertThrows(Exception.class, () -> buyerService.updateBuyer(this.buyerDTORes));
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
    void deleteBuyerById(){
        doNothing().when(buyerRepository).deleteById(1L);
        buyerService.deleteBuyer(1L);
    }

    @Test
    void findAllBuyersPageable(){

    }

}