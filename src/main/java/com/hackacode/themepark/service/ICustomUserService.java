package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomUserService {

    void saveUser(UserDTOReq user) throws Exception;
    UserDTORes getByUsername(String username) throws Exception;
    UserDTORes getUserById(Long userId) throws IdNotFoundException;
    Page<UserDTORes> getAllUsers(Pageable pageable);

    //LISTAR USUARIOS POR ROLE
    Page<UserDTORes> getAllUsersPerRole(Pageable pageable, String role);

    void updateUser(UserDTOReq user) throws Exception;
    void deleteUser(Long userId) throws IdNotFoundException;
}
