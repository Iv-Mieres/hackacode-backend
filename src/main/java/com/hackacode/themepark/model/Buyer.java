package com.hackacode.themepark.model;

import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity(name = "buyers")
public class Buyer extends Person{

}
