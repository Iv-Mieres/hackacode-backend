package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Employees")
public class Employee extends Person {

    private boolean isEnable;

    @ManyToOne
    @JoinColumn(name = "fkGame")
    @JsonIgnoreProperties("employee")
    private Game game;

    public Employee() {
        super();
    }

    public Employee(Long id, String dni, String name, String surname, LocalDate birthdate,
                    boolean isEnable, Game game) {
        super(id, dni, name, surname, birthdate);
        this.isEnable = isEnable;
        this.game = game;
    }

}
