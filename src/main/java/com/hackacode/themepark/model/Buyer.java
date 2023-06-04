package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Buyer extends Person{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "fkTicketVip")
    private TicketVip ticketVip;
    @OneToOne
    @JoinColumn(name = "fkTicket")
    private Ticket ticket;
}
