package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;
import com.hackacode.themepark.dto.request.ResetPasswordDTOReq;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.service.ICustomUserService;
import com.hackacode.themepark.util.IEmailService;
import com.hackacode.themepark.service.ITokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Controlador de cambio de contraseña")
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

    @Operation(summary = "Enviar mail con token",
    description = "Genera un token para la recuperacion de contraseña y la envia por correo al usuario, devuelve un codigo de estado creado ")
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
    @Operation(summary = "Cambiar la contraseña",
    description = "Actualiza la contraseña del usuario en base de datos y devuelve un codigo de estado 200 ok"
    )
    @PostMapping("/cambiar_pass")
    public ResponseEntity<HttpStatus> resetPassword(@RequestBody ResetPasswordDTOReq resetPassword) throws Exception {
        emailService.resetPassword(resetPassword);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
