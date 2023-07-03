package com.hackacode.themepark.service;

import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.ICustomUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private ICustomUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void loadUserByUsername() {
        var role = new Role();
        role.setId(1L);
        role.setRole("ADMINISTRADOR");

        when(passwordEncoder.encode("prueba1234"))
                .thenReturn("$2a$12$sXqCfKx6s0/xRgl0SRgXwuyYgyco7QO3RHVuvuHalt3Mun.jrhWEq");

        String username = "prueba@mail.com";
        var user = new CustomUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("prueba1234"));
        user.setEnable(true);
        user.setRoles(Set.of(role));

        var expectedUser =   new User(user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                user.getAuthorities());

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        var currentUser =  userDetailsService.loadUserByUsername(username);

        assertEquals(expectedUser, currentUser);
    }
}