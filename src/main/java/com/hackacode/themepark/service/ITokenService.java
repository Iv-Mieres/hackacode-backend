package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.ResetPasswordDTOReq;
import com.hackacode.themepark.exception.UsernameNotFoundException;
import com.hackacode.themepark.model.Token;

public interface ITokenService {

    Token saveToken(String token) throws UsernameNotFoundException;

    Token getToken(String token) throws Exception;
    void deleteByToken(String token);

    void deleteToken(Long tokenId);
}
