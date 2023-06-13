package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.AuthRequestDTOReq;
import com.hackacode.themepark.dto.response.AuthResponseDTORes;
import com.hackacode.themepark.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @PostMapping("/token")
    public ResponseEntity<AuthResponseDTORes> login(@RequestBody AuthRequestDTOReq authDTO){
        return ResponseEntity.ok(loginService.authenticate(authDTO));
    }

}
