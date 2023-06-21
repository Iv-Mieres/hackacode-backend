package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double totalPrice;
    @CreationTimestamp
    private LocalDateTime purchaseDate;
    @OneToMany
    private List<VipTicket> vipTickets;
    @OneToMany
    private List<NormalTicket> normalTickets;

}
