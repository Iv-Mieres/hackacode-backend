package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalTicketDTORes {

    private UUID id;
    private LocalDateTime dateTime;
    private boolean used;
    private GameDTORes game;
    private BuyerDTORes buyer;
}
