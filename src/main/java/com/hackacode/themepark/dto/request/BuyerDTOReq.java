package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerDTOReq {

    private String dni;
    private String name;
    private String surname;
    private LocalDate birthdate;
}
