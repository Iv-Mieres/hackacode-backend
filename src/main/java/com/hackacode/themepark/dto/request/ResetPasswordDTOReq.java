package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDTOReq {

    @NotNull(message = "No puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String password;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String repeatPassword;
    private String token;
}
