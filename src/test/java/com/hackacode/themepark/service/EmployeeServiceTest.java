package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.exception.DniExistsException;
import com.hackacode.themepark.exception.DniNotFoundException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.IEmployeeRepository;
import com.hackacode.themepark.repository.IGameRepository;
import com.hackacode.themepark.util.IWordsConverter;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private IEmployeeRepository employeeUserRepository;
    @Mock
    private ICustomUserRepository userRepository;
    @Mock
    private IGameRepository gameRepository;
    @Mock
    private IWordsConverter wordsConverter;
    @Mock
    private ModelMapper modelMapper;

    private Employee employee;

    @BeforeEach
    void setUp() {

        this.employee = new Employee(1L, "34845347", "diego", "Sosa",
                LocalDate.of(1989, 3, 1), true, null);
    }

    @DisplayName("comprueba que se guarde un empleado")
    @Test
    void saveEmployee() throws Exception {

        var employeeDTO = EmployeeDTOReq.builder().id(1L).name("diego").build();

        when(employeeUserRepository.existsByDni(employeeDTO.getDni())).thenReturn(false);
        when(wordsConverter.capitalizeWords(employeeDTO.getName())).thenReturn("Diego");
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(this.employee);
        employeeService.saveEmployee(employeeDTO);
        assertEquals(employeeDTO.getName(), "Diego");
        verify(employeeUserRepository).save(this.employee);
    }

    @DisplayName("comprueba excepción si el juego es nulo")
    @Test
    void throwsAnExceptionIfTheGameIsNull() throws EntityNotFoundException {
        String expected = "El juego que intenta ingresar no existe. Ingrese un juego existente";
        var game = new GameDTOReq();
        game.setId(1L);
        game.setName("Montaña Rusa");
        var employeeDTO = EmployeeDTOReq.builder().id(1L).name("diego").game(game).build();

        when(employeeUserRepository.existsByDni(employeeDTO.getDni())).thenReturn(false);
        when(wordsConverter.capitalizeWords(employeeDTO.getName())).thenReturn("Diego");
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(this.employee);
        when(gameRepository.existsById(game.getId())).thenReturn(false);

        Exception currentError = assertThrows(EntityNotFoundException.class,
                () -> employeeService.saveEmployee(employeeDTO));
        assertEquals(expected, currentError.getMessage());
    }

    @DisplayName("comprueba excepción si el id del juego ingresado es nulo")
    @Test
    void throwsAnExceptionIfTheGameIdIsNull() throws Exception {
        String expected = "El juego que intenta ingresar no existe. Ingrese un juego existente";
        var game = new GameDTOReq();
        var employeeDTO = EmployeeDTOReq.builder().id(1L).name("diego").game(game).build();

        when(employeeUserRepository.existsByDni(employeeDTO.getDni())).thenReturn(false);
        when(wordsConverter.capitalizeWords(employeeDTO.getName())).thenReturn("Diego");
        when(modelMapper.map(employeeDTO, Employee.class)).thenReturn(this.employee);

        Exception cuerrentError = assertThrows(EntityNotFoundException.class,
                () -> employeeService.saveEmployee(employeeDTO));
        assertEquals(expected, cuerrentError.getMessage());
    }

    @DisplayName("comprueba que lance una excepción si el dni ya está registrado al guardar un empleado")
    @Test
    void throwAnExceptionIfExistsByDniIsTrue() {
        String expectedEx = "El dni " + this.employee.getDni() + " ya existe. Ingrese un nuevo dni";
        EmployeeDTOReq employeeDTOReq = new EmployeeDTOReq();
        employeeDTOReq.setDni("34845347");

        when(modelMapper.map(employeeDTOReq, Employee.class)).thenReturn(this.employee);
        when(employeeUserRepository.existsByDni(employeeDTOReq.getDni())).thenReturn(true);
        Exception currentEx = assertThrows(DniExistsException.class,
                () -> employeeService.saveEmployee(employeeDTOReq));

        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba que se devuelva un empleado al buscar por id")
    @Test
    void getEmployeeById() throws Exception {
        this.employee.setId(1L);

        EmployeeDTORes employeeDTORes = new EmployeeDTORes();
        employeeDTORes.setId(this.employee.getId());

        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        when(modelMapper.map(this.employee, EmployeeDTORes.class)).thenReturn(employeeDTORes);
        EmployeeDTORes employeeDTORes1 = employeeService.getEmployeeById(1L);

        assertEquals(this.employee.getId(), employeeDTORes1.getId());
        verify(employeeUserRepository).findById(1L);

    }

    @DisplayName("comprueba que se devuelva un empleado al buscar por dni")
    @Test
    void getEmployeeByDni() throws Exception {

        var employeeDTORes = new EmployeeDTORes(1L, "Diego", "Sosa", "34845347",
                LocalDate.of(1989, 3, 1), true, null);

        when(employeeUserRepository.findByDni(this.employee.getDni())).thenReturn(Optional.ofNullable(this.employee));
        when(modelMapper.map(this.employee, EmployeeDTORes.class)).thenReturn(employeeDTORes);
        var result = employeeService.getEmployeeByDni(this.employee.getDni());

        assertEquals(this.employee.getDni(), result.getDni());
        verify(employeeUserRepository).findByDni(this.employee.getDni());

    }

    @Test
    void dniNotFoundException() {
        String expectedEx = "El dni " + this.employee.getDni() + " no existe";
        when(employeeUserRepository.findByDni(this.employee.getDni())).thenReturn(Optional.empty());
        Exception currentEx = assertThrows(DniNotFoundException.class,
                () -> employeeService.getEmployeeByDni(this.employee.getDni()));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba el paginado juento con el page y el size al llamar a todos lo empleados")
    @Test
    void findAllEmployeesPageable() {
        int page = 0;
        int size = 3;

        EmployeeDTORes employeeDTORes = new EmployeeDTORes();
        employeeDTORes.setId(this.employee.getId());

        //guardar los employees en una lista
        var employees = new ArrayList<Employee>();
        employees.add(this.employee);
        employees.add(new Employee(2L, "56845347", "Silvia", "Sosa",
                LocalDate.of(1989, 3, 1), true, null));

        var pageable = PageRequest.of(page, size);
        when(modelMapper.map(this.employee, EmployeeDTORes.class)).thenReturn(employeeDTORes);
        when(employeeUserRepository.findAllByIsEnable(true, pageable)).thenReturn(new PageImpl<>(employees, pageable, employees.size()));

        var result = employeeService.getAllEmployees(pageable);

        //compara el objeto devuelto
        assertEquals(employeeDTORes, result.getContent().get(0));

        //verifica que el paginado devuelva la cantidad de elementos y el total de paginas correctas
        assertEquals(2, result.getTotalElements());
        assertEquals(0, result.getNumber());
        assertEquals(1, result.getTotalPages());

        verify(employeeUserRepository).findAllByIsEnable(true, pageable);

    }

    @DisplayName("comprueba excepción al llamar al método para validar si el dni existe")
    @Test
    void ifWhenValidatingTheDniItIsNotUniqueToThrowAnException() throws Exception {
        String dniDTO = "24845347";
        String espectedMjError = "El dni " + dniDTO + " ya existe. Ingrese un nuevo dni";

        when(employeeUserRepository.existsByDni(dniDTO)).thenReturn(true);
        Exception actual = assertThrows(Exception.class,
                () -> employeeService.validateIfExistsByDni(dniDTO, "34845347"));
        assertEquals(espectedMjError, actual.getMessage());

    }

    @DisplayName("comprueba que se modifique un empleado")
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

    @DisplayName("comprueba excepción si el id del empleado no está registrado")
    @Test
    void deleteNonExistingBuyerById() {
        when(employeeUserRepository.findById(1L)).thenReturn(Optional.empty());
        String expected = "El id 1 no existe";

        Exception currentError = assertThrows(IdNotFoundException.class,
                () -> employeeService.deleteEmployee(this.employee.getId()));
        assertEquals(expected, currentError.getMessage());
        verify(employeeUserRepository, never()).save(this.employee);
    }

    @DisplayName("comprueba que se elimine un empleado de forma lógica junto con us usuario")
    @Test
    void deleteEmployeeById() throws Exception {
        this.employee.setEnable(false);
        var user = new CustomUser();

        when(employeeUserRepository.findById(1L)).thenReturn(Optional.ofNullable(this.employee));
        when(userRepository.findByEmployee_Id(this.employee.getId())).thenReturn(user);
        employeeService.deleteEmployee(1L);

        assertFalse(user.isEnabled());
        assertFalse(this.employee.isEnable());

        verify(userRepository).save(user);
        verify(employeeUserRepository).save(this.employee);
    }

}