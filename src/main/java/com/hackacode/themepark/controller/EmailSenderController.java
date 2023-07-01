package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;
import com.hackacode.themepark.dto.request.ResetPasswordDTOReq;
import com.hackacode.themepark.dto.request.UserDTOReq;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Token;
import com.hackacode.themepark.service.ICustomUserService;
import com.hackacode.themepark.service.IEmailService;
import com.hackacode.themepark.service.ITokenService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ICustomUserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/recuperar_pass")
    public ResponseEntity<Map<String, Object>> sendEmail(@Valid @RequestBody RecoverPasswordDTOReq user) throws UsernameNotFoundException, MessagingException {

        //guardar token en BD
        var token = tokenService.saveToken(user.getUsername());

        //Map para devolver la respuesta al cliente
        Map<String, Object> response = new HashMap<>();
        response.put("token" , token.getToken());
        response.put("expiration", token.getExpirationTime());

        //método de envio de email junto con el token
        emailService.sendEmail(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cambiar_pass")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody ResetPasswordDTOReq resetPassword) throws Exception {
        emailService.resetPassword(resetPassword);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
