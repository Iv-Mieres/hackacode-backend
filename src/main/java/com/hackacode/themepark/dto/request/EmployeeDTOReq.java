package com.hackacode.themepark.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTOReq {

    private String email;
    private String username;
    private String password;
    private boolean isEnable;
    private RoleDTOReq roleDTO;
}
