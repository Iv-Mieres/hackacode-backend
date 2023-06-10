package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.BuyerDTOReq;
import com.hackacode.themepark.model.Buyer;
import com.hackacode.themepark.repository.IBuyerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;

    @Mock
    private IBuyerRepository buyerRepository;


    private Buyer buyer;

    @BeforeEach
    void setUp() {
        this.buyer = new Buyer();
        buyer.setName("Diego");
        buyer.setSurname("Martinez");
        buyer.setBirthdate(LocalDate.of(1988,06,14));
        buyer.setDni("40948585");
    }

    @Test
    void verifyIfModelMapperConvertsBuyerDTOToEntityBuyer(){
        BuyerDTOReq buyerDTO = BuyerDTOReq.builder()
                .name("Diego")
                .surname("Martinez")
                .dni("40948585")
                .birthdate(LocalDate.of(1988,06,14))
                .build();

        when(buyerRepository.save(this.buyer)).thenReturn(this.buyer);
        buyerService.saveBuyer(buyerDTO);
        verify(buyerRepository).save(this.buyer);
    }

}