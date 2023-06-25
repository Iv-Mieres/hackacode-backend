package com.hackacode.themepark.service;

import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;
import com.hackacode.themepark.repository.ICustomUserRepository;
import com.hackacode.themepark.repository.ITokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements ITokenService{

    @Autowired
    private ITokenRepository tokenRepository;

    @Autowired
    private ICustomUserRepository userRepository;


    @Override
    public Token saveToken(Token token, String username) throws UsernameNotFoundException {
       var userBD = userRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("El username ingresado no existe"));
       token.setUser(userBD);
       return tokenRepository.save(token);
    }

    @Override
    public Token getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteByToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Override
    public void deleteToken(Long tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    public String validateToken(String token){
        String tokenBD = tokenRepository.findByToken(token).getToken();
        return null;
    }

}
