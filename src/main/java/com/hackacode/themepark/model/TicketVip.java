package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketVip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;
    @CreationTimestamp
    private LocalDateTime date;
    private boolean used;
    @ManyToOne
    @JoinColumn(name = "fkSale")
    private Sale sale;
    @OneToOne
    @JoinColumn(name = "fkBuyer")
    private Buyer buyer;

}
