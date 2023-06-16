package com.hackacode.themepark.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    private Employee employee;
    private Role role;

    @BeforeEach
    void beforeAll() {
        this.role = new Role();
        role.setRole("ADMINISTRADOR");
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);

        this.employee =  new Employee(2L,"41948585", "Martin", "Martinez", LocalDate.of(1990, 4,17),
                "martin@mail.com", "martin1234", true, roles, null);
    }

    @Test
    void testThatGetAuthoritiesReturnsAListOfRoles(){
        boolean result = this.employee.getAuthorities()
                .stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMINISTRADOR"));
        assertEquals(true, result);
    }

    @Test
    void ifEmployeeIsDismissedThenIsEnableFalse(){
        this.employee.setEnable(false);
        assertEquals(false, employee.isEnabled());
    }

    @Test
    void ifEmployeeIsNotDismissedThenIsEnableTrue(){
        this.employee.setEnable(true);
        assertEquals(true, employee.isEnabled());
    }



}