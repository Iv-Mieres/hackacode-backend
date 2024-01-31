package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleExistsException;

import java.util.List;


public interface IRoleService {

    void saveRole(RoleDTOReq role) throws Exception;
    RoleDTORes getRoleById(Long roleId) throws IdNotFoundException;

    //LISTA DTO DE ROLES
    List<RoleDTORes> getAllRoles();

    //MODIFICA UN ROL
    void updateRole(RoleDTOReq roleDTO) throws IdNotFoundException, RoleExistsException;

    //ELIMINA UN ROL
    void deleteRole(Long roleId);
}
