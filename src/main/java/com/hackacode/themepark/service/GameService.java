package com.hackacode.themepark.service;

import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.repository.IGameRepository;
import org.springframework.stereotype.Service;

@Service
public class GameService implements IGameService{

    private IGameRepository gameRepository;

    @Override
    public void saveGame(Game game) {
        gameRepository.save(game);
    }
}
