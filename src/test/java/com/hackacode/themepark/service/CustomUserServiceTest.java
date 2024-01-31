package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.EmployeeDTOReq;
import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.request.UserUpdateDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.exception.EmailExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleNotFoundException;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.IEmployeeRepository;
import com.hackacode.themepark.repository.IRoleRepository;
import jakarta.persistence.EntityExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserServiceTest {

    @InjectMocks
    private CustomUserService customUserService;

    @Mock
    private ICustomUserRepository customUserRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private IEmployeeRepository employeeRepository;
    @Mock
    private IRoleRepository roleRepository;


    private UserDTOReq userDTOReq;
    private CustomUser user;
    private RoleDTOReq roleDTO;

    @BeforeEach
    void setUp() {
        var role = new Role();
        role.setId(1L);
        role.setRole("ADMINISTRADOR");

        this.roleDTO = new RoleDTOReq();
        this.roleDTO.setId(1L);
        this.roleDTO.setRole("ADMINISTRADOR");

        this.user = new CustomUser();
        this.user.setRoles(Set.of(role));

        this.userDTOReq = new UserDTOReq();
        this.userDTOReq.setId(1L);
        this.userDTOReq.setUsername("ivan@gmail.com");
        this.userDTOReq.setPassword(passwordEncoder.encode("ivan1234"));
        this.userDTOReq.setRoles(List.of(roleDTO));
        this.userDTOReq.setEmployee(new EmployeeDTOReq(1L, "20394857", "Pablo", "Pedroso",
                LocalDate.of(2000,11,23), null));

    }

    @DisplayName("comprueba que se guarde un usuario")
    @Test
    void saveUser() throws RoleNotFoundException, EmailExistsException, IdNotFoundException {

        this.user.setPassword(passwordEncoder.encode("ivan1234"));

        when(customUserRepository.existsByUsername(this.userDTOReq.getUsername())).thenReturn(false);
        when(customUserRepository.existsByEmployee_Id(this.userDTOReq.getEmployee().getId())).thenReturn(false);
        when(employeeRepository.existsById(this.userDTOReq.getEmployee().getId())).thenReturn(true);
        when(roleRepository.existsById(this.userDTOReq.getRoles().get(0).getId())).thenReturn(true);
        when(modelMapper.map(this.userDTOReq, CustomUser.class)).thenReturn(this.user);
        when(passwordEncoder.encode(this.user.getPassword())).thenReturn(this.user.getPassword());
        customUserService.saveUser(this.userDTOReq);
        verify(customUserRepository).save(this.user);
    }

    @DisplayName("comprueba que se muestre un usuario por username")
    @Test
    void getByUsername() throws UsernameNotFoundException {
        when(modelMapper.map(this.user, UserDTORes.class)).thenReturn(new UserDTORes());
        when(customUserRepository.findByUsername(this.userDTOReq.getUsername())).thenReturn(Optional.ofNullable(this.user));
        customUserService.getByUsername(this.userDTOReq.getUsername());
        verify(customUserRepository).findByUsername(this.userDTOReq.getUsername());
    }

    @DisplayName("comprueba que se muestre un usuario por id")
    @Test
    void usernameNotFoundException() {

        String expectedEx = "El usuario:" + this.userDTOReq.getUsername() + " no se encuentra registrado";

        when(customUserRepository.findByUsername(this.userDTOReq.getUsername())).thenReturn(Optional.empty());
        Exception currentEx = assertThrows(UsernameNotFoundException.class,
                () -> customUserService.getByUsername(this.userDTOReq.getUsername()));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @Test
    void getUserById() throws IdNotFoundException {
        when(modelMapper.map(this.user, UserDTORes.class)).thenReturn(new UserDTORes());
        when(customUserRepository.findById(this.userDTOReq.getId())).thenReturn(Optional.ofNullable(this.user));
        customUserService.getUserById(this.userDTOReq.getId());
        verify(customUserRepository).findById(this.userDTOReq.getId());
    }

    @DisplayName("comprueba que retorne una lista de usuarios paginados")
    @Test
    void getAllUsers() {

        int page = 0;
        int size = 1;

        this.user.setId(1L);
        this.user.setUsername("marcos@gmail.com");
        this.user.setEnable(true);

        var users = new ArrayList<CustomUser>();
        users.add(this.user);
        users.add(new CustomUser());

        var userDTO = new UserDTORes();

        var pageable = PageRequest.of(page, size);
        when(customUserRepository.findAll(pageable)).thenReturn(new PageImpl<>(users, pageable, users.size()));
        when(modelMapper.map(this.user, UserDTORes.class)).thenReturn(userDTO);
        var currentList = customUserService.getAllUsers(pageable);

        assertEquals(1, currentList.getSize());
        assertEquals(2, currentList.getTotalElements());
        assertEquals(0, currentList.getNumber());
        assertEquals(2, currentList.getTotalPages());

        verify(customUserRepository).findAll(pageable);



    }

    @DisplayName("comprueba que retorne una lista de usuarios paginados por role")
    @Test
    void getAllUsersPerRole() {
        int page = 0;
        int size = 1;

        this.user.setId(1L);
        this.user.setUsername("marcos@gmail.com");
        this.user.setEnable(true);

        var users = new ArrayList<CustomUser>();
        users.add(this.user);
        users.add(new CustomUser());

        var userDTO = new UserDTORes();

        var pageable = PageRequest.of(page, size);
        when(customUserRepository.findByRoles_role(pageable, this.userDTOReq.getRoles().get(0).getRole()))
                .thenReturn(new PageImpl<>(users, pageable, users.size()));
        when(modelMapper.map(this.user, UserDTORes.class)).thenReturn(userDTO);
        var currentList = customUserService.getAllUsersPerRole(pageable, this.userDTOReq.getRoles().get(0).getRole());

        assertEquals(size, currentList.getSize());
        assertEquals(2, currentList.getTotalElements());
        assertEquals(0, currentList.getNumber());
        assertEquals(2, currentList.getTotalPages());

        verify(customUserRepository).findByRoles_role(pageable,this.userDTOReq.getRoles().get(0).getRole() );
    }

    @DisplayName("comprueba que se modifique un usuario")
    @Test
    void updateUser() throws RoleNotFoundException, EmailExistsException, IdNotFoundException {

        var employee = new Employee();
        employee.setId(1L);

        var updateDTO = new UserUpdateDTOReq();
        updateDTO.setId(1L);
        updateDTO.setEnable(true);
        updateDTO.setUsername("Pablo@gmail.com");
        updateDTO.setRoles(List.of(this.roleDTO));
        updateDTO.setEmployee(new EmployeeDTOReq(1L, "20394857", "Pablo", "Pedroso",
                LocalDate.of(2000,11,23), null));

        when(customUserRepository.findById(updateDTO.getId())).thenReturn(Optional.ofNullable(this.user));
        when(employeeRepository.findById(updateDTO.getEmployee().getId())).thenReturn(Optional.of(employee));
        when(customUserRepository.existsByUsername(updateDTO.getUsername())).thenReturn(false);
        when(roleRepository.existsById(updateDTO.getRoles().get(0).getId())).thenReturn(true);
        when(modelMapper.map(updateDTO, CustomUser.class)).thenReturn(this.user);
        customUserService.updateUser(updateDTO);
        verify(customUserRepository).save(this.user);

    }

    @DisplayName("comprueba que se elimine un usuario")
    @Test
    void deleteUser() throws IdNotFoundException {
        this.user.setEnable(true);

        when(customUserRepository.findById(this.userDTOReq.getId())).thenReturn(Optional.ofNullable(this.user));
        customUserService.deleteUser(this.userDTOReq.getId());
        assertFalse(this.user.isEnabled());
        verify(customUserRepository).save(this.user);
    }

    @Test
    void validateUsernameBeforeSavingUser() throws EmailExistsException{

        String expectedEx = "El Email " + this.userDTOReq.getUsername() + " ya existe. Ingrese un nuevo Email";

        when(customUserRepository.existsByUsername(this.userDTOReq.getUsername())).thenReturn(true);
        var currentEx = assertThrows(EmailExistsException.class, () ->  customUserService.saveUser(this.userDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @Test
    void validateEmployeeBeforeSavingUser() throws EntityExistsException{
        String expectedEx = "El empleado que intenta ingresar ya tiene asignado un usuario";

        when(customUserRepository.existsByUsername(this.userDTOReq.getUsername())).thenReturn(false);
        when(employeeRepository.existsById(this.userDTOReq.getEmployee().getId())).thenReturn(true);
        when(customUserRepository.existsByEmployee_Id(this.userDTOReq.getEmployee().getId())).thenReturn(true);
        var currentEx = assertThrows(EntityExistsException.class, () ->  customUserService.saveUser(this.userDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @Test
    void validateRoleBeforeUpdatingUser() throws RoleNotFoundException{
        String expectedEx = "El rol que intenta ingresar no se encuentra registrado";

        when(customUserRepository.existsByUsername(this.userDTOReq.getUsername())).thenReturn(false);
        when(employeeRepository.existsById(this.userDTOReq.getEmployee().getId())).thenReturn(true);
        when(customUserRepository.existsByEmployee_Id(this.userDTOReq.getEmployee().getId())).thenReturn(false);
        when(roleRepository.existsById(this.userDTOReq.getRoles().get(0).getId())).thenReturn(false);
        var currentEx = assertThrows(RoleNotFoundException.class, () ->  customUserService.saveUser(this.userDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }
}