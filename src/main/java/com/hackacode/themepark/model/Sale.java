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
    private List<VipTicket> vipTickets;
    @OneToMany
    private List<NormalTicket> normalTickets;

    public double calculateTotalPrice(){
        double sum = 0.0;
        for (NormalTicket normalTicket: normalTickets) {
          sum += normalTicket.getGame().getPrice();
        }
        for (VipTicket vipTicket : vipTickets) {
            sum += vipTicket.getPrice();
        }
        return sum;
    }
}
