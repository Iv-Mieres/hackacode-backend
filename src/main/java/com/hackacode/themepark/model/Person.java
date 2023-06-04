package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
@Data
@MappedSuperclass
public class Person {

    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 8, max = 8, message = "Debe contener 8 caracteres")
    private String dni;
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 4, max = 10, message = "Debe contener un mínimo de 4 y un máximo de 8 caracteres")
    private String name;
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 4, max = 20, message = "Debe contener un mínimo de 4 y un máximo de 20 caracteres")
    private String surname;
    @NotEmpty(message = "No puede estar vacio")
    private LocalDate birthdate;

}
