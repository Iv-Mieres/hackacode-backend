package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.repository.ICustomUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class CustomUserService implements ICustomUserService{

    @Autowired
    private ICustomUserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    //CREAR USUARIO
    @Override
    public void saveUser(UserDTOReq user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new Exception("El Email " + user.getUsername() + " ya existe. Ingrese un nuevo Email");
        }
        var saveUser = modelMapper.map(user, CustomUser.class);
        saveUser.setEnable(true);
        userRepository.save(saveUser);
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

    //MODIFICAR USUARIO
    @Override
    public void updateUser(UserDTOReq user) throws Exception {
        if (userRepository.existsByUsername(user.getUsername())){
            throw new Exception("El Email " + user.getUsername() + " ya existe");
        }
        var saveUser = modelMapper.map(user, CustomUser.class);
        saveUser.setEnable(true);
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

}
