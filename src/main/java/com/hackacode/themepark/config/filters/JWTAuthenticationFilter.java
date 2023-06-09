package com.hackacode.themepark.config.filters;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.util.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private JWTUtils jwtUtils;

    public JWTAuthenticationFilter(JWTUtils jwtUtils){
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        Employee employeeeUser = null;
        String username = "";
        String password = "";
        try{
            employeeeUser = new ObjectMapper().readValue(request.getInputStream(), Employee.class);
            username = employeeeUser.getUsername();
            password = employeeeUser.getPassword();
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        //User user = (User) authResult.getPrincipal();
        //ring token = jwtUtils.generateAccesToken(user.getUsername());

//        response.addHeader("Authorization", token);
//
//        Map<String, Object> httpResponse = new HashMap<>();
//        httpResponse.put("token", token);
//        httpResponse.put("Message", "Autenticacion Correcta");
//        httpResponse.put("Username", user.getUsername());
//
//        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
