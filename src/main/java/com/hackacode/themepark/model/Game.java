package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, message = "Debe contener un mínimo de 4 caracteres")
    private String name;
    @NotEmpty(message = "No puede estar vacio")
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;
    @NotEmpty(message = "No puede estar vacio")
    private int requiredAge;
    @ManyToOne
    @JoinColumn(name = "fkSchedule")
    @JsonIgnoreProperties("games")
    private Schedule schedule;
    @OneToOne
    @JoinColumn(name = "fkEmployee")
    private Employee employee;

    //Método para validar si el comprador es menor o mayor a 18 años
    public boolean validateAge(Buyer buyer){
        return Period.between(buyer.getBirthdate(), LocalDate.now()).getYears() >= 18;
    }
}
