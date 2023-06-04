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
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime dateTime;
    private boolean used;
    @OneToOne
    @JoinColumn(name = "fkGame")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "fkSale")
    private Sale sale;
    @OneToOne
    @JoinColumn(name = "fkBuyer")
    private Buyer buyer;
}
