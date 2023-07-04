package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class CustomUserTest {

    private CustomUser user;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setRole("ADMINISTRADOR");

        user = new CustomUser();
        user.setRoles(Set.of(role));
    }

    @Test
    void getAuthorities() {
        Set<GrantedAuthority> expected = new HashSet<>();
        expected.add(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
        assertEquals(expected, this.user.getAuthorities());
    }

    @Test
    void ifEnableIsTrueThenCredentialIsEnabledReturnTrue() {
        this.user.setEnable(true);
        assertTrue(this.user.isEnabled());
    }

    @Test
    void isEnabled() {
    }
}