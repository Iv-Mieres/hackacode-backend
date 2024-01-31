package com.hackacode.themepark.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Hidden
@Data
@MappedSuperclass
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private String surname;
    @Temporal(TemporalType.DATE)
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
