package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.dto.response.UserDTORes;
import com.hackacode.themepark.service.ICustomUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ICustomUserService userService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveUser(@Valid @RequestBody UserDTOReq userDTO) throws Exception {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTORes> getUser(@PathVariable Long userId) throws Exception {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping()
    public ResponseEntity<Page<UserDTORes>> getAllUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UserDTOReq userDTO) throws Exception {
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long userId) throws Exception {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
