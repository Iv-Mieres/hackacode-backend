package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalTicketDTOReq {

    private UUID id;
    private LocalDateTime purchaseDate;
    private GameDTOReq game;
    private BuyerDTOReq buyer;
}
