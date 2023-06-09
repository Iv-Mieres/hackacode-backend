package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalDTOReq {

    private LocalDateTime purchaseDate;
    private GameDTOReq gameDTO;
    private BuyerDTOReq buyerDTO;
}