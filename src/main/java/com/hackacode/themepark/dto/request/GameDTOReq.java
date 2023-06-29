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
public class GameDTOReq {

    private Long id;
    @Pattern(regexp = "[A-Za-zÑñ ]+", message = "Debe contener solo letras y espacios")
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, message = "Debe contener un mínimo de 4 caracteres")
    private String name;
    @Pattern(regexp = "[0-9]+", message = "Debe contener solo numeros")
    @NotNull(message = "No puede estar vacio")
    private int requiredAge;
    @NotNull(message = "Debe asignar un horario")
    private ScheduleDTOReq schedule;

}
