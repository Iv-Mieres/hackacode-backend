package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int requiredAge;
    @ManyToOne
    @JoinColumn(name = "fkSchedule")
    @JsonIgnoreProperties("games")
    private Schedule schedule;
    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties("game")
    private List<Employee> employees;

    //Método para validar si el comprador cumple con la edad requerida del juego
    public boolean validateAge(LocalDate birthdate){
        return Period.between(birthdate, LocalDate.now()).getYears() >= this.requiredAge;
    }
}
