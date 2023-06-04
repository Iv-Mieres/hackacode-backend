package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 4, max = 20, message = "Debe contener un mínimo de 4 y un máximo de 20 caracteres")
    private String role;
    @ManyToMany(mappedBy = "roles")
    private List<EmployeeUser> users;
}
