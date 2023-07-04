package com.hackacode.themepark.util;

import com.hackacode.themepark.dto.request.ResetPasswordDTOReq;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.model.Token;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private ICustomUserRepository userRepository;
    @Mock
    private ITokenRepository tokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private ResetPasswordDTOReq passwordDTOReq;


    @BeforeEach
    void setUp() {
    }

    @DisplayName("lanza excepcíon si password y repeatPassword no coinciden")
    @Test
    void throwAnExceptionIsThePasswordsDoNotMatch() throws Exception {
        String expectedEx = "Revise los datos ingresados. Ambos campos deben coincidir";
        var generateToken = UUID.randomUUID().toString();
        this.passwordDTOReq = new ResetPasswordDTOReq("prueba1234", "otro1234", generateToken);

        Exception currentEx = assertThrows(Exception.class, () -> emailService.resetPassword(this.passwordDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());

    }

    @DisplayName("comprueba que se guarde la nueva contraseña")
    @Test
    void resetPassword() throws Exception {

        String resetPassword = "reset1234";
        String hashPassword = "$2y$10$.2e0ifqv2KIOjolMPDGbfu2R4jueXeuQBzguu1XMF4cl7XwAfVEem";
        var generateToken = UUID.randomUUID().toString();

        var user = new CustomUser();
        user.setUsername("prueba@mail.com");

        var token = new Token();
        token.setToken(generateToken);
        token.setExpirationTime(LocalDateTime.now().plusMinutes(15));
        token.setUser(user);
        this.passwordDTOReq = new ResetPasswordDTOReq(resetPassword, resetPassword, generateToken);

        when(tokenRepository.findByToken(this.passwordDTOReq.getToken())).thenReturn(Optional.of(token));
        when(userRepository.findByUsername(token.getUser().getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(resetPassword)).thenReturn(hashPassword);
        user.setPassword(passwordEncoder.encode(resetPassword));
        emailService.resetPassword(this.passwordDTOReq);

        assertEquals(hashPassword, user.getPassword());
        verify(userRepository).save(user);


    }

    @DisplayName("comprueba que el token haya expirado")
    @Test
    void expirationToken() throws Exception {

        String expectedEx = "El link ah expirado";
        String resetPassword = "reset1234";
        var generateToken = UUID.randomUUID().toString();

        var token = new Token();
        token.setToken(generateToken);
        token.setExpirationTime(LocalDateTime.now());

        this.passwordDTOReq = new ResetPasswordDTOReq(resetPassword, resetPassword, generateToken);

        when(tokenRepository.findByToken(this.passwordDTOReq.getToken())).thenReturn(Optional.of(token));
        Exception currentEx = assertThrows(Exception.class, () ->  emailService.resetPassword(this.passwordDTOReq));
        assertEquals(expectedEx, currentEx.getMessage());
    }
}