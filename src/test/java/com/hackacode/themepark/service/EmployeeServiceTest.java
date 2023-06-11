package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.IEmployeeUserRepository;
import com.hackacode.themepark.repository.IRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private IEmployeeUserRepository employeeUserRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private Employee employee;

    private RoleDTORes role;

    @BeforeEach
    void setUp() {
        this.employee = new Employee();
        this.employee.setName("Ana");
        this.employee.setSurname("Soria");
        this.employee.setBirthdate(LocalDate.of(1988,7,24));
        this.employee.setDni("49384763");
        this.employee.setEmail("ana@mail.com");
        this.employee.setUsername("anaSoria");
        this.employee.setPassword("1234");
    }

    @Test
    void saveEmployee() throws Exception {

        role = new RoleDTORes();
        role.setRoleId(1L);
        role.setRole("ADMINISTRADOR");

//        this.employee.setRoles(Set.of(role));

        EmployeeDTOReq employeeDTO = EmployeeDTOReq.builder()
                .name("Ana")
                .surname("Soria")
                .birthdate(LocalDate.of(1988,7,24))
                .dni("49384763")
                .email("ana@mail.com")
                .username("anaSoria")
                .password("1234")
                .roleDTO(role).build();

        when(employeeUserRepository.save(this.employee)).thenReturn(employee);
        employeeService.saveEmployee(employeeDTO);
        verify(employeeUserRepository).save(employee);
    }

    @Test
    void getEmployyeById(){
//        this.employee.setId(1L);

        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        employeeService.getEmployeeById(1L);
//        verify(employeeUserRepository).findById(1L);

    }



}