package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOReq {

    private Long id;
    @Email(message = "debe contener un formato de tipo mail (example@example.com)")
    @NotNull(message = "No puede estar vacio")
    private String username;
    @NotNull(message = "No puede estar vacio")
    private String password;
    @NotNull(message = "No puede estar vacio")
    private EmployeeDTOReq employee;
    @NotNull(message = "No puede estar vacio")
    private List<RoleDTOReq> roles;
}
