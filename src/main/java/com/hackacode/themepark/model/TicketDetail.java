package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ticketDetails")
public class TicketDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @CreationTimestamp
    private LocalDateTime purchaseDate;
    @OneToOne
    @JoinColumn(name = "fkBuyer")
    private Buyer buyer;

    @OneToOne
    @JoinColumn(name = "fkTicket")
    private Ticket ticket;
}
