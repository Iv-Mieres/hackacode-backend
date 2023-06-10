package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.repository.IEmployeeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IEmployeeUserRepository employeeUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Employee employeeUser = employeeUserRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));


        return new User(employeeUser.getUsername(),
                employeeUser.getPassword(),
                true,
                true,
                true,
                true,
                employeeUser.getAuthorities());
    }
}