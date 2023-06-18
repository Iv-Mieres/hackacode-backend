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
public class EmployeeDTOReq {

    private Long id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private GameDTOReq game;
}
