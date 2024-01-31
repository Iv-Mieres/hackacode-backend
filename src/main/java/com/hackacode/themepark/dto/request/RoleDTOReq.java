package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTOReq {

    private Long id;
    @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]*", message = "Debe contener solo letras y espacios")
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, max = 50, message = "Debe contener un mínimo de 4 y un máximo de 50 caracteres")
    private String role;
}
