package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;
import com.hackacode.themepark.service.IEmailService;
import com.hackacode.themepark.service.ITokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class EmailSenderController {

    @Autowired
    private IEmailService emailService;

    @Autowired
    private ITokenService tokenService;

    @PostMapping("/recuperar_pass")
    public ResponseEntity<Map<String, Object>> sendEmail(@Valid @RequestBody RecoverPasswordDTOReq user) throws UsernameNotFoundException {

        //guardar token en BD
        var token = tokenService.saveToken(user.getUsername());

        //Map para devolver la respuesta al cliente
        Map<String, Object> response = new HashMap<>();
        response.put("token" , token);

        //método de envio de email junto con el token
        emailService.sendEmail(token.getToken() , user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
