package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;



@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "buyers")
public class Buyer extends Person{

    private boolean isBanned;

    public Buyer() {
    }

    public Buyer(Long id, String dni, String name, String surname, LocalDate birthdate, boolean isBanned) {
        super(id, dni, name, surname, birthdate);
        this.isBanned = isBanned;
    }
}
