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
    @Size(min = 3, max = 25, message = "Debe contener un mínimo de 3 y un máximo de 25 caracteres")
    private String name;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 3, max = 50, message = "Debe contener un mínimo de 4 y un máximo de 50 caracteres")
    private String surname;
    @Temporal(TemporalType.DATE)
    @NotNull(message = "No puede estar vacio")
    private LocalDate birthdate;

    public Person(Long id, String dni, String name, String surname, LocalDate birthdate) {
        this.id = id;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    public Person() {
    }
}
