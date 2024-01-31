package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTOReq {

    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Debe ingresar un formato de tipo email")
    private String username;
    private String password;
    @NotNull(message = "No puede estar vacio")
    private EmployeeDTOReq employee;
    @NotNull(message = "No puede estar vacio")
    private List<RoleDTOReq> roles;
    private boolean isEnable;
}
