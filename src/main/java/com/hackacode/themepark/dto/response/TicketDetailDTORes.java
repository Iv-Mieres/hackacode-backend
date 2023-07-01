package com.hackacode.themepark.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailDTORes {

    private UUID id;
    private LocalDateTime purchaseDate;
    private TicketDTORes ticket;
    private BuyerDTORes buyer;
}
