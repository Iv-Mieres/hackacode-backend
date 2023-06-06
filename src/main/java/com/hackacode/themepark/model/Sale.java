package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @OneToMany
    private List<TicketVip> ticketsVip;
    @OneToMany
    private List<Ticket> tickets;

    public double calculateTotalPrice(){
        double sum = 0.0;
        for (Ticket ticket: tickets) {
          sum += ticket.getGame().getPrice();
        }
        for (TicketVip vip: ticketsVip) {
            sum += vip.getPrice();
        }
        return sum;
    }
}
