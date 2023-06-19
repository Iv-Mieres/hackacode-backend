package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.service.IGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/juegos")
public class GameController {

    @Autowired
    private IGameService gameService;

    @PostMapping()
    public ResponseEntity<HttpStatus> saveGame(@Valid @RequestBody GameDTOReq gameDTO) throws Exception {
        gameService.saveGame(gameDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTORes> getGame(@PathVariable Long gameId) throws Exception {
        return ResponseEntity.ok(gameService.getGameById(gameId));
    }

    @GetMapping()
    public ResponseEntity<Page<GameDTORes>> getAllGames(Pageable pageable) throws Exception {
        return ResponseEntity.ok(gameService.getAllGames(pageable));
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateGame(@Valid @RequestBody GameDTOReq gameDTO) throws Exception {
        gameService.updateGame(gameDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<HttpStatus> deleteGame(@PathVariable Long gameId) throws Exception {
        gameService.deleteGame(gameId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
