package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VipDTORes {

    private UUID ticketId;
    private double Price;
    private LocalDate purchaseDate;
    private BuyerDTORes buyerDTO;
}