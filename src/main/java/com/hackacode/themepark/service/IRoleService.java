package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;


public interface IRoleService {

    void saveRole(RoleDTOReq role) throws Exception;
    RoleDTORes getRoleById(Long roleId);
}
