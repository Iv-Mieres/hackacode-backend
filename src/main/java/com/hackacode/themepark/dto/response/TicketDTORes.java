package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTORes {

    private UUID id;
    private LocalDateTime dateTime;
    private double price;
    private GameDTORes game;
    private BuyerDTORes buyer;
}
