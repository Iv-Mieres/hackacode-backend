package com.hackacode.themepark.util;

import com.hackacode.themepark.dto.request.AuthRequestDTOReq;
import com.hackacode.themepark.dto.response.AuthResponseDTORes;
import com.hackacode.themepark.model.CustomUser;
import com.hackacode.themepark.repository.ICustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ICustomUserRepository userRepository;

    @Override
    public AuthResponseDTORes authenticate(AuthRequestDTOReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        CustomUser user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtUtils.generateToken(user);
        return AuthResponseDTORes.builder()
                .token(token)
                .build();
    }
}
