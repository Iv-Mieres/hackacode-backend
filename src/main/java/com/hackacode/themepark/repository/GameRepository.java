package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}