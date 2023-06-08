package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NormalDTORes {

    private LocalDateTime dateTime;
    private boolean used;
    private GameDTORes gameDTO;
    private BuyerDTORes buyerDTO;
}
