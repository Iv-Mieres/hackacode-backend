package com.hackacode.themepark.service;

import com.hackacode.themepark.model.NormalTicket;
import com.hackacode.themepark.repository.INormalTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NormalTicketServiceTest {

    @Mock
    private INormalTicketRepository normalTicketRepository;

    @InjectMocks
    private NormalTicketService normalTicketService;

    @Test
    void calculateTotalNormalTicketsSoldOnAGivenDate(){

        LocalDateTime start = LocalDateTime.of(LocalDate.of(2010,4,5), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2010,4,5), LocalTime.MAX);

        when(normalTicketRepository.countByPurchaseDateBetween(start, end)).thenReturn(4L);
        var result = normalTicketService.totalNormalTicketsSoldOnAGivenDate(LocalDate.of(2010,4,5));

        assertEquals(4, result.getTotalNormalTicketsSold());
    }

    @Test
    void calculateTotalNormalTicketsSoldOfOneGameOnAGivenDate(){

        String gameName = "Montaña Rusa";

        LocalDateTime start = LocalDateTime.of(LocalDate.of(2010,4,5), LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(LocalDate.of(2010,4,5), LocalTime.MAX);

        when(normalTicketRepository.countByPurchaseDateBetweenAndGame_name(start, end, gameName)).thenReturn(4L);
        var result = normalTicketService.totalNormalTicketsSoldOfOneGameOnAGivenDate(LocalDate.of(2010,4,5), "Montaña Rusa");

        assertEquals(4, result.getTotalNormalTicketsSold());
    }

}