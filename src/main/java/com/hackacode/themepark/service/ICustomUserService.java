package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.request.UserUpdateDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.exception.EmailExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleNotFoundException;
import jakarta.persistence.EntityExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface ICustomUserService {

    void saveUser(UserDTOReq user) throws EmailExistsException,
            IdNotFoundException, RoleNotFoundException;
    UserDTORes getByUsername(String username) throws UsernameNotFoundException;
    UserDTORes getUserById(Long userId) throws IdNotFoundException;
    Page<UserDTORes> getAllUsers(Pageable pageable);

    //LISTAR USUARIOS POR ROLE
    Page<UserDTORes> getAllUsersPerRole(Pageable pageable, String role);

    void updateUser(UserUpdateDTOReq user) throws EntityExistsException, RoleNotFoundException,
            EmailExistsException, IdNotFoundException;
    void deleteUser(Long userId) throws IdNotFoundException;
}
