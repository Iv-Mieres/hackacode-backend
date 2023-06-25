package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.RecoverPasswordDTOReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements IEmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String token, String username) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(username);
        mailMessage.setFrom("mieres.iv@gmail.com");
        mailMessage.setSubject("Recuperación de contraseña");
        mailMessage.setText("www.localhost:4200/recuperar_pass/" + token);

        javaMailSender.send(mailMessage);
    }
}
