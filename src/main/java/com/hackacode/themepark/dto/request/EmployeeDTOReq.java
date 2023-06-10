package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOReq {

    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private String email;
    private String username;
    private String password;
    private Long roleId;
}
