package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTORes {

    private Long id;
    private double totalPrice;
    private List<TicketDetailDTORes> ticketsDetail;
    private GameDTORes game;
    private LocalDateTime purchaseDate;

}
