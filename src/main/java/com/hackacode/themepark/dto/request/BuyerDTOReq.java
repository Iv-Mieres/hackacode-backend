package com.hackacode.themepark.dto.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyerDTOReq {

    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 8, max = 8, message = "Debe contener 8 caracteres")
    @Pattern(regexp = "[0-9]+", message = "Debe contener solo numeros")
    private String dni;
    @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]*", message = "Debe contener solo letras y espacios")
    @NotNull(message = "No puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe contener un mínimo de 3 y un máximo de 50 caracteres")
    private String name;
    @Pattern(regexp = "[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ ]*", message = "debe contener solo letras y espacios")
    @NotNull(message = "No puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe contener un mínimo de 4 y un máximo de 50 caracteres")
    private String surname;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "No puede estar vacio")
    private LocalDate birthdate;
}
