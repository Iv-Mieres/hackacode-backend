package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOReq {

    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Debe ingresar un formato de tipo email")
    private String username;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String password;
    @NotNull(message = "No puede estar vacio")
    private EmployeeDTOReq employee;
    @NotNull(message = "No puede estar vacio")
    private List<RoleDTOReq> roles;
}
