package com.hackacode.themepark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUser extends Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Debe ingresar un formato de tipo email")
    private String email;
    @NotEmpty(message = "No puede estar vacio")
    @Size(min = 4, max = 10, message = "Debe contener un mínimo de 4 y un máximo de 10 caracteres")
    private String username;
    @NotEmpty(message = "Do puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String password;
    private boolean isEnable;

    @ManyToMany
    @JoinTable(name = "employeeRole",
            joinColumns = @JoinColumn(name = "fkEmployee"),
            inverseJoinColumns = @JoinColumn(name = "fkRole")
    )
    private Set<Role> roles;
    @OneToOne
    @JoinColumn(name = "fkGame")
    private Game game;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
        authorities.add(new SimpleGrantedAuthority(role.getRole()));
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
        if (this.isEnable){
            return false;
        }
        return true;
    }
}
