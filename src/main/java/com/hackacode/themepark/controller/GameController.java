package com.hackacode.themepark.controller;

import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.service.IGameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private IGameService gameService;

    @PostMapping
    public void saveGame(@RequestBody Game game){
        gameService.saveGame(game);
    }

}
