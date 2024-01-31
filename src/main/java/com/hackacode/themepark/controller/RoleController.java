package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.RoleDTOReq;
import com.hackacode.themepark.dto.response.RoleDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleExistsException;
import com.hackacode.themepark.service.IRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Controlador de Rol")
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Operation(
            summary = "Guarda un rol",
            description = "Guarda el rol y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveRole(@RequestBody RoleDTOReq roleDTO) throws Exception {
        roleService.saveRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un rol",
            description = "Busca un rol por id y devuelve un Codigo de estado 200 y los datos del rol"
    )
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTORes> getRoleById(@PathVariable Long roleId) throws IdNotFoundException {
        return ResponseEntity.ok(roleService.getRoleById(roleId));
    }

    @Operation(
            summary = "Traer todos los roles",
            description = "Trae todos los roles de base de datos y devuelve un Codigo de estado 200 y el listado de roles"
    )
    @GetMapping()
    public ResponseEntity<List<RoleDTORes>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @Operation(
            summary = "Actualiza un rol",
            description = "Busca un rol por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateRole(@RequestBody RoleDTOReq roleDTO) throws RoleExistsException, IdNotFoundException {
        roleService.updateRole(roleDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un rol",
            description = "Elimina un rol por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{roleId}")
    public ResponseEntity<HttpStatus> deleteRole(@PathVariable Long roleId) throws RoleExistsException, IdNotFoundException {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
