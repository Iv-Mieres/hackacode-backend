package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerDTORes {

    private Long buyerId;
    private String dni;
    private String name;
    private String surname;
    private LocalDate birthdate;

}
