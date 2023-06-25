package com.hackacode.themepark.service;

import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;

public interface ITokenService {

    Token saveToken(Token token, String username) throws UsernameNotFoundException;

    Token getToken(String token);
    void deleteByToken(String token);

    void deleteToken(Long tokenId);
}
