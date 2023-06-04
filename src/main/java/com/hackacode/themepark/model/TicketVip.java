package com.hackacode.themepark.model;

import jakarta.persistence.*;
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
