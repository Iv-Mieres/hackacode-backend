package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;

import java.util.List;


public interface IRoleService {

    List<RoleDTORes> gettAll();
    void saveRole(RoleDTOReq role) throws Exception;
    RoleDTORes getRoleById(Long roleId);
}
