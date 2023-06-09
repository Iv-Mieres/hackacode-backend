package com.hackacode.themepark.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testThatGetAuthoritiesReturnsAListOfRoles(){
        //se crea el role
        var role1 = new Role();
        role1.setRole("ADMIN");
        //el Role se guarda en un Set
        var roles = new HashSet<Role>();
        roles.add(role1);
        //Se crea un Empleado asignando el Set de roles
        var employee = Employee.builder().roles(roles).build();
        //se comprueba que el método getAuthorities de la entidad Employee contenga el role asignado
        var result = employee.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));

        assertEquals(true, result);
    }

    @Test
    void ifEmployeeIsDismissedThenIsEnableFalse(){
        var employee = Employee.builder().isEnable(false).build();
        assertEquals(false, employee.isEnabled());
    }

    @Test
    void ifEmployeeIsNotDismissedThenIsEnableTrue(){
        var employee = Employee.builder().isEnable(true).build();
        assertEquals(true, employee.isEnabled());
    }

}