package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vips")
public class VipTicket extends Ticket {

    @OneToOne
    @JoinColumn(name = "fkBuyer", referencedColumnName = "id")
    private Buyer buyer;
    @DecimalMin(value = "0.0", message = "El valor mínimo ingresado debe ser de 0.0")
    private double price;

}
