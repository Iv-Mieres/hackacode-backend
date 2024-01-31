package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.AuthRequestDTOReq;
import com.hackacode.themepark.dto.response.AuthResponseDTORes;
import com.hackacode.themepark.util.ILoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Controlador de inicio de sesion")
@RestController
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Operation(
            summary = "Loguear usuario",
            description = "Autentica al usuario, y devuelve un Jason Web Token"
    )
    @PostMapping("/token")
    public ResponseEntity<AuthResponseDTORes> login(@RequestBody AuthRequestDTOReq authDTO){
        return ResponseEntity.ok(loginService.authenticate(authDTO));
    }

}
