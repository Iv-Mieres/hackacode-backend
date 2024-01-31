package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordDTOReq {

    @Email(message = "El fomato ingresado debe ser de tipo email (example@example.com)")
    @NotNull(message = "No puede estar vacio")
    private String username;
}
