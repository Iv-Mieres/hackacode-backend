package com.hackacode.themepark.service;

import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService implements ITokenService {

    @Autowired
    private ITokenRepository tokenRepository;

    @Autowired
    private ICustomUserRepository userRepository;


    @Override
    public Token saveToken(String username) throws UsernameNotFoundException {

        var userBD = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El username ingresado no existe"));

        String generateToken = UUID.randomUUID().toString();
        var token = new Token();
        token.setToken(generateToken);
        token.setExpirationTime(LocalDateTime.now().plusMinutes(15));
        token.setUser(userBD);
        return tokenRepository.save(token);
    }

    @Override
    public Token getToken(String token) throws Exception {
        return tokenRepository.findByToken(token)
                .orElseThrow(() -> new Exception("El token no ha sido encotrado"));
    }

    @Override
    public void deleteById(Long tokenId) {
        tokenRepository.deleteById(tokenId);
    }


}
