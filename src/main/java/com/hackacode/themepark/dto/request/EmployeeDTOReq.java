package com.hackacode.themepark.dto.request;

import com.hackacode.themepark.dto.response.RoleDTORes;
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

    private Long employeeId;
    private String name;
    private String surname;
    private String dni;
    private LocalDate birthdate;
    private String email;
    private String username;
    private String password;
    private RoleDTORes roleDTO;
}
