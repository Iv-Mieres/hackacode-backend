package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTORes {

    private Long id;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private boolean isEnable;
    private GameDTORes game;

}
