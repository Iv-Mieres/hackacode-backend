package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, max = 20, message = "Debe contener un mínimo de 4 y un máximo de 20 caracteres")
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private List<CustomUser> users;
}
