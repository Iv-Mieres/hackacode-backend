package com.hackacode.themepark.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class CustomUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "No puede estar vacio")
    @Email(message = "Debe ingresar un formato de tipo email")
    private String username;
    @NotNull(message = "Do puede estar vacio")
    @Size(min = 6, message = "Debe contener un mínimo de 6 caracteres")
    private String password;
    private boolean isEnable;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usersRoles",
            joinColumns = @JoinColumn(name = "fkUser"),
            inverseJoinColumns = @JoinColumn(name = "fkRole")
    )
    @JsonIgnoreProperties("users")
//    @NotNull(message = "Debe asignar un rol")
    private Set<Role> roles;

    @OneToOne
    @JoinColumn(name = "fkEmployee", referencedColumnName = "id")
    @NotNull(message = "No puede estar vacio")
    private Employee employee;

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
