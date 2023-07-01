package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleExistsException;
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

    //CREA UN ROL
    @Override
    public void saveRole(RoleDTOReq roleDTO) throws RoleExistsException {
        if (roleRepository.existsByRole(roleDTO.getRole())){
            throw new RoleExistsException("El rol " + roleDTO.getRole() + " ya se encuentra registrado");
        }
        roleDTO.setRole(roleDTO.getRole().toUpperCase());
        roleRepository.save(modelMapper.map(roleDTO, Role.class));
    }

    //MUESTRA UN ROL POR ID
    @Override
    public RoleDTORes getRoleById(Long roleId) throws IdNotFoundException {
        return modelMapper.map(roleRepository.findById(roleId)
                .orElseThrow(() -> new IdNotFoundException("El id " + roleId + " no se encuentra registrado")), RoleDTORes.class);
    }

    //LISTA DTO DE ROLES
    @Override
    public List<RoleDTORes> getAllRoles(){
        var rolesBD = roleRepository.findAll();
        var rolesDTO = new ArrayList<RoleDTORes>();

        for (Role roleBD: rolesBD) {
            rolesDTO.add(modelMapper.map(roleBD, RoleDTORes.class));
        }
        return rolesDTO;
    }

    //MODIFICA UN ROL
    @Override
    public void updateRole(RoleDTOReq roleDTO) throws IdNotFoundException, RoleExistsException {
       var saveRole = roleRepository.findById(roleDTO.getId())
               .orElseThrow(() -> new IdNotFoundException("El id " + roleDTO.getId() + " no se encuentra registrado"));
        if (!roleDTO.getRole().equals(saveRole.getRole()) && roleRepository.existsByRole(saveRole.getRole())){
            throw new RoleExistsException("El rol " + roleDTO.getRole() + " ya se encuentra registrado");
        }
        roleDTO.setRole(roleDTO.getRole().toUpperCase());
        roleRepository.save(modelMapper.map(roleDTO, Role.class));
    }

    //ELIMINA UN ROL
    @Override
    public void deleteRole(Long roleId){
        roleRepository.deleteById(roleId);
    }

}
