package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleExistsException;
import com.hackacode.themepark.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveRole(@RequestBody RoleDTOReq roleDTO) throws Exception {
        roleService.saveRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTORes> getRoleById(@PathVariable Long roleId) throws IdNotFoundException {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @GetMapping()
    public ResponseEntity<List<RoleDTORes>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateRole(@RequestBody RoleDTOReq roleDTO) throws RoleExistsException, IdNotFoundException {
        roleService.updateRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long roleId) throws RoleExistsException, IdNotFoundException {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
