package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTOReq {

    private UUID id;
    private GameDTOReq game;
    private BuyerDTOReq buyer;
}
