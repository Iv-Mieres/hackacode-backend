package com.hackacode.themepark.dto.response;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORes {

    private Long id;
    private String username;
    private EmployeeDTORes employee;
    private Set<RoleDTORes> roles;
    private boolean isEnable;
}
