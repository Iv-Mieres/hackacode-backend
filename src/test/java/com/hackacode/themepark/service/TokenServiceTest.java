package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.TicketDTOReq;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.*;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.ITicketDetailRepository;
import com.hackacode.themepark.repository.ITokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @Mock
    private ITokenRepository tokenRepository;
    @Mock
    private ICustomUserRepository userRepository;
    private CustomUser user;
    private Token token;


    @BeforeEach
    void setUp() {
        this.user = new CustomUser(1L, "prueba@mail.com", "prueba1234",
                true, Set.of(new Role()), new Employee());
        this.token = new Token(1L, "", LocalDateTime.now().plusMinutes(15),user);
    }

    @DisplayName("comprueba que método saveToken no lance ningun excepción")
    @Test
    void saveToken() throws UsernameNotFoundException {
        String username = "prueba@mail.com";

        token.setUser(this.user);
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(this.user));
        tokenService.saveToken(username);
    }

    @DisplayName("comprueba que se devuelva un token")
    @Test
    void getToken() throws Exception {
        var uuid = UUID.randomUUID();
        this.token.setToken(uuid.toString());
        when(tokenRepository.findByToken(this.token.getToken())).thenReturn(Optional.ofNullable(this.token));
        var currentToken = tokenService.getToken(this.token.getToken());
        assertEquals(this.token, currentToken);
        assertEquals(this.token.getToken(), currentToken.getToken());
    }

    @DisplayName("comprueba que se devuelva un token")
    @Test
    void throwAnExceptionIfTheTokenDoesNotExist() throws Exception {
        String expectedEx = "El token no ha sido encotrado";
        var uuid = UUID.randomUUID();
        this.token.setToken(uuid.toString());
        when(tokenRepository.findByToken(this.token.getToken())).thenReturn(Optional.empty());
        Exception currentEx = assertThrows(Exception.class, () -> tokenService.getToken(this.token.getToken()));

        assertEquals(expectedEx, currentEx.getMessage());
    }

    @DisplayName("comprueba que se elimine un token")
    @Test
    void deleteById() {
        doNothing().when(tokenRepository).deleteById(1L);
        tokenService.deleteById(1L);
        verify(tokenRepository).deleteById(1L);
    }

}