package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.AuthRequestDTOReq;
import com.hackacode.themepark.dto.response.AuthResponseDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.repository.IEmployeeUserRepository;
import com.hackacode.themepark.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {

    @Autowired
    private JWTUtils jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IEmployeeUserRepository employeeUserRepository;

    @Override
    public AuthResponseDTORes authenticate(AuthRequestDTOReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Employee employee = employeeUserRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtService.generateAccesToken(employee.getUsername());
        return AuthResponseDTORes.builder()
                .token(token)
                .build();
    }
}
