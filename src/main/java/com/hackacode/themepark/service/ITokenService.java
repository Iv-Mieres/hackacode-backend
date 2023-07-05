package com.hackacode.themepark.service;

import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;

public interface ITokenService {

    Token saveToken(String token) throws UsernameNotFoundException;
    Token getToken(String token) throws Exception;

    void deleteById(Long tokenId);
}
