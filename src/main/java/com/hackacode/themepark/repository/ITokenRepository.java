package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    void deleteByToken(String token);
}
