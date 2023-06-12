package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTORes {

    private Long employeeId;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private String email;
    private String username;
    private GameDTORes gameDTO;

}
