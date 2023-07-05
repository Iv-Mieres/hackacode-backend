package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.request.UserUpdateDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.exception.EmailExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleNotFoundException;
import com.hackacode.themepark.service.ICustomUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private ICustomUserService userService;

    @Operation(
            summary = "Guarda un usuario",
            description = "Guarda el usuario y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveUser(@Valid @RequestBody UserDTOReq userDTO) throws EmailExistsException,
            IdNotFoundException, RoleNotFoundException {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un usuario por email",
            description = "Busca un usuario por email y devuelve un Codigo de estado 200 y los datos del usuario"
    )
    @GetMapping("/por_nombre/{username}")
    public ResponseEntity<UserDTORes> getUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return  ResponseEntity.ok(userService.getByUsername(username));
    }

    @Operation(
            summary = "Trae un usuario",
            description = "Busca un usuario por id y devuelve un Codigo de estado 200 y los datos del usuario"
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTORes> getUser(@PathVariable Long userId) throws IdNotFoundException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(
            summary = "Traer todos los usuarios",
            description = "Trae todos los usuarios de base de datos y devuelve un Codigo de estado 200 y el listado de usuarios"
    )
    @GetMapping()
    public ResponseEntity<Page<UserDTORes>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Operation(
            summary = "Traer todos los usuarios",
            description = "Trae todos los usuarios de base de datos por rol y devuelve un Codigo de estado 200 y el listado de usuarios paginados"
    )
    @GetMapping("/por_rol/{role}")
    public ResponseEntity<Page<UserDTORes>> getAllUsersPerRole(Pageable pageable, @PathVariable String role){
        return ResponseEntity.ok(userService.getAllUsersPerRole(pageable, role));
    }

    @Operation(
            summary = "Actualiza un usuario",
            description = "Busca un usuario por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserUpdateDTOReq userDTO) throws EntityExistsException, RoleNotFoundException,
            EmailExistsException, IdNotFoundException {
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un usuario",
            description = "Elimina de forma logica un usuario por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) throws IdNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
