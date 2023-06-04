package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 4, message = "Debe contener un mínimo de 4 caracteres")
    private String name;
    @NotEmpty(message = "No puede estar vacio")
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;
    @NotEmpty(message = "No puede estar vacio")
    private int minAge;
    @ManyToOne
    @JoinColumn(name = "fkSchedule")
    private Schedule schedule;

    public boolean validateAge(Buyer buyer){
        return Period.between(buyer.getBirthdate(), LocalDate.now()).getYears() >= 18;
    }
}
