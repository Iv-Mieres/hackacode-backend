package com.hackacode.themepark.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOres {

    private Long employeeId;
    private String email;
    private String username;
    private GameDTORes gameDTO;

}
