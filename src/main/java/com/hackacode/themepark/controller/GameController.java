package com.hackacode.themepark.controller;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.exception.IdNotFoundException;
import com.hackacode.themepark.exception.NameExistsException;
import com.hackacode.themepark.service.IGameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Controlador de Juegos")
@RestController
@RequestMapping("/api/juegos")
public class GameController {

    @Autowired
    private IGameService gameService;

    @Operation(
            summary = "Guarda un juego",
            description = "Guarda el juego y devuelve un Codigo de estado 201 creado"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> saveGame(@Valid @RequestBody GameDTOReq gameDTO) throws NameExistsException {
        gameService.saveGame(gameDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Trae un juego",
            description = "Busca un juego por id y devuelve un Codigo de estado 200 y los datos del juego"
    )
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTORes> getGame(@PathVariable Long gameId) throws IdNotFoundException {
        return ResponseEntity.ok(gameService.getGameById(gameId));
    }

    @Operation(
            summary = "Traer todos los juegos",
            description = "Trae todos los juegos de base de datos y devuelve un Codigo de estado 200 y el listado de juegos"
    )
    @GetMapping()
    public ResponseEntity<Page<GameDTORes>> getAllGames(Pageable pageable){
        return ResponseEntity.ok(gameService.getAllGames(pageable));
    }

    @Operation(
            summary = "Actualiza un juego",
            description = "Busca un juego por id y lo actualiza, devuelve un Codigo de estado 204"
    )
    @PutMapping()
    public ResponseEntity<HttpStatus> updateGame(@Valid @RequestBody GameDTOReq gameDTO) throws IdNotFoundException,
            NameExistsException {
        gameService.updateGame(gameDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Elimina un juego",
            description = "Elimina de forma logica un juego por id, devuelve un Codigo de estado 204"
    )
    @DeleteMapping("/{gameId}")
    public ResponseEntity<HttpStatus> deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
