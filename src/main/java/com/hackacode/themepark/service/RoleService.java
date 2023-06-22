package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.model.Role;
import com.hackacode.themepark.repository.IRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService implements IRoleService{

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoleDTORes> gettAll() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDTORes> response = new ArrayList<>();
        roles.forEach(
                r -> response.add(modelMapper.map(r,RoleDTORes.class))
        );
        return response;
    }

    @Override
    public void saveRole(RoleDTOReq role) throws Exception {
        if (roleRepository.existsByRole(role.getRole())){
            throw new Exception("El role ya existe");
        }
        roleRepository.save(modelMapper.map(role, Role.class));
    }

    @Override
    public RoleDTORes getRoleById(Long roleId) {
        return modelMapper.map(roleRepository.findById(roleId), RoleDTORes.class);
    }
}
