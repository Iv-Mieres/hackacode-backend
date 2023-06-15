package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "Employees")
public class Employee extends Person implements UserDetails {

    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Debe ingresar un formato de tipo email")
    private String email;
    @NotNull(message = "No puede estar vacio")
    @Size(min = 4, max = 10, message = "Debe contener un mínimo de 4 y un máximo de 10 caracteres")
    private String username;
    @NotNull(message = "Do puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String password;
    private boolean isEnable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employeeRole",
            joinColumns = @JoinColumn(name = "fkEmployee"),
            inverseJoinColumns = @JoinColumn(name = "fkRole")
    )
    @JsonIgnoreProperties("employee")
    private Set<Role> roles;

    @OneToOne(mappedBy = "employee", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("employee")
    private Game game;

    public Employee() {
        super();
    }

    public Employee(Long id, String dni, String name, String surname, LocalDate birthdate,
                    String email, String username, String password, boolean isEnable, Set<Role> roles, Game game) {
        super(id, dni, name, surname, birthdate);
        this.email = email;
        this.username = username;
        this.password = password;
        this.isEnable = isEnable;
        this.roles = roles;
        this.game = game;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
}
