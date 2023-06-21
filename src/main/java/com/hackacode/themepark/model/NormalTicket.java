package com.hackacode.themepark.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "normals")
public class NormalTicket extends Ticket{

    private boolean used;
    @ManyToOne
    @JoinColumn(name = "fkGame")
    private Game game;
    @OneToOne
    @JoinColumn(name = "fkBuyer", referencedColumnName = "id")
    private Buyer buyer;

}
