package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vips")
public class Vip extends Ticket{

    @OneToOne
    @JoinColumn(name = "fkBuyer", referencedColumnName = "id")
    private Buyer buyer;

}
