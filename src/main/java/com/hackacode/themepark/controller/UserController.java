package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.request.UserUpdateDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.exception.EmailExistsException;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.RoleNotFoundException;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.service.ICustomUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private ICustomUserService userService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveUser(@Valid @RequestBody UserDTOReq userDTO) throws EmailExistsException,
            IdNotFoundException, RoleNotFoundException {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/por_nombre/{username}")
    public ResponseEntity<UserDTORes> getUserByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return  ResponseEntity.ok(userService.getByUsername(username));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTORes> getUser(@PathVariable Long userId) throws IdNotFoundException {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping()
    public ResponseEntity<Page<UserDTORes>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/por_rol/{role}")
    public ResponseEntity<Page<UserDTORes>> getAllUsersPerRole(Pageable pageable, @PathVariable String role){
        return ResponseEntity.ok(userService.getAllUsersPerRole(pageable, role));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserUpdateDTOReq userDTO) throws EntityExistsException, RoleNotFoundException,
            EmailExistsException, IdNotFoundException {
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) throws IdNotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
