package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleExistsException;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.IRoleRepository;
import com.hackacode.themepark.util.IWordsConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private IRoleRepository roleRepository;
    @Mock
    private IWordsConverter wordsConverter;
    @Mock
    private ModelMapper modelMapper;

    private Role role;
    private RoleDTOReq roleDTOReq;
    private RoleDTORes roleDTORes;

    @BeforeEach
    void setUp() {
        this.role = new Role();
        this.role.setId(1L);
        this.role.setRole("ADMINISTRADOR");

        this.roleDTOReq = new RoleDTOReq();
        this.roleDTOReq.setId(1L);
        this.roleDTOReq.setRole("ADMINISTRADOR");

        this.roleDTORes = new RoleDTORes();
        this.roleDTORes.setId(1L);
        this.roleDTORes.setRole("ADMINISTRADOR");

    }

    @DisplayName("comprueba que se guarde un role")
    @Test
    void saveRole() throws RoleExistsException {

        when(roleRepository.existsByRole(this.roleDTOReq.getRole())).thenReturn(false);
        when(modelMapper.map(this.roleDTOReq, Role.class)).thenReturn(this.role);
        roleService.saveRole(this.roleDTOReq);
        verify(roleRepository).save(this.role);

    }

    @DisplayName("comprueba que se lance una excepció si el nombre del rol ya está registrado")
    @Test
    void throwsAnExceptionIfRoleNameAlreadyExists() throws RoleExistsException {
        String expectedEx = "El rol " + this.roleDTOReq.getRole()+ " ya se encuentra registrado";

        when(roleRepository.existsByRole(this.roleDTOReq.getRole())).thenReturn(true);
        Exception currentEx = assertThrows(RoleExistsException.class, () -> roleService.saveRole(this.roleDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba que muestre un role por id")
    @Test
    void getRoleById() throws IdNotFoundException {
        when(modelMapper.map(this.role, RoleDTORes.class)).thenReturn(this.roleDTORes);
        when(roleRepository.findById(this.role.getId())).thenReturn(Optional.ofNullable(this.role));
        var currentRoleDTORes = roleService.getRoleById(this.role.getId());
        assertEquals(this.roleDTORes, currentRoleDTORes);
        verify(roleRepository).findById(this.role.getId());

    }

    @DisplayName("comprueba que muestre una lista de roles")
    @Test
    void getAllRoles() {

        var roles = new ArrayList<Role>();
        roles.add(this.role);
        roles.add(new Role());

        when(roleRepository.findAll()).thenReturn(roles);
        when(modelMapper.map(this.role, RoleDTORes.class)).thenReturn(this.roleDTORes);
        var currentRoles = roleService.getAllRoles();

        assertEquals(roles.size(), currentRoles.size());
        assertEquals(this.roleDTORes,currentRoles.get(0));
    }

    @DisplayName("comprueba que se actualice un rol")
    @Test
    void updateRole() throws RoleExistsException, IdNotFoundException {

        when(roleRepository.findById(this.roleDTOReq.getId())).thenReturn(Optional.ofNullable(this.role));
        when(modelMapper.map(this.roleDTOReq, Role.class)).thenReturn(this.role);
        roleService.updateRole(this.roleDTOReq);
        verify(roleRepository).save(this.role);
    }

    @DisplayName("comprueba que elimine un rol")
    @Test
    void deleteRole() {

        doNothing().when(roleRepository).deleteById(this.role.getId());
        roleService.deleteRole(this.role.getId());
        verify(roleRepository).deleteById(this.role.getId());
    }
}