package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalTicketDTOReq {

    private Long id;
    private LocalDateTime purchaseDate;
    private GameDTOReq game;
    private BuyerDTOReq buyer;
}
