package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketVipDTOReq {

    private LocalDateTime purchaseDate;
    private double price;
    private BuyerDTOReq buyerDTO;
}
