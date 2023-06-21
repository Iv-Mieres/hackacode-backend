package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.IEmployeeRepository;
import com.hackacode.themepark.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements ICustomUserService{

    @Autowired
    private ICustomUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IEmployeeRepository employeeRepository;

    //CREAR USUARIO
    @Override
    public void saveUser(UserDTOReq user) throws Exception {
        this.validateDataBeforeSavingUser(user.getUsername(), user.getEmployee().getId(), user.getRoles());
        var saveUser = modelMapper.map(user, CustomUser.class);
        saveUser.setEnable(true);
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(saveUser);
    }

    @Override
    public UserDTORes getByUsername(String username) throws Exception{
        var userDB = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("El usuario:" + username + " no existe"));

        return modelMapper.map(userDB, UserDTORes.class);
    }

    //MOSTRAR UN USUARIO POR ID
    @Override
    public UserDTORes getUserById(Long userId) throws Exception {
        var userBD = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("El id " + userId + " no existe"));
        return modelMapper.map(userBD, UserDTORes.class);
    }

    //LISTAR USUARIOS
    @Override
    public Page<UserDTORes> getAllUsers(Pageable pageable) {
        var usersBD = userRepository.findAll(pageable);
        var usersDTO = new ArrayList<UserDTORes>();

        for (CustomUser user: usersBD) {
            usersDTO.add(modelMapper.map(user, UserDTORes.class));
        }
        return new PageImpl<>(usersDTO, pageable, usersDTO.size());
    }

    //LISTAR USUARIOS POR ROLE
    @Override
    public Page<UserDTORes> getAllUsersPerRole(Pageable pageable, String role) {
        var usersBD = userRepository.findByRoles_role(pageable, role);
        var usersDTO = new ArrayList<UserDTORes>();

        for (CustomUser user: usersBD) {
            usersDTO.add(modelMapper.map(user, UserDTORes.class));
        }
        return new PageImpl<>(usersDTO, pageable, usersDTO.size());
    }

    //MODIFICAR USUARIO
    @Override
    public void updateUser(UserDTOReq user) throws Exception {
        var userBD = userRepository.findById(user.getId())
                .orElseThrow(() -> new Exception("El id " + user.getId() + " no existe"));

        this.validateDataBeforeUpdatingUser(user.getUsername(), userBD.getUsername(),
                                            user.getEmployee().getId(), user.getRoles());
        var saveUser = modelMapper.map(user, CustomUser.class);
        saveUser.setEnable(true);
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(saveUser);
    }

    //ELIMINADO LÓGICO DE USUARIO
    @Override
    public void deleteUser(Long userId) throws Exception {
        var userBD = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("El id" + userId + " no existe"));
        userBD.setEnable(false);
        userRepository.save(userBD);
    }


    //VALIDA DATOS ANTES DE GUARDAR UN USARIO
    public void validateDataBeforeSavingUser(String usernameDTO, Long employeeIdDTO, List<RoleDTOReq> rolesDTO) throws Exception {
        if (userRepository.existsByUsername(usernameDTO)){
            throw new Exception("El Email " + usernameDTO + " ya existe. Ingrese un nuevo Email");
        }
        if (!employeeRepository.existsById(employeeIdDTO)){
            throw new Exception("El empleado que intenta ingresar no existe");
        }
        if(userRepository.existsByEmployee_Id(employeeIdDTO)){
            throw new Exception("El empleado que intenta ingresar ya tiene asignado un usuario");
        }
        for (RoleDTOReq roleDTO: rolesDTO) {
            if (!roleRepository.existsById(roleDTO.getId())){
                throw new Exception("El rol que intenta ingresar no existe");
            }
        }
    }

    //VALIDA DATOS ANTES DE MODIFICAR UN USARIO
    public void validateDataBeforeUpdatingUser(String usernameDTO, String usernameBD , Long employeeIdDTO, List<RoleDTOReq> rolesDTO) throws Exception {
        var employeeBD = employeeRepository.findById(employeeIdDTO)
                .orElseThrow(() -> new Exception("El id " + employeeIdDTO + " no existe"));

        if (!usernameDTO.equals(usernameBD) && userRepository.existsByUsername(usernameDTO)){
            throw new Exception("El Email " + usernameDTO + " ya existe");
        }
        if ( !employeeRepository.existsById(employeeIdDTO)){
            throw new Exception("El empleado que intenta ingresar no existe");
        }
        if(!employeeIdDTO.equals(employeeBD.getId()) && employeeRepository.existsById(employeeIdDTO)){
            throw new Exception("El empleado que intenta ingresar ya tiene asignado un usuario");
        }
        for (RoleDTOReq roleDTO: rolesDTO) {
            if (!roleRepository.existsById(roleDTO.getId())){
                throw new Exception("El rol que intenta ingresar no existe");
            }
        }
    }

}
