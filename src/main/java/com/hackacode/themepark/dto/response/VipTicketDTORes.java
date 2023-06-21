package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipTicketDTORes {

    private UUID id;
    private double Price;
    private LocalDateTime purchaseDate;
    private BuyerDTORes buyer;
}
