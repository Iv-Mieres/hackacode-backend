package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 8, max = 8, message = "Debe contener 8 caracteres")
    private String dni;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, max = 10, message = "Debe contener un mínimo de 4 y un máximo de 8 caracteres")
    private String name;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, max = 20, message = "Debe contener un mínimo de 4 y un máximo de 20 caracteres")
    private String surname;
    @NotNull(message = "No puede estar vacio")
    private LocalDate birthdate;

}
