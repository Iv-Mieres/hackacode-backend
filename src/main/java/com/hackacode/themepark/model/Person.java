package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@MappedSuperclass
public class Person {
    private String dni;
    private String name;
    private String surname;
    private LocalDate birthdate;

}
