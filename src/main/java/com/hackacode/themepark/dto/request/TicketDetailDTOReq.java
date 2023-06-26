package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailDTOReq {

    private UUID id;
    private TicketDTOReq ticket;
    private BuyerDTOReq buyer;
}
