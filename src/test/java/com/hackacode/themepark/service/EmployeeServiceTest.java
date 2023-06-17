package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.response.BuyerDTORes;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.model.Buyer;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

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

    private Employee employee;

    @BeforeEach
    void setUp() {

        this.employee = new Employee(1L, "34845347", "Diego", "Sosa",
                LocalDate.of(1989, 3,1), true,null);

    }

    @Test
    void saveEmployee() throws Exception {

        EmployeeDTOReq employeeDTO = EmployeeDTOReq.builder()
                .id(1L)
                .build();

        when(employeeUserRepository.existsByDni(employeeDTO.getDni())).thenReturn(false);
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(this.employee);
        employeeService.saveEmployee(employeeDTO);
        verify(employeeUserRepository).save(this.employee);
    }


    @Test
    void throwAnExceptionIfExistsByDniIsTrue() throws Exception {

        EmployeeDTOReq employeeDTOReq = new EmployeeDTOReq();
        employeeDTOReq.setDni("23456778");

        when(employeeUserRepository.existsByDni(employeeDTOReq.getDni())).thenReturn(true);
        assertThrows(Exception.class,
                () -> employeeService.saveEmployee(employeeDTOReq));
    }

    @Test
    void getEmployeeById() throws Exception {
        this.employee.setId(1L);

        EmployeeDTORes employeeDTORes = new EmployeeDTORes();
        employeeDTORes.setId(this.employee.getId());


        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        when(modelMapper.map(this.employee, EmployeeDTORes.class)).thenReturn(employeeDTORes);
        EmployeeDTORes employeeDTORes1 =  employeeService.getEmployeeById(1L);

        assertEquals(this.employee.getId(), employeeDTORes1.getId());
        verify(employeeUserRepository).findById(1L);

    }

    @Test
    void findAllEmployeesPageable(){
        int page = 0;
        int size = 3;

        EmployeeDTORes employeeDTORes = new EmployeeDTORes();
        employeeDTORes.setId(this.employee.getId());

        //guardar los employees en una lista
        var employees = new ArrayList<Employee>();
        employees.add(this.employee);
        employees.add(new Employee(2L, "56845347", "Silvia", "Sosa",
                LocalDate.of(1989, 3,1), true,null));

        var pageable = PageRequest.of(page, size);
        when(modelMapper.map(this.employee, EmployeeDTORes.class)).thenReturn(employeeDTORes);
        when(employeeUserRepository.findAllByIsEnable(true, pageable)).thenReturn(new PageImpl<>(employees, pageable, employees.size()));

        var result = employeeService.getAllEmployees(pageable);

        //compara el objeto devuelto
        assertEquals(employeeDTORes, result.getContent().get(0));

        //verifica que el paginado devuelva la cantidad de elementos y el total de paginas correctas
        assertEquals(employees.size(), result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(1, result.getTotalPages());

        verify(employeeUserRepository).findAllByIsEnable(true, pageable);

    }

    @Test
    void ifWhenValidatingTheDniItIsNotUniqueToThrowAnException() throws Exception {
            String dniDTO = "24845347";
            String espectedMjError = "El dni " + dniDTO + " ya existe. Ingrese un nuevo dni";

            when(employeeUserRepository.existsByDni(dniDTO)).thenReturn(true);
           Exception actual = assertThrows(Exception.class,
                    () ->  employeeService.validateIfExistsByDni(dniDTO , "34845347"));
            assertEquals(espectedMjError, actual.getMessage());

    }

    @Test
    void updateEmployeeIfDniDoesNotExistInTheDataBase() throws Exception {

        var employeeDTO = EmployeeDTOReq.builder()
                .id(1L)
                .dni("34845347")
                .build();

        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(this.employee);
        employeeService.updateEmployee(employeeDTO);
        verify(employeeUserRepository).save(this.employee);
    }

    @Test
    void deleteEmployeeById() throws Exception {
        this.employee.setEnable(false);

        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        employeeService.deleteEmployee(1L);

        verify(employeeUserRepository).save(this.employee);
    }

}