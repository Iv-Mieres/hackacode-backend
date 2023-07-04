package com.hackacode.themepark.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTOReq {

    private Long id;
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser 0.0")
    @NotNull(message = "No puede estar vacio")
    private Double price;
    @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]*", message = "Debe contener solo letras y espacios")
    @Size(min = 4, max = 60, message =  "debe tener un mínimo 4 y un máximo de 60 caracteres")
    @NotNull(message = "No puede estar vacio")
    private String description;
    @NotNull(message = "Debe ser verdadero o falso")
    private Boolean vip;
}
