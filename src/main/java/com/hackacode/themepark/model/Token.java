package com.hackacode.themepark.model;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Hidden
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationTime;

    @OneToOne
    @JoinColumn(name = "fkUser")
    private CustomUser user;
}
