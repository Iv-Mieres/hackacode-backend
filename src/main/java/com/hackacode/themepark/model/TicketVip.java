package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ticketsVip")
public class TicketVip {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;
    @CreationTimestamp
    private LocalDateTime purchaseDate;
    private boolean used;
    @OneToOne
    @JoinColumn(name = "fkBuyer", referencedColumnName = "id")
    private Buyer buyer;

}
