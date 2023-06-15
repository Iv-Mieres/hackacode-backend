package com.hackacode.themepark.service;

import com.hackacode.themepark.dto.request.GameDTOReq;
import com.hackacode.themepark.dto.response.EmployeeDTORes;
import com.hackacode.themepark.dto.response.GameDTORes;
import com.hackacode.themepark.dto.response.ScheduleDTORes;
import com.hackacode.themepark.model.Employee;
import com.hackacode.themepark.model.Game;
import com.hackacode.themepark.model.Schedule;
import com.hackacode.themepark.repository.IGameRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GameService implements IGameService{

    @Autowired
    private IGameRepository gameRepository;

    @Autowired
    private ModelMapper modelMapper;

    //CREA UN JUEGO
    @Override
    public void saveGame(GameDTOReq gameDTO) throws Exception {
        if (gameRepository.existsByName(gameDTO.getName())){
            throw new Exception("El nombre " + gameDTO.getName() + " ya existe. Ingrese un nuevo nombre");
        }
        gameRepository.save(modelMapper.map(gameDTO, Game.class));
    }

    //MOTRAR UN JUEGO POR ID
    @Override
    public GameDTORes getGameById(Long gameId) throws Exception {
        var gameBD = gameRepository.findById(gameId)
                .orElseThrow(() -> new Exception("El id " + gameId + " no exite. Ingrese un nuevo id"));
        return modelMapper.map(gameBD, GameDTORes.class);
    }

    //LISTA JUEGOS PAGINADOS
    @Override
    public Page<GameDTORes> getAllGames(Pageable pageable) {
        var gamesBD = gameRepository.findAll(pageable);
        var gamesDTO = new ArrayList<GameDTORes>();
        //recorre la lista de juegos de la BD, los convierte a DTO y los guarda en una listaDTO
        for (Game game: gamesBD) {
            gamesDTO.add(modelMapper.map(game, GameDTORes.class));
        }
        return new PageImpl<>(gamesDTO, pageable, gamesDTO.size());
    }

    //ACTUALIZA UN JUEGO
    @Override
    public void updateGame(GameDTOReq gameDTO) throws Exception {
        var gameBD = gameRepository.findById(gameDTO.getId())
                .orElseThrow(() -> new Exception("El id " + gameDTO + " no existe. Ingrese un nuevo id"));
        //valida que el nombre del juego no exista y si existe que coincida con el juego encontrado
        if (!gameDTO.getName().equals(gameBD.getName()) && gameRepository.existsByName(gameDTO.getName())){
            throw new Exception("El nombre " + gameDTO.getName() + " ya existe. Ingrese un nuevo nombre");
        }
        gameRepository.save(modelMapper.map(gameDTO, Game.class));
    }

    //ELIMINA UN JUEGO
    @Override
    public void deleteGame(Long gameID) {
        gameRepository.deleteById(gameID);
    }
}
