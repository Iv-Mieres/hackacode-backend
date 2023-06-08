package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;
    @CreationTimestamp
    private LocalDateTime purchaseDate;
}
